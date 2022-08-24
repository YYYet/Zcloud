//package com.chengzzz.zcloud.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    private AuthenticationSuccessHandler successHandler;
//
//    @Autowired
//    private AuthenticationFailHandler failHandler;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());//加密
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
//                .authorizeRequests();
//
//        registry.and()
//        //表单登录方式
//                .formLogin()
//                .permitAll()
//        //成功处理类
//                .successHandler(successHandler)
//        //失败
//                .failureHandler(failHandler)
//                .and()
//                .logout()
//                .permitAll()
//                .and()
//                .authorizeRequests()
//        //任何请求
//                .anyRequest()
//        //需要身份认证
//                .authenticated()
//                .and()
//        //关闭跨站请求防护
//                .csrf().disable()
//       // 前后端分离采用JWT 不需要session
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//        //添加JWT过滤器 除已配置的其它请求都需经过此过滤器
//            .addFilter(new JWTAuthenticationFilter(authenticationManager(), 7));
//    }
//
//}
