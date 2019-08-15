package com.config;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //включаем авторизацию
                    .authorizeRequests()
                    //по запросам на страницу "/" разрешаем доступ
                    .antMatchers("/", "/registration").permitAll()
                    // остальные запросы требуют авторизацию
                    .anyRequest().authenticated()
                .and()
                    // включаем формлогин
                    .formLogin()
                    // страница логина
                    .loginPage("/login")
                    // разрешение пользоваться всем
                    .permitAll()
                .and()
                    //включаем логаут
                    .logout()
                    // разрешение пользоваться всем
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());

        /*//выбираем jdbc
        auth.jdbcAuthentication()
        //вставляем нужный datasource (подключен из контекста через @Autowired)
                .dataSource(dataSource)
        //шифруем пароли, используем NoOpPasswordEncoder в тестовых целях
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
        //делаем запрос по полям именно в данной последовательности (определено системой)
                .usersByUsernameQuery("select username, password, active from springdb.usr where username= ?")
        //Spring получает список пользователей с ролями
                .authoritiesByUsernameQuery("SELECT u.username, ur.roles FROM springdb.usr AS u INNER JOIN springdb.user_role AS ur ON u.id = ur.user_id WHERE username = ?");*/
    }
}