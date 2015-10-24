package spring.sample.rest.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import spring.sample.rest.security.CsrfTokenHeaderFilter;
import spring.sample.rest.security.RESTAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("*.js", "*.css", "*.html");
        web.ignoring().antMatchers("/**/favicon.ico");
        web.ignoring().antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources", "/v2/api-docs");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(new CsrfTokenHeaderFilter(), CsrfFilter.class)
            .csrf()
            .disable() // or .and() // need to add the csrf token in every request header
            .httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/**")
            .authenticated()
            .anyRequest()
            .permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint);

    }
}
