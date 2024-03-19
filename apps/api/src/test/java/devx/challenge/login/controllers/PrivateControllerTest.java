package devx.challenge.login.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrivateControllerTest {
  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setup(){
    mockMvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(springSecurity())
      .build();
  }


  @WithMockUser("spring")
  @Test
  public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
    mockMvc.perform(get("/private").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  public void givenRequestOnPrivateService_shouldRejectsWith401() throws Exception {
    mockMvc.perform(get("/private").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isUnauthorized());
  }
}
