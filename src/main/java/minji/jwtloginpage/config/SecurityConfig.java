package minji.jwtloginpage.config;

import lombok.RequiredArgsConstructor;
import minji.jwtloginpage.jwt.JwtAccessDeniedHandler;
import minji.jwtloginpage.jwt.JwtAuthenticationEntryPoint;
import minji.jwtloginpage.jwt.JwtSecurityConfig;
import minji.jwtloginpage.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity  //기본적인 웹 보완을 설정하도록 함
@EnableGlobalMethodSecurity(prePostEnabled = true)  //@PreAuthorize 어노테이션을 메소드단위로 추가하기 위함
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtSecurityConfig jwtSecurityConfig;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()

                .exceptionHandling()
                //두가지 예외를 직접 만든 예외로 대체
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않음

                .and()
                .authorizeRequests()
                .antMatchers("/jwt").permitAll()    //이 주소는 모두에게 허용
                .antMatchers("/jwt/signup").permitAll()
                .anyRequest().authenticated()                  //나머지는 인증거치도록

                .and()
                .apply(jwtSecurityConfig);

    }
}
