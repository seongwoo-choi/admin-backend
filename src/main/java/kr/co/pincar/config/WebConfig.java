package kr.co.pincar.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

/************************************************
 * 스프링 MVC 자동구성은 WebMvcAutoConfiguration이 담당한다.
 * 이 구성이 활성화되는 조건 중에 WebMvcConfigurationSupport 타입의 빈을 찾을 수 없을 때 라는 조건이 있다.
 * 아래 내용의 @EnableWebMvc를 스프링 MVC 구성을 위한 클래스에 선언하면
 * WebMvcConfigurationSupport을 불러와 스프링 MVC를 구성한다.
 * 이 과정에서 WebMvcConfigurationSupport가 컴포넌트로 등록되어
 *
 * @ConditionalOnMissingBean(WebMvcConfigurationSupport.class) 조건을 만족시키지 못하게 되어
 *                                                             WebMvcAutoConfiguration은 비활성화 된다.
 *                                                             스프링 MVC 구성 제어권한 가지기
 * @Configuration 과 @EnableWebMvc를 함께 선언.
 * @EnableWebMvc를 선언하면 WebMvcConfigurationSupport에서 구성한 스프링 MVC 구성을 불러온다.
 *                아래에 선언한 클래스에 WebMvcConfigurer 인터페이스 구현.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate (RestTemplateBuilder builder ) {
        return builder.build();
    }

    @Bean
    MappingJackson2JsonView jsonView () {
        return new MappingJackson2JsonView();
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter () {

        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding( "UTF-8" );
        characterEncodingFilter.setForceEncoding( true );
        return characterEncodingFilter;
    }

    @Override
    public void addResourceHandlers ( final ResourceHandlerRegistry registry ) {

        registry.addResourceHandler( "/assets/**" ).addResourceLocations( "classpath:static/assets/" );
        registry.addResourceHandler( "/module/**" ).addResourceLocations( "classpath:static/module/" );
        registry.addResourceHandler( "/resource/**" ).addResourceLocations( "classpath:static/resource/" );
        registry.addResourceHandler( "/css/**" ).addResourceLocations( "classpath:static/css/" );
        registry.addResourceHandler( "/images/**" ).addResourceLocations( "classpath:static/images/" );
        registry.addResourceHandler( "/js/**" ).addResourceLocations( "classpath:static/js/" );
        registry.addResourceHandler( "/data/**" ).addResourceLocations( "classpath:static/data/" );

        // For Swagger-ui
        registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
        registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
    }

    @Override
    public void addInterceptors ( InterceptorRegistry registry ) {
        // registry.addInterceptor(new LoggingInterceptor());
        // registry.addInterceptor(new TransactionInterceptor()).addPathPatterns("/person/save/*");
    }

//    @Bean
//    public MessageSource messageSource () {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasenames( "classpath:messages/resultMessage" );
//        messageSource.setCacheSeconds( 10 ); // reload messages every 10 seconds
//        return messageSource;
//    }

//    @Bean
//    public LocaleResolver localeResolver () {
//        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
//        localeResolver.setDefaultLocale( Locale.KOREAN ); // change this
//        return localeResolver;
//    }
}
