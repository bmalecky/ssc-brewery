package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
public class BreweryControllerIT extends BaseIT {

   // @WithMockUser("spring")
   @Test
   void testListBreweriesUserRole() throws Exception {
     mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries")
             .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
         .andExpect(MockMvcResultMatchers.status().isForbidden());
   }

  @Test
  void testListBreweriesAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries")
            .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("breweries"))
        .andExpect(MockMvcResultMatchers.view().name("breweries/index"));
  }

  @Test
  void testListBreweriesCustomerRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries")
            .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeExists("breweries"))
        .andExpect(MockMvcResultMatchers.view().name("breweries/index"));
  }

  @Test
  void testListBreweriesNotAuthenticated() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  void testApiListBreweriesUserRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/brewery/api/v1/breweries")
            .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  void testApiListBreweriesAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/brewery/api/v1/breweries")
            .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testApiListBreweriesCustomerRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/brewery/api/v1/breweries")
            .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testApiListBreweriesNotAuthenticated() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/brewery/api/v1/breweries"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }


}
