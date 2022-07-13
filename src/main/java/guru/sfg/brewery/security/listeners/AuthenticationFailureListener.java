package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationFailureListener {

  private final LoginFailureRepository loginFailureRepository;
  private final UserRepository userRepository;

  @EventListener
  public void listen(AuthenticationFailureBadCredentialsEvent event) {
    log.debug("User has bad credentials");

    if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
      UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
      LoginFailure.LoginFailureBuilder builder = LoginFailure.builder();

      if (token.getPrincipal() instanceof String) {
        String username = (String) token.getPrincipal();
        log.debug("User name entered: " + token.getPrincipal());
        builder.username(username);

        // look up user
        userRepository.findByUsername(username).ifPresent(builder::user);
      }

      if (token.getDetails() instanceof WebAuthenticationDetails) {
        WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
        log.debug("Source IP: " + details.getRemoteAddress());
        builder.sourceIp(details.getRemoteAddress());
      }

      LoginFailure loginFailure = loginFailureRepository.save(builder.build());
      log.debug("Login Failure saved. Id: " + loginFailure.getId());

      if (loginFailure.getUser() != null) {
        lockUserAccount(loginFailure.getUser());
      }
    }
  }

  private void lockUserAccount(User user) {

    List<LoginFailure> failures = loginFailureRepository
        .findAllByUserAndCreatedDateIsAfter(user, Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

    if (failures.size()>3) {
      log.debug("Locking User Account...");
      user.setAccountNonLocked(false);
      userRepository.save(user);
    }
  }

}
