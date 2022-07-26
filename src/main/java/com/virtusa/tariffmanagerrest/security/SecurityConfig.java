// package com.virtusa.tariffmanagerrest.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @SuppressWarnings("deprecation")
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {

//     @Autowired
//     private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    
//     @Autowired
//     private CustomUserDetailsService usersDetailsServices;
    
//     private static final String[] AUTH_WHITELIST = {
//             // -- Swagger UI v2
// 			  "/v2/api-docs", 
// 			  "/swagger-resources", 
// 			  "/swagger-resources/**",
// 			  "/configuration/ui",
// 			  "/configuration/security", "/swagger-ui.html",
// 			  "/webjars/**",
			 
//             // -- Swagger UI v3 (OpenAPI)
//             "/v3/api-docs/**",
//             "/swagger-ui/**"
//     };
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
    	
//         http
//         .csrf().disable()
//         .authorizeRequests()
//         .antMatchers(AUTH_WHITELIST).permitAll()
//         .antMatchers("/public/**").permitAll()
//         .antMatchers("/employee/**").hasRole("EMPLOYEE")
//         .antMatchers("/admin/**").hasRole("ADMIN")
//         .antMatchers("/manager/**").hasRole("MANAGER")
//         .anyRequest()
//         .authenticated()
//         .and()
//         .formLogin()
//         .loginPage("/login")
//         .loginProcessingUrl("/doLogin")
//         .successHandler(customAuthenticationSuccessHandler)
//         .failureUrl("/login?error=true")
//         .permitAll()
//         .and()
//         .logout().logoutRequestMatcher(new AntPathRequestMatcher("/public/logout"))
//         .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
//         .invalidateHttpSession(true)
//         .and()
//         .sessionManagement()
//         .maximumSessions(1)
//         .maxSessionsPreventsLogin(true)
//         .expiredUrl("/login?invalid-session=true");    
       
//     }

//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(usersDetailsServices).passwordEncoder(passwordEncoder());
//     }


//     @Bean
//     public BCryptPasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder(10);
//     }
    
// }
