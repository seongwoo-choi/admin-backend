package kr.co.pincar.config;

import kr.co.pincar.utils.JwtAuthenticationFilter;
import kr.co.pincar.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 필터 등록하고 필요한 부분 채워 넣기
    private final JwtTokenProvider jwtTokenProvider;


    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/api/**").permitAll()
//                .authenticated()
                .antMatchers("/login/**","/join/**").permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");    //.addResourceLocations( "classpath:static/assets/" );
        web.ignoring().antMatchers("/css/**");    //.addResourceLocations( "classpath:static/css/" );
        web.ignoring().antMatchers("/images/**");    //.addResourceLocations( "classpath:static/images/" );
        web.ignoring().antMatchers("/js/**");        //.addResourceLocations( "classpath:static/js/" );

        // For Swagger-ui
        web.ignoring().antMatchers("/swagger**");
        web.ignoring().antMatchers("/v2/api-docs/**");
    }

}