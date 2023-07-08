package com.comak.sce.controller;

import com.comak.sce.dto.AccountDto;
import com.comak.sce.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean AccountService accountService;

    private static final String URL = "/api/account";


    @Test
    void testCreateAccountWithoutFirstNameShouldReturn400BadRequest() throws Exception {

        String requestBody = objectMapper.writeValueAsString(
            AccountDto.builder()
                .ownerFirstName("")
                .ownerLastName("Cichecki")
                .accountBalance(BigDecimal.valueOf(1000))
                .build()
        );
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(URL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content( requestBody );

        mockMvc.perform( mockRequest )
            .andExpect(status().isBadRequest());
    }
}