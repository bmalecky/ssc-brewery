package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
public class BeerControllerIT extends BaseIT {

  @DisplayName("Init New Form")
  @Nested
  class InitNewForm {

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamAllUsers")
    void initCreationFormAuth(String user, String pwd) throws Exception {

      mockMvc.perform(MockMvcRequestBuilders.get("/beers/new")
          .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, pwd)))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.view().name("beers/createBeer"))
          .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    void initCreationFormNoAuthentication() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/beers/new"))
          .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
  }
   // @WithMockUser("spring")
   @Test
   @Disabled
   void initCreationFormWithUser() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.get("/beers/new")
                       .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("beers/createBeer"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
   }

    @Test
    @Disabled
    void initCreationFormWithSpring() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/new")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/createBeer"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    @Disabled
    void initCreationFormWithBmalecky() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/new")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/createBeer"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    @Disabled
    void findBeers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/findBeers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    @Disabled
    void findBeersWithHttpBasic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/find"))
//                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("foo", "bar"))) - will fail
//                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/findBeers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }


  @DisplayName("Find Beers")
  @Nested
  class FindBeers {

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.BaseIT#getStreamAllUsers")
    void findBeersAuth(String user, String pwd) throws Exception {

      mockMvc.perform(MockMvcRequestBuilders.get("/beers/find")
              .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, pwd)))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.view().name("beers/findBeers"))
          .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    void findBeersNoAuthentication() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/beers/find"))
          .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
  }

}
