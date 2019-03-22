package com.test.audit;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("test");
        // Use below commented code when will use Spring Security.
        //https://www.javaguides.net/2018/09/spring-data-jpa-auditing-with-spring-boot2-and-mysql-example.html
    }
}