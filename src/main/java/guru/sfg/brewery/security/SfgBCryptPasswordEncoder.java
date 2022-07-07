package guru.sfg.brewery.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SfgBCryptPasswordEncoder extends BCryptPasswordEncoder {

    public SfgBCryptPasswordEncoder() {
       super(15);
    }
}
