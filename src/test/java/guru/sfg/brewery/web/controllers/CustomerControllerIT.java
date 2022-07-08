package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
public class CustomerControllerIT extends BaseIT {

  @ParameterizedTest(name = "#{index} with [{arguments}]")
  @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamAdminCustomer")
  void testListCustomersAuthentication(String user, String pwd) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/customers")
        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, pwd)))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testListCustomersUserRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/customers")
            .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  void testListCustomersNoAuthentication() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @DisplayName("Add Customers")
  @Nested
  class AddCustomers {

    @Rollback
    @Test
    void processCreationFormAdminRole() throws Exception {

      mockMvc.perform(MockMvcRequestBuilders.post("/customers/new")
          .param("customerName", "Foo Customer")
          .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
          .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Rollback
    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamNotAdmin")
    void processCreationFormNotAdminRole(String user, String pwd) throws Exception {

      mockMvc.perform(MockMvcRequestBuilders.post("/customers/new")
              .param("customerName", "Foo Customer2")
              .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, pwd)))
          .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Rollback
    @Test
    void processCreationFormNoAuthentication() throws Exception {

      mockMvc.perform(MockMvcRequestBuilders.post("/customers/new")
              .param("customerName", "Foo Customer2"))
          .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

  }
}
