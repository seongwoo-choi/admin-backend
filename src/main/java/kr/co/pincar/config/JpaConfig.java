package kr.co.pincar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories( basePackages = "kr.co.pincar")
public class JpaConfig {


}
