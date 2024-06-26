package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_C > ROLE_B\n"+"ROLE_B > ROLE_A");
        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        특정 경로에 대한 인가 작업
        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/lgoin").permitAll()
                        .requestMatchers("/").hasRole("A")
                        .requestMatchers("/manager").hasAnyRole("B")
                        .requestMatchers("/admin").hasAnyRole("C")
                        .anyRequest().authenticated()
                );
//        커스텀 로그인 페이지(폼로그인
        http
                .formLogin((auth)-> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );
/*//        http basic 방식 로그인
        http
                .httpBasic(Customizer.withDefaults());*/

/*        csrfFilter를 통해 post,put,delete 요청에 대해서 토큰 검증을 진행한다
        csrf사용시 logout을 put방식으로 해야한다
        앱에서 사용하는 api 서버의 경우 보통 세션을 stateless로 관리해서 csrf 설정을 진행하지 않고 jwt 방식 사용*/
        http.csrf((auth)->auth.disable());

/*//        csrf get logout처리
        http.
                logout((auth)-> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));*/

/*//        동일한 아이디로 다중 로그인을 진행할 경우 세션 통제를 통해 진행
        http
                //메소드를 통한 설정
                .sessionManagement((auth) -> auth
                        //하나의 아이디에 대한 다중 로그인 허용 개수
                        .maximumSessions(1)
                        //다중 로그인 개수를 초과하였을 경우 처리방법 true 초과시 새로운 로그인 차단, false 초과시 기존 세션 하나 삭제
                        .maxSessionsPreventsLogin(true));
//        세션 고정 공격을 보호하기 위한 로그인 성공시 세션 설정 방법은 sessionManagement() 메소드의 sessionFixation() 메소드를 통해 설정 가능
//        .none 로그인 시 세션 정보 변경안함 .newSession 로그인 시 세션 새로 생성 .changeSessionId() 로그인 시 동일한 세션에 대한 id 변경
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());*/
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("C")
                .build();

        return new InMemoryUserDetailsManager(user1);

    }
}
