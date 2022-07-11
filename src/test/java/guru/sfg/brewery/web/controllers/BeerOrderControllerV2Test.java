package guru.sfg.brewery.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.brewery.bootstrap.DefaultBreweryLoader;
import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.domain.BeerOrder;
import guru.sfg.brewery.domain.Customer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class BeerOrderControllerV2Test extends BaseIT {

  public static final String API_ROOT = "/api/v2/orders/";

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  BeerOrderRepository beerOrderRepository;

  @Autowired
  BeerRepository beerRepository;

  @Autowired
  ObjectMapper objectMapper;

  Customer stPeteCustomer;
  Customer dunedinCustomer;
  Customer keyWestCustomer;
  List<Beer> loadedBeers;

  @BeforeEach
  void setUp() {
    stPeteCustomer = customerRepository.findAllByCustomerName(DefaultBreweryLoader.ST_PETE_DISTRIBUTING).orElseThrow();
    dunedinCustomer = customerRepository.findAllByCustomerName(DefaultBreweryLoader.DUNEDIN_DISTRIBUTING).orElseThrow();
    keyWestCustomer = customerRepository.findAllByCustomerName(DefaultBreweryLoader.KEY_WEST_DISTRIBUTING).orElseThrow();
    loadedBeers = beerRepository.findAll();
  }

  @Test
  void listOrdersNotAuthenticated() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @WithUserDetails(value = "spring")
  @Test
  void listOrdersAdminAuth() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @WithUserDetails(value = DefaultBreweryLoader.STPETE_USER)
  @Test
  void listOrdersCustomerStPete() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @WithUserDetails(value = DefaultBreweryLoader.DUNEDIN_USER)
  @Test
  void listOrdersCustomerDunedin() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Transactional
  @Test
  void getByOrderIdNotAuthentication() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + "/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Transactional
  @WithUserDetails("spring")
  @Test
  void getByOrderIdAdminRole() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + "/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Transactional
  @WithUserDetails(DefaultBreweryLoader.STPETE_USER)
  @Test
  void getByOrderIdCustomerAuth() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + "/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Transactional
  @WithUserDetails(DefaultBreweryLoader.DUNEDIN_USER)
  @Test
  void getByOrderIdCustomerNotAuth() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + "/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }



}
