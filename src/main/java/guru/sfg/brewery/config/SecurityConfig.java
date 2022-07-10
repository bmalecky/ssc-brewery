package guru.sfg.brewery.config;

import guru.sfg.brewery.security.JpaUserDetailsService;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestUrlParameterAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestUrlParameterAuthFilter restUrlParameterAuthFilter(AuthenticationManager authenticationManager) {
        RestUrlParameterAuthFilter filter = new RestUrlParameterAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        http.addFilterBefore(restUrlParameterAuthFilter(authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

        http
            .authorizeRequests((authorize) -> {
                authorize.antMatchers("/h2-console/**").permitAll() // do not use in production!!
                    .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll();
                    //.antMatchers(HttpMethod.GET, "/beers/find", "/beers*").hasAnyRole("ADMIN", "CUSTOMER", "USER")
                    //.antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                    //.mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                    //.mvcMatchers(HttpMethod.GET, "/brewery/**").hasAnyRole("CUSTOMER", "ADMIN");
                    //.mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll()
                    //.mvcMatchers(HttpMethod.GET, "/api/v1/beer*/**").hasAnyRole("ADMIN", "CUSTOMER", "USER");
            })
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin().and()
            .httpBasic();

        // h2 console config
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // return new StandardPasswordEncoder();
        //  return new BCryptPasswordEncoder(15);
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

 //   @Autowired
 //   JpaUserDetailsService jpaUserDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(jpaUserDetailsService)
//            .passwordEncoder(passwordEncoder());
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("spring")
//                .password("{bcrypt}$2a$10$h/1C7ttrZfCLgoC7Kol6ZeIaglb9vhE.RfvNQ/e73mJETOAc5P/pi")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{ldap}{SSHA}gh1hj2HniQauAlYqtUqRYw3UMKUQLqsZKIWcSA==") // LDAP
//                //.password("{sha256}d6a33f3b2776aa8af96daf6c7cc067d62a9623ddd8a60a066f7ef0d0e5b4473d0af71e3748872003") // sha256
//                //.password("{bcrypt15}$2a$15$DydIA0K1py8.o4ma.DmnreJByG3sWg7VSObjOGxLIFVVONJI9CNxm") // BCrypt
//                .roles("USER")
//                .and()
//                .withUser("bmalecky")
//                .password("{sha256}afec59eb607cc4df4cf059c274a2da2efee19596a10f757e9306386a808221993daa853f852fafe5")
//                .roles("CUSTOMER");
//
//        auth.inMemoryAuthentication().withUser("scott").password("{noop}tiger").roles("CUSTOMER");
//    }

    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }




}
