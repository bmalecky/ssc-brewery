package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class BeerControllerIT extends BaseIT {

   // @WithMockUser("spring")
   @Test
   void initCreationFormWithUser() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.get("/beers/new")
                       .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("beers/createBeer"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
   }

    @Test
    void initCreationFormWithSpring() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/new")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/createBeer"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    void initCreationFormWithBmalecky() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/new")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/createBeer"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/findBeers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

    @Test
    void findBeersWithHttpBasic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/beers/find"))
//                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("foo", "bar"))) - will fail
//                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("beers/findBeers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("beer"));
    }

}
