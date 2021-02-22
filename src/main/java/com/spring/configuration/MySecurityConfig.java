package com.spring.configuration;

import com.mchange.v2.c3p0.DataSources;

import com.spring.webapp.dao.securityDAO.UserDAO;
import com.spring.webapp.service.securityService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.spring.configuration.AuthProvider;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@ComponentScan(basePackages = "com.spring")
@Configuration
@EnableOAuth2Client
@EnableWebSecurity
//@EnableOAuth2Sso
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    private AuthProvider authProvider;

    //    @Autowired
//    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//
//    @Autowired
//   public MySecurityConfig(@Lazy PasswordEncoder passwordEncoder){
//        this.passwordEncoder = passwordEncoder;
//   }

    // @Qualifier("oauth2ClientContext")
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Primary
    @Bean
    public OAuth2ClientContext singletonClientContext() {
        return new DefaultOAuth2ClientContext();
    }

    @Autowired
    private UserDAO userDAO;

    @Value("242519953141-4jqbtrira03mgqussnnnu872vej0r908.apps.googleusercontent.com")
    String clientId;

    @Value("C0mgWM_nA2VF4GFtBijkmT8t")
    String clientSecret;

    @Value("https://www.googleapis.com/oauth2/v4/token")
    String accessTokenUri;

    @Value("https://accounts.google.com/o/oauth2/v2/auth")
    String userAuthorizationUri;


    @Bean
    // @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails google() {
        AuthorizationCodeResourceDetails authorizationCodeResourceDetails = new AuthorizationCodeResourceDetails();
        authorizationCodeResourceDetails.setClientId(clientId);
        authorizationCodeResourceDetails.setClientSecret(clientSecret);
        authorizationCodeResourceDetails.setAccessTokenUri(accessTokenUri);
        authorizationCodeResourceDetails.setUserAuthorizationUri(userAuthorizationUri);
        authorizationCodeResourceDetails.setClientAuthenticationScheme(AuthenticationScheme.form);
        List<String> scopeList = new ArrayList<>();
        scopeList.add("openid");
        scopeList.add("email");
        scopeList.add("profile");
        authorizationCodeResourceDetails.setScope(scopeList);
        return authorizationCodeResourceDetails;
    }

    @Value("https://www.googleapis.com/oauth2/v3/userinfo")
    String userInfoUri;

    @Value("true")
    boolean preferTokenInfo;


    @Bean
    @Primary
    //  @ConfigurationProperties("google.resource")
    public ResourceServerProperties googleResource() {
        ResourceServerProperties resourceServerProperties = new ResourceServerProperties();
        //   resourceServerProperties.setTokenInfoUri(userInfoUri);
        resourceServerProperties.setPreferTokenInfo(preferTokenInfo);
        resourceServerProperties.setUserInfoUri(userInfoUri);
        return resourceServerProperties;
    }

//    @Bean
//    public ResourceServerTokenServices tokenService() {
//        RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setClientId(clientId);
//        tokenServices.setClientSecret(clientSecret);
//        tokenServices.setCheckTokenEndpointUrl("http://127.0.0.1:8080/oauth/check_token");
//        return tokenServices;
//    }

    @Bean
    public FilterRegistrationBean oAuth2ClientFilterRegistration(OAuth2ClientContextFilter oAuth2ClientContextFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(oAuth2ClientContextFilter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oAuth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        CustomUserInfoTokenServices tokenServices = new CustomUserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        tokenServices.setUserRepo(userDAO);
        googleFilter.setTokenServices(tokenServices);
        googleFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler() {
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                this.setDefaultTargetUrl("http://localhost:8080/greet");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        });
        //  tokenServices.setPasswordEncoder(passwordEncoder());
        return googleFilter;
    }


//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

  /*  @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration", "/users", "/updateUser").hasRole("ADMIN")
                //.antMatchers("/").permitAll()
                .antMatchers("/", "/viewPatient", "/patients").hasAnyRole("DOCTOR", "NURSE", "ADMIN")
                .antMatchers("/doctor/updateTreatmentInfo").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers("/nurse/showAllTreatments", "nurse/cancelStatus",
                        "/nurse/showNearestHourTreatments", "/nurse/").hasAnyRole("NURSE", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }


}
