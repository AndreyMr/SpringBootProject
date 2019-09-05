package com.config;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder getPassowrdencoder(){
        return new BCryptPasswordEncoder(8);
    }
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //включаем авторизацию
                    .authorizeRequests()
                    //по запросам на страницу "/" разрешаем доступ (* после / обозначет что далее может лежать ещё один сегмент)
                    .antMatchers("/", "/registration", "/static/**","/activate/*").permitAll()
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
                    /*включаем запомининание сессий
                    * <dependency>
                         <groupId>org.springframework.session</groupId>
                        <artifactId>spring-session-jdbc</artifactId>
                       </dependency>
                    *
                    * spring.session.jdbc.initialize-schema=always
                    * spring.session.jdbc.table-name=SPRING_SESSION
                    */
                    .rememberMe()
                .and()
                    //включаем логаут
                    .logout()
                    // разрешение пользоваться всем
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                //проверка паролей при логине пользователей
                .passwordEncoder(passwordEncoder);

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