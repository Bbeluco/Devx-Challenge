package devx.challenge.login.services;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.util.Utils;
import devx.challenge.login.DTOs.MfaDTO;
import devx.challenge.login.entities.UserEntity;
import devx.challenge.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MfaService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LoginService loginService;

  public String generateToken() {
    SecretGenerator secretGenerator = new DefaultSecretGenerator();
    return secretGenerator.generate();
  }

  public String generateQrCodeImage(String secret) {
    QrData data = new QrData.Builder()
      .label("2FA Java")
      .secret(secret)
      .issuer("bruno-beluco")
      .algorithm(HashingAlgorithm.SHA1)
      .digits(6)
      .period(30)
      .build();

    QrGenerator qrGenerator = new ZxingPngQrGenerator();
    byte[] imageData = new byte[0];
      try {
          imageData = qrGenerator.generate(data);
      } catch (QrGenerationException e) {
          throw new RuntimeException(e);
      }

      return Utils.getDataUriForImage(imageData, qrGenerator.getImageMimeType());
  }

  public boolean isOtpValid(String secret, String code) {
    TimeProvider timeProvider = new SystemTimeProvider();
    CodeGenerator codeGenerator = new DefaultCodeGenerator();
    CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
    return verifier.isValidCode(secret, code);
  }

  public boolean isOTPSetted(MfaDTO dto) {
    if(loginService.isUserCreated(dto.email())) {
      UserEntity user = userRepository.findByEmail(dto.email());
      return true;
    }

    return false;
  }
}
