//package com.bn.biteNest;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.web.SecurityFilterChain;
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////  @Bean
////  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////      http
////          .authorizeHttpRequests(authorize -> authorize
////              .anyRequest().permitAll()  // ��� ��û ���
////          )
////          .formLogin().disable();  // �⺻ �α��� ȭ�� ��Ȱ��ȭ
////      return http.build();
////  }
////}
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorize -> authorize
//                .anyRequest().permitAll()
//            )
//            .formLogin().disable();  // �⺻ �α��� ȭ�� ��Ȱ��ȭ
//        return http.build();
//    }
//}