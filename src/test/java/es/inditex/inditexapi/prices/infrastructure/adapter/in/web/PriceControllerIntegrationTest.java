package es.inditex.inditexapi.prices.infrastructure.adapter.in.web;

import es.inditex.inditexapi.InditexApiApplication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = InditexApiApplication.class)
@AutoConfigureMockMvc
@DisplayName("PriceController Integration Tests")
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final String PRODUCT_ID = "35455";
    private final String BRAND_ID = "1";

    @Test
    @DisplayName("Test 1: Petición a las 10:00 del día 14")
    @WithMockUser
    void test1_requestAt10amOnDay14() throws Exception {
        mockMvc.perform(get("/prices/query")
                        .param("applicationDate", "2020-06-14 10:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.priceAmount", is(35.50)));
    }

    @Test
    @DisplayName("Test 2: Petición a las 16:00 del día 14")
    @WithMockUser
    void test2_requestAt4pmOnDay14() throws Exception {
        mockMvc.perform(get("/prices/query")
                        .param("applicationDate", "2020-06-14 16:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(2)))
                .andExpect(jsonPath("$.priceAmount", is(25.45)));
    }

    @Test
    @DisplayName("Test 3: Petición a las 21:00 del día 14")
    @WithMockUser
    void test3_requestAt9pmOnDay14() throws Exception {
        mockMvc.perform(get("/prices/query")
                        .param("applicationDate", "2020-06-14 21:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.priceAmount", is(35.50)));
    }

    @Test
    @DisplayName("Test 4: Petición a las 10:00 del día 15")
    @WithMockUser
    void test4_requestAt10amOnDay15() throws Exception {
        mockMvc.perform(get("/prices/query")
                        .param("applicationDate", "2020-06-15 10:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(3)))
                .andExpect(jsonPath("$.priceAmount", is(30.50)));
    }

    @Test
    @DisplayName("Test 5: Petición a las 21:00 del día 16")
    @WithMockUser
    void test5_requestAt9pmOnDay16() throws Exception {
        mockMvc.perform(get("/prices/query")
                        .param("applicationDate", "2020-06-16 21:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList", is(4)))
                .andExpect(jsonPath("$.priceAmount", is(38.95)));
    }

    @Test
    @DisplayName("Debe devolver 404 Not Found si no se encuentra un precio aplicable")
    @WithMockUser
    void shouldReturn404_whenNoPriceIsFound() throws Exception {
        mockMvc.perform(get("/prices/query")
                        .param("applicationDate", "2025-01-01 12:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID))
                .andExpect(status().isNotFound());
    }
}