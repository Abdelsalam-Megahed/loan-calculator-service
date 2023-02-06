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

import java.io.UnsupportedEncodingException;
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
	void itShouldReturnMaxSumForSegmentOne() throws Exception {
		LoanCalculatorRequest customerRequest = new LoanCalculatorRequest("49002010976", 5000, 40); //personal code for segment one

		MvcResult calculatorResult = mockMvc.perform(post("/api/v1/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(customerRequest))))
				.andExpect(status().isOk())
				.andReturn();
		LoanCalculatorResponse response = JsonToObject(calculatorResult);

		assertThat(response.getAmount()).isEqualTo(4000);
		assertThat(response.getLoanPeriod()).isEqualTo(40);
	}

	@Test
	void itShouldReturnMaxSumAndProlongLoanPeriodWhenAmountIsBelowTheMinimum() throws Exception {
		LoanCalculatorRequest customerRequest = new LoanCalculatorRequest("49002010976", 5000, 14); //personal code for segment one

		MvcResult calculatorResult = mockMvc.perform(post("/api/v1/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(customerRequest))))
				.andExpect(status().isOk())
				.andReturn();
		LoanCalculatorResponse response = JsonToObject(calculatorResult);

		assertThat(response.getAmount()).isEqualTo(2000);
		assertThat(response.getLoanPeriod()).isEqualTo(20);
	}

	@Test
	void itShouldNotReturnMoreThan10K() throws Exception {
		LoanCalculatorRequest customerRequest = new LoanCalculatorRequest("49002010998", 5600, 14); //personal code for segment three

		MvcResult calculatorResult = mockMvc.perform(post("/api/v1/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(customerRequest))))
				.andExpect(status().isOk())
				.andReturn();
		LoanCalculatorResponse response = JsonToObject(calculatorResult);

		assertThat(response.getAmount()).isEqualTo(10000);
		assertThat(response.getLoanPeriod()).isEqualTo(14);
	}

	@Test
	void itShouldGiveBadRequestIfCustomerHasLoan() throws Exception {
		LoanCalculatorRequest customerRequest = new LoanCalculatorRequest("49002010965", 3000, 16);

		mockMvc.perform(post("/api/v1/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(customerRequest))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void itShouldGiveBadRequestIfRequestedAmountIsMoreThan10K() throws Exception {
		LoanCalculatorRequest customerRequest = new LoanCalculatorRequest("49002010976", 12000, 14); //personal code for segment one

		mockMvc.perform(post("/api/v1/calculate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(Objects.requireNonNull(ObjectToJson(customerRequest))))
				.andExpect(status().isBadRequest());
	}

	private String ObjectToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private LoanCalculatorResponse JsonToObject(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
		return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), LoanCalculatorResponse.class);
	}
}
