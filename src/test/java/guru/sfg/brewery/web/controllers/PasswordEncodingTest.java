package guru.sfg.brewery.web.controllers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.ldap.LdapServerBeanDefinitionParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTest {

    final static String PASSWORD = "password";

    @Test
    void testBcrypt15() {
        PasswordEncoder bcrypt15 = new BCryptPasswordEncoder(15);

        System.out.println(bcrypt15.encode(PASSWORD));
        System.out.println(bcrypt15.encode(PASSWORD));
    }

    @Test
    void testBCrypt() {
        // the larger the strength parameter the more work will have to be done (exponentially) to hash the passwords.
        int strength = 10;
        PasswordEncoder bcrypt = new BCryptPasswordEncoder(strength);

        System.out.println("BCrypt " + bcrypt.encode(PASSWORD));
        System.out.println("BCrypt " + bcrypt.encode(PASSWORD));

        System.out.println("BCrypt guru " + bcrypt.encode("guru"));
    }

    @Test
    void testSha256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();

        //random salt
        System.out.println("sha256 " + sha256.encode(PASSWORD));
        System.out.println("sha256 " + sha256.encode(PASSWORD));

        System.out.println("sha256 bill1234 " + sha256.encode("bill1234"));
    }

    @Test
    void testLdap() {
        PasswordEncoder ldap = new LdapShaPasswordEncoder();

        // uses random salt
        System.out.println("ldap encoder " + ldap.encode(PASSWORD));
        System.out.println("ldap encoder " + ldap.encode(PASSWORD));

        String encodedPassword = ldap.encode(PASSWORD);
        System.out.println("encodedPassword " + encodedPassword);

        Assertions.assertTrue(ldap.matches(PASSWORD, encodedPassword));
    }

    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();

        System.out.println("noOp encoder + " + noOp.encode(PASSWORD));
    }

    @Test
    void hashingExample() {
        System.out.println("password hashed1: " + DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
        System.out.println("password hashed2: " + DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

        String salted = PASSWORD + "ThisIsMySALTVALUE";
        System.out.println("password with salt: " + DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
