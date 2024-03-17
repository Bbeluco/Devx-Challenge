package devx.challenge.login.services;

import devx.challenge.login.DTOs.AuthenticationResponseDTO;
import devx.challenge.login.DTOs.LoginDTO;
import devx.challenge.login.DTOs.SetupPasswordDTO;
import devx.challenge.login.entities.UserEntity;
import devx.challenge.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  public boolean isUserCreated(String email) {
    UserEntity user = userRepository.findByEmail(email);
    return user != null;
  }

  public AuthenticationResponseDTO register(SetupPasswordDTO dto) {
    UserEntity user = new UserEntity();
    user.setEmail(dto.email());
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    AuthenticationResponseDTO response = new AuthenticationResponseDTO();
    response.setAccessToken(jwtToken);
    response.setRefreshToken(refreshToken);
    return response;
  }

  public UserEntity searchUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public void changeStatusOfValidMfaToTrue(String email) {
    UserEntity user = userRepository.findByEmail(email);
    user.setMfaEnabled(true);
    saveUserInDb(user);
  }

  public void saveUserInDb(UserEntity user) {
    userRepository.save(user);
  }
}
