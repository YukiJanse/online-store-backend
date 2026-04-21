package se.jensen.yuki.onlinestorebackend.user_order.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.LoginUserUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.RefreshTokenService;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.RegisterUserUseCase;
import se.jensen.yuki.onlinestorebackend.user_order.user.application.TokenPair;
import se.jensen.yuki.onlinestorebackend.user_order.user.web.dto.AuthRegisterRequestDTO;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserCommandControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RegisterUserUseCase registerUserUseCase;

    @MockitoBean
    LoginUserUseCase loginUserUseCase;

    @MockitoBean
    RefreshTokenService refreshTokenService;

    @Test
    void registerUser() throws Exception {
        TokenPair tokenPair = new TokenPair("accessToken", "refreshToken");

        when(registerUserUseCase.execute(any())).thenReturn(tokenPair);

        mockMvc.perform(post("/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "Yuki Janse",
                                  "email": "yuki@hotmail.com",
                                  "password": "Password1234",
                                  "phoneNumber": "+46012345678",
                                  "street": "street",
                                  "postalCode": "11111",
                                  "city": "city",
                                  "country": "country"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(header().exists("Set-Cookie"))
                .andExpect(header().string("Set-Cookie", containsString("refreshToken")));
    }

    @Test
    void login() throws Exception {
        TokenPair tokenPair = new TokenPair("accessToken", "refreshToken");

        when(loginUserUseCase.execute(any())).thenReturn(tokenPair);

        mockMvc.perform(post("/v1/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "yuki@hotmail.com",
                                  "password": "Password1234"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(header().exists("Set-Cookie"))
                .andExpect(header().string("Set-Cookie", containsString("refreshToken")));
    }
}