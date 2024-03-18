package devx.challenge.login.controllers;

import devx.challenge.login.DTOs.*;
import devx.challenge.login.Enums.Challanges;
import devx.challenge.login.entities.UserEntity;
import devx.challenge.login.services.JwtService;
import devx.challenge.login.services.LoginService;
import devx.challenge.login.services.MfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;
  @Autowired
  private MfaService mfaService;

  @Autowired
  private JwtService jwtService;

  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/login")
  public LoginResponseDTO login(@RequestBody LoginDTO loginDTO) {
    LoginResponseDTO response = new LoginResponseDTO();
    String token = mfaService.generateToken();
    if(!loginService.isUserCreated(loginDTO.email())) {
      response.setChallenge(Challanges.VALIDATE_QR_CODE);
      String image = mfaService.generateQrCodeImage(token);
      response.setImageURI(image);

      UserEntity user = new UserEntity();
      user.setEmail(loginDTO.email());
      user.setMfaCode(token);
      user.setLastMfaAvailable(false);
      loginService.saveUserInDb(user);
      response.setOtpCode(mfaService.getOtpCode(token));
      return response;
    }

    UserEntity user = loginService.searchUserByEmail(loginDTO.email());

    if(!user.isMfaEnabled()) {
      String image = mfaService.generateQrCodeImage(user.getMfaCode());
      response.setChallenge(Challanges.VALIDATE_QR_CODE);
      response.setImageURI(image);
      response.setOtpCode(mfaService.getOtpCode(user.getMfaCode()));
      return response;
    }

    long daysBetweenLastLogin = ChronoUnit.DAYS.between(LocalDateTime.now(), user.getLastLogin());
    int maxTimeWithoutOTP = 7;
    if(daysBetweenLastLogin > maxTimeWithoutOTP) {
      response.setOtpCode(mfaService.getOtpCode(user.getMfaCode()));
      response.setChallenge(Challanges.SEND_OTP);
    } else {
      response.setChallenge(Challanges.SEND_PASSWORD);
    }

    return response;
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/mfa")
  public ResponseEntity<?> validateMfa(@RequestBody MfaDTO mfaDTO) {
    if(!mfaService.isOTPSetted(mfaDTO)) {
      return ResponseEntity.badRequest().build();
    }

    UserEntity user = loginService.searchUserByEmail(mfaDTO.email());

    boolean isOtpValid = mfaService.isOtpValid(user.getMfaCode(), mfaDTO.code());
    if(!isOtpValid) {
      return ResponseEntity.badRequest().build();
    }

    loginService.changeStatusOfValidMfaToTrue(mfaDTO.email());
    ResponseChallangesDTO dto = new ResponseChallangesDTO();

    if(user.getPassword() == null) {
      dto.setChallenge(Challanges.SET_PASSWORD);
    } else {
      dto.setChallenge(Challanges.SEND_PASSWORD);
    }
    user.setMfaEnabled(true);
    loginService.saveUserInDb(user);
    return ResponseEntity.ok(dto);
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/password")
  public ResponseEntity<?> password(@RequestBody SetupPasswordDTO dto) {
    UserEntity user = loginService.searchUserByEmail(dto.email());
    if(user == null || !user.isMfaEnabled()) {
      return ResponseEntity.badRequest().build();
    }

    String jwtToken = jwtService.generateToken(user);
    String refresh = jwtService.generateRefreshToken(user);
    AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
    authenticationResponseDTO.setAccessToken(jwtToken);
    authenticationResponseDTO.setRefreshToken(refresh);

    if(user.getPassword() == null) {
      user.setPassword(dto.password());
      user.setLastLogin(LocalDateTime.now());
      loginService.saveUserInDb(user);
      return ResponseEntity.ok(authenticationResponseDTO);
    }

    if(user.getPassword().equals(dto.password())) {
      user.setLastLogin(LocalDateTime.now());
      loginService.saveUserInDb(user);
      return ResponseEntity.ok(authenticationResponseDTO);
    }
    return ResponseEntity.badRequest().build();
  }
}
