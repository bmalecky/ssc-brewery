package guru.sfg.brewery.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.brewery.bootstrap.DefaultBreweryLoader;
import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.domain.BeerOrder;
import guru.sfg.brewery.domain.Customer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.web.model.BeerOrderDto;
import guru.sfg.brewery.web.model.BeerOrderLineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class BeerOrderControllerTest extends BaseIT {

  public static final String API_ROOT = "/api/v1/customers/";

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
  void createOrderNotAuthenticated() throws Exception {
    BeerOrderDto beerOrderDto = buildOrderDto(stPeteCustomer, loadedBeers.get(0).getId());
    mockMvc.perform(MockMvcRequestBuilders.post(API_ROOT + stPeteCustomer.getId() + "/orders")
        .accept(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(beerOrderDto)))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @WithUserDetails("spring")
  @Test
  void createOrderUserAdmin() throws Exception {
    BeerOrderDto beerOrderDto = buildOrderDto(stPeteCustomer, loadedBeers.get(0).getId());

    mockMvc.perform(MockMvcRequestBuilders.post(API_ROOT + stPeteCustomer.getId() + "/orders")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beerOrderDto)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @WithUserDetails(DefaultBreweryLoader.STPETE_USER)
  @Test
  void createOrderUserAuthCustomer() throws Exception {
    BeerOrderDto beerOrderDto = buildOrderDto(stPeteCustomer, loadedBeers.get(0).getId());

    mockMvc.perform(MockMvcRequestBuilders.post(API_ROOT + stPeteCustomer.getId() + "/orders")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beerOrderDto)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @WithUserDetails(DefaultBreweryLoader.KEYWEST_USER)
  @Test
  void createOrderUserNOTAuthCustomer() throws Exception {
    BeerOrderDto beerOrderDto = buildOrderDto(stPeteCustomer, loadedBeers.get(0).getId());

    mockMvc.perform(MockMvcRequestBuilders.post(API_ROOT + stPeteCustomer.getId() + "/orders")
            .accept(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beerOrderDto)))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  void listOrdersNotAuthenticated() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() + "/orders"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @WithUserDetails(value = "spring")
  @Test
  void listOrdersAdminAuth() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() + "/orders"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @WithUserDetails(value = DefaultBreweryLoader.STPETE_USER)
  @Test
  void listOrdersCustomerAuth() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() + "/orders"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @WithUserDetails(value = DefaultBreweryLoader.DUNEDIN_USER)
  @Test
  void listOrdersCustomerNOTAuth() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() + "/orders"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  void listOrdersNoAuthentication() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId()))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Transactional
  @Test
  void getByOrderIdNotAuthentication() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() +"/orders/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Transactional
  @WithUserDetails("spring")
  @Test
  void getByOrderIdAdminRole() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() +"/orders/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Transactional
  @WithUserDetails(DefaultBreweryLoader.STPETE_USER)
  @Test
  void getByOrderIdCustomerAuth() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() +"/orders/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Transactional
  @WithUserDetails(DefaultBreweryLoader.DUNEDIN_USER)
  @Test
  void getByOrderIdCustomerNotAuth() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.get(API_ROOT + stPeteCustomer.getId() +"/orders/" + beerOrder.getId()))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Transactional
  @Test
  void pickUpOrderNotAuth() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.put(API_ROOT + stPeteCustomer.getId() +
            "/orders/" + beerOrder.getId() + "/pickup"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Transactional
  @WithUserDetails("spring")
  @Test
  void pickUpOrderAdminUser() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.put(API_ROOT + stPeteCustomer.getId() +
            "/orders/" + beerOrder.getId() + "/pickup"))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Transactional
  @WithUserDetails(DefaultBreweryLoader.STPETE_USER)
  @Test
  void pickUpOrderCustomerUserAuth() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.put(API_ROOT + stPeteCustomer.getId() +
            "/orders/" + beerOrder.getId() + "/pickup"))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Transactional
  @WithUserDetails(DefaultBreweryLoader.DUNEDIN_USER)
  @Test
  void pickUpOrderCustomerUserNOT_AUTH() throws Exception {
    BeerOrder beerOrder = stPeteCustomer.getBeerOrders().stream()
        .findFirst()
        .orElseThrow();

    mockMvc.perform(MockMvcRequestBuilders.put(API_ROOT + stPeteCustomer.getId() +
            "/orders/" + beerOrder.getId() + "/pickup"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }




  private BeerOrderDto buildOrderDto(Customer customer, UUID beerId) {
    List<BeerOrderLineDto> orderLines = Arrays.asList(BeerOrderLineDto.builder()
        .id(UUID.randomUUID())
        .beerId(beerId)
        .orderQuantity(5)
        .build());

    return BeerOrderDto.builder()
        .customerId(customer.getId())
        .customerRef("123")
        .orderStatusCallbackUrl("http://example.com")
        .beerOrderLines(orderLines)
        .build();
  }

}
