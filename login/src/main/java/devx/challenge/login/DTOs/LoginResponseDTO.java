package devx.challenge.login.DTOs;

import devx.challenge.login.Enums.Challanges;

public class LoginResponseDTO {
  private String imageURI;
  private Challanges challenge;
  private String otpCode;

  public String getOtpCode() {
    return otpCode;
  }

  public void setOtpCode(String otpCode) {
    this.otpCode = otpCode;
  }

  public String getImageURI() {
    return imageURI;
  }

  public void setImageURI(String imageURI) {
    this.imageURI = imageURI;
  }

  public Challanges getChallenge() {
    return challenge;
  }

  public void setChallenge(Challanges challenge) {
    this.challenge = challenge;
  }
}
