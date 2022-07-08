package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final AuthorityRepository authorityRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    loadSecurityData();
  }

  private void loadSecurityData() {

    long count = authorityRepository.count();
    if (count > 0) {return;}

    Authority authorityAdmin = Authority.builder().role("ROLE_ADMIN").build();
    authorityAdmin = authorityRepository.save(authorityAdmin);

    Authority authorityUser = Authority.builder().role("ROLE_USER").build();
    authorityUser = authorityRepository.save(authorityUser);

    Authority authorityCustomer = Authority.builder().role("ROLE_CUSTOMER").build();
    authorityCustomer = authorityRepository.save(authorityCustomer);

    User userSpring = User.builder()
        .username("spring")
        .password(passwordEncoder.encode("guru"))
        .authority(authorityAdmin)
        .build();
    userRepository.save(userSpring);

    User userUser = User.builder()
        .username("user")
        .password(passwordEncoder.encode("password"))
        .authority(authorityUser)
        .build();
    userRepository.save(userUser);

    User userBill = User.builder()
        .username("bmalecky")
        .password(passwordEncoder.encode("bill1234"))
        .authority(authorityCustomer)
        .build();
    userRepository.save(userBill);

    log.debug("Users Loaded: " + userRepository.count());
  }

}
