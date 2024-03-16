package devx.challenge.login.DTOs;

import devx.challenge.login.Enums.Challanges;

public class LoginResponseDTO {
  private String imageURI;
  private Challanges challenge;

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
