package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;
  private final PasswordEncoder passwordEncoder;

  private final RoleRepository roleRepository;


  @Override
  @Transactional
  public void run(String... args) throws Exception {
    //loadSecurityData();
  }

  private void loadSecurityData() {

//    long count = authorityRepository.count();
//    if (count > 0) {return;}
//
//    //beer auths
//    Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
//    Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
//    Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
//    Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());
//
//    // customer auths
//    Authority createCustomer = authorityRepository.save(Authority.builder().permission("customer.create").build());
//    Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
//    Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
//    Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());
//
//    // brewery auths
//    Authority createBrewery = authorityRepository.save(Authority.builder().permission("brewery.create").build());
//    Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
//    Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
//    Authority deleteBrewery = authorityRepository.save(Authority.builder().permission("brewery.delete").build());
//
//    // beer order auths
//    Authority createOrder = authorityRepository.save(Authority.builder().permission("order.create").build());
//    Authority readOrder = authorityRepository.save(Authority.builder().permission("order.read").build());
//    Authority updateOrder = authorityRepository.save(Authority.builder().permission("order.update").build());
//    Authority deleteOrder = authorityRepository.save(Authority.builder().permission("order.delete").build());
//    Authority pickupOrder = authorityRepository.save(Authority.builder().permission("order.pickup").build());
//
//    Authority createOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.create").build());
//    Authority readOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.read").build());
//    Authority updateOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.update").build());
//    Authority deleteOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.delete").build());
//    Authority pickUpOrderCustomer = authorityRepository.save(Authority.builder().permission("customer.order.pickup").build());
//
//    Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
//    Role userRole = roleRepository.save(Role.builder().name("USER").build());
//    Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
//
//    adminRole.setAuthorities(new HashSet<>(Set.of(createBeer, updateBeer, readBeer, deleteBeer,
//                                    createCustomer, updateCustomer, readCustomer, deleteCustomer,
//                                    createBrewery, updateBrewery, readBrewery, deleteBrewery,
//                                    createOrder, updateOrder, readOrder, deleteOrder, pickupOrder)));
//    userRole.setAuthorities(new HashSet<>(Set.of(readBeer)));
//    customerRole.setAuthorities(new HashSet<>(Set.of(readBeer, readCustomer, readBrewery,
//        createOrderCustomer, readOrderCustomer, updateOrderCustomer, deleteOrderCustomer, pickUpOrderCustomer)));
//
//    roleRepository.saveAll(Arrays.asList(adminRole,userRole,customerRole));
//
//    userRepository.save(User.builder()
//        .username("spring")
//        .password(passwordEncoder.encode("guru"))
//        .role(adminRole)
//        .build());
//
//    userRepository.save(User.builder()
//        .username("user")
//        .password(passwordEncoder.encode("password"))
//        .role(userRole)
//        .build());
//
//    userRepository.save(User.builder()
//        .username("bmalecky")
//        .password(passwordEncoder.encode("bill1234"))
//        .role(customerRole)
//        .build());

//    Authority authorityAdmin = Authority.builder().role("ROLE_ADMIN").build();
//    authorityAdmin = authorityRepository.save(authorityAdmin);
//
//    Authority authorityUser = Authority.builder().role("ROLE_USER").build();
//    authorityUser = authorityRepository.save(authorityUser);
//
//    Authority authorityCustomer = Authority.builder().role("ROLE_CUSTOMER").build();
//    authorityCustomer = authorityRepository.save(authorityCustomer);

//    User userSpring = User.builder()
//        .username("spring")
//        .password(passwordEncoder.encode("guru"))
//        .authority(authorityAdmin)
//        .build();
//    userRepository.save(userSpring);
//
//    User userUser = User.builder()
//        .username("user")
//        .password(passwordEncoder.encode("password"))
//        .authority(authorityUser)
//        .build();
//    userRepository.save(userUser);
//
//    User userBill = User.builder()
//        .username("bmalecky")
//        .password(passwordEncoder.encode("bill1234"))
//        .authority(authorityCustomer)
//        .build();
//    userRepository.save(userBill);

//    User bmaleckyUser = userRepository.findByUsername("bmalecky").orElse(null);
//    bmaleckyUser.getAuthorities().forEach((authority) -> System.out.println(authority.getAuthority()));
//
//    log.debug("Users Loaded: " + userRepository.count());
  }

}
