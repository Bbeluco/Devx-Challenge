package devx.challenge.login.controllers;

import devx.challenge.login.DTOs.PrivateContentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateContentController {

  @CrossOrigin(origins = "*")
  @GetMapping("/private")
  public ResponseEntity<?> privateContent() {
    PrivateContentDTO dto = new PrivateContentDTO();
    dto.setMessage("Congratulations, you successful logged in! :D ");
    return ResponseEntity.ok(dto);
  }
}
