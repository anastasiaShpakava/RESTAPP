package com.leverX.blog.configuration;

import com.leverX.blog.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author Shpakova A.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //откл.(XSS-атака)
                .csrf().disable()  //откл. (чтобы не предоставл. юзера и пароль при входе)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //настройка для сессииб управляет сессией юзера в спринг секьюрити.Так как я буду авторизировать пользователя по токену, мне не нужно создавать и хранить для него сессию. Поэтому я указал STATELESS.
                .and()
                .authorizeRequests() //все запросы должны проходить через Spring Security:
                .antMatchers("/admin/*").hasRole("ADMIN")  // какие урл адреса будут доступны для определенной роли, а какие нет
                .antMatchers("/user/*").hasRole("USER")
                .antMatchers("/register", "/auth").permitAll()  //доступны всем
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // В этом фильтре я делаю логику, которая будет доставать данные из токена, получать юзера из базы данных и ложить данные пользователя и его роли в Spring Security, чтобы спринг мог дальше выполнять свою работу и определять доступен ли определенный адрес для пользователя.
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
