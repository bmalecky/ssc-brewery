package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerBadCredentials() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guruXXXX"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
    @Test
    void deleteBeer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guru"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void deleteBeerHttpBasic() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
                        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }




    @Test
    void findBeers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersByUpc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/0631234200036"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



}
