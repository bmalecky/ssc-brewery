package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

public class BaseIT {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

//    @MockBean
//    BeerRepository beerRepository;
//
//    @MockBean
//    BeerInventoryRepository beerInventoryRepository;
//
//    @MockBean
//    BreweryService breweryService;
//
//    @MockBean
//    CustomerRepository customerRepository;
//
//    @MockBean
//    BeerService beerService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    public static Stream<Arguments> getStreamAllUsers() {
        return Stream.of(Arguments.of("spring", "guru"),
            Arguments.of("user", "password"),
            Arguments.of("bmalecky", "bill1234"));
    }

    public static Stream<Arguments> getStreamNotAdmin() {
        return Stream.of(Arguments.of("user", "password"),
            Arguments.of("bmalecky", "bill1234"));
    }

    public static Stream<Arguments> getStreamAdminCustomer() {
        return Stream.of(Arguments.of("spring", "guru"),
            Arguments.of("bmalecky", "bill1234"));
    }

}
