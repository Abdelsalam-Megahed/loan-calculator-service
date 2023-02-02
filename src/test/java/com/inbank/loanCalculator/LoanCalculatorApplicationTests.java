package com.inbank.loanCalculator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoanCalculatorApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void itShouldThrowExceptionIfCustomerHasLoan() throws Exception {
		LoanCalculatorRequest customerRequest = new LoanCalculatorRequest("49002010965", 3000, 16);

		mockMvc.perform(post("/api/v1/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(customerRequest))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void itShouldReturnMaxSumForSegmentOne() throws Exception {
		LoanCalculatorRequest customerRequest = new LoanCalculatorRequest("49002010976", 5000, 14);

		MvcResult calculatorResult = mockMvc.perform(post("/api/v1/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(customerRequest))))
				.andExpect(status().isOk())
				.andReturn();

		String response = calculatorResult.getResponse().getContentAsString();

		assertThat(response).contains("1400");
	}

	private String ObjectToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
