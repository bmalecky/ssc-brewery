package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;


@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests {

        public Beer beerToDelete() {
            Random rand = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                .beerName("Delete Me Beer")
                .beerStyle(BeerStyleEnum.IPA)
                .minOnHand(12)
                .quantityToBrew(200)
                .upc(String.valueOf(rand.nextInt(99999999)))
                .build());
        }
        @Test
        void deleteBeerHttpBasicAdmin() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        }



        @Test
        void deleteBeerUrlParameters() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .param("apiKey", "spring").param("apiSecret", "guru"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void deleteBeerUrlParametersBadCredentials() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .param("apiKey", "spring").param("apiSecret", "guruxxx"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }

        @Test
        void deleteBeerBadCredentials() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "spring")
                    .header("Api-Secret", "guruXXXX"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }
        @Test
        void deleteBeer() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "spring")
                    .header("Api-Secret", "guru"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        }
        @Test
        void deleteBeerHttpBasic() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

        @Test
        void deleteBeerNoAuth() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/" + beerToDelete().getId()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }
    }


    @Test
    void findBeersAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersCustomerRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersNoAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void findBeersByIdAdminRole() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + beer.getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersByIdUserRole() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + beer.getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersByIdCustomerRole() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + beer.getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersByIdNoAuthentication() throws Exception {
        Beer beer = beerRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + beer.getId()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void findBeersByUpcAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/0631234200036")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersByUpcUserRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/0631234200036")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "password")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersByUpcCustomerRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/0631234200036")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("bmalecky", "bill1234")))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findBeersByUpcNoAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/0631234200036"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


//    @Test
//    void findBeerFormAdminRole() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/beers").param("beerName", "")
//                .with(SecurityMockMvcRequestPostProcessors.httpBasic("spring", "guru")))
//            .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//


}
