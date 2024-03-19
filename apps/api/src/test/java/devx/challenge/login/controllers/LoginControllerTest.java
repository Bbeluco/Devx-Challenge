package devx.challenge.login.controllers;

import devx.challenge.login.Enums.Challanges;
import devx.challenge.login.entities.UserEntity;
import devx.challenge.login.repositories.UserRepository;
import devx.challenge.login.services.LoginService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
  @Autowired
  private WebApplicationContext context;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private LoginService loginService;

  private MockMvc mockMvc;

  private JSONObject json = new JSONObject();


  @Before
  public void setup(){
    mockMvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(springSecurity())
      .build();

    Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);

    try {
        json.put("email", "unit.test@test.com");
    } catch (JSONException e) {
        throw new RuntimeException(e);
    }
  }

  @BeforeEach
  public void resetUser() {

  }

  @Test
  public void givenSendValidEmailToAPI_shouldReturnImageUriAndChallenge() throws Exception {
    mockMvc.perform(
      post("/login")
        .contentType(MediaType.APPLICATION_JSON).content(json.toString())
    ).andExpect(status().isOk())
      .andExpect(result -> {
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertNotNull(response.get("imageURI"));
        Assert.assertNotNull(response.get("otpCode"));
        Assert.assertEquals(Challanges.VALIDATE_QR_CODE.toString(), response.get("challenge"));
      });
  }

  @Test
  public void givenSendEmptyBody_shouldReturn400error() throws Exception {
    mockMvc.perform(
      post("/login")
        .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void givenUserCreatedWithNoMfaDefined_shouldreturnChallangeValidateQrCode() throws Exception {
    UserEntity user = new UserEntity();
    user.setEmail("unit.test@email.com");
    user.setMfaEnabled(false);

    Mockito.when(loginService.isUserCreated(Mockito.any())).thenReturn(true);
    Mockito.when(loginService.searchUserByEmail(Mockito.any())).thenReturn(user);

    mockMvc.perform(
      post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json.toString())
    ).andExpect(status().isOk())
      .andExpect(result -> {
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertNotNull(response.get("imageURI"));
        Assert.assertNotNull(response.get("otpCode"));
        Assert.assertEquals(Challanges.VALIDATE_QR_CODE.toString(), response.get("challenge"));
      });
  }

  @Test
  public void givenUserCreatedButWithNoPasswordSet_shouldReturnSetPasswordChallenge() throws Exception {
    UserEntity user = new UserEntity();
    user.setEmail("unit.test@email.com");
    user.setMfaEnabled(true);

    Mockito.when(loginService.isUserCreated(Mockito.any())).thenReturn(true);
    Mockito.when(loginService.searchUserByEmail(Mockito.any())).thenReturn(user);

    mockMvc.perform(
        post("/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json.toString())
      ).andExpect(status().isOk())
      .andExpect(result -> {
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(Challanges.SET_PASSWORD.toString(), response.get("challenge"));
      });
  }

  @Test
  public void givenUserWithMoreThan7DaysWithoutLogin_shouldReturnSendOtpChallenge() throws Exception {
    UserEntity user = new UserEntity();
    user.setEmail("unit.test@email.com");
    user.setMfaEnabled(true);
    user.setPassword("unit test");
    user.setLastLogin(LocalDateTime.now().minusDays(8));

    Mockito.when(loginService.isUserCreated(Mockito.any())).thenReturn(true);
    Mockito.when(loginService.searchUserByEmail(Mockito.any())).thenReturn(user);

    mockMvc.perform(
        post("/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json.toString())
      ).andExpect(status().isOk())
      .andExpect(result -> {
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(Challanges.SEND_OTP.toString(), response.get("challenge"));
      });
  }

  @Test
  public void givenUserWithAllRegistryOk_shouldReturnSendPasswordChallenge() throws Exception {
    UserEntity user = new UserEntity();
    user.setEmail("unit.test@email.com");
    user.setMfaEnabled(true);
    user.setPassword("unit test");
    user.setLastLogin(LocalDateTime.now());

    Mockito.when(loginService.isUserCreated(Mockito.any())).thenReturn(true);
    Mockito.when(loginService.searchUserByEmail(Mockito.any())).thenReturn(user);

    mockMvc.perform(
        post("/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json.toString())
      ).andExpect(status().isOk())
      .andExpect(result -> {
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        Assert.assertEquals(Challanges.SEND_PASSWORD.toString(), response.get("challenge"));
      });
  }
}
