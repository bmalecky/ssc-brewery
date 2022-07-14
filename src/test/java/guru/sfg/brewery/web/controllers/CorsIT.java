package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
public class CorsIT extends BaseIT {

  @WithUserDetails("spring")
  @Test
  void findBeersAuth() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer")
            .header("Origin", "https://springframework.guru"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", "*"));
  }

  @Test
  void findBeersPreflight() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.options("/api/v1/beer")
            .header("Origin", "https://springframework.guru")
            .header("Access-Control-Request-Method", "GET"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", "*"));
  }

  @Test
  void postBeersPreflight() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.options("/api/v1/beer")
            .header("Origin", "https://springframework.guru")
            .header("Access-Control-Request-Method", "POST"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", "*"));
  }

  @Test
  void putBeersPreflight() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.options("/api/v1/beer")
            .header("Origin", "https://springframework.guru")
            .header("Access-Control-Request-Method", "PUT"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", "*"));
  }

  @Test
  void deleteBeersPreflight() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.options("/api/v1/beer/1234")
            .header("Origin", "https://springframework.guru")
            .header("Access-Control-Request-Method", "DELETE"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", "*"));
  }

}