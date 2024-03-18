package devx.challenge.login.DTOs;

import devx.challenge.login.Enums.Challanges;

public class ResponseChallangesDTO {
  private String session;
  private Challanges challenge;

  public String getSession() {
    return session;
  }

  public void setSession(String session) {
    this.session = session;
  }

  public Challanges getChallenge() {
    return challenge;
  }

  public void setChallenge(Challanges challenge) {
    this.challenge = challenge;
  }
}
