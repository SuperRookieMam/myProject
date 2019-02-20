package com.yhl.oauthserver.componet.config;

import com.yhl.oauthserver.service.YhlUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {


    @Autowired
    private YhlUserDetailsService yhlUserDetailsService;

    /**
     *  重写这个方法主要是自定义url 的自定义拦截规则
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
          /*多HttpSecurity配置时必须设置这个，除最后一个外，
          因为不设置的话默认匹配所有，就不会执行到下面的HttpSecurity了*/
        //http.antMatcher("/**");//设置最顶顶级的路径

        http.authorizeRequests()//获取子路径链条，设置子路径的行为
                //.antMatchers("/static/**","/oauth/static/**").permitAll()//允许static下面的资源请求
                //.antMatchers("/","/").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated();// 指定任何经过身份验证的用户都允许url。
        /**CSRF（Cross-site request forgery）跨站请求伪造，
         * 由于目标站无token/referer限制，
         * 导致攻击者可以用户的身份完成操作达到各种目的。
         * 根据HTTP请求方式，CSRF利用方式可分为两种
                * */
        http.csrf().disable();
        //Http.ExceptionHandling 命名空间包含与捕获、处理和记录异常相关的类。
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        //验证出错登陆地址
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
        //将此http应用与那个配置
        // http.apply(new OAuth2ClientAuthenticationConfigurer(oauth2SsoFilter()));

        //注意上面时用的secret的默认实现，如果要指定filter 可自定义filter ，就不用天上面的配置勒
        /* http.addFilterBefore(new YhlBeforFilter(), ConcurrentSessionFilter.class);
        http.addFilter(new YhlBeforFilter());//添加自定义filter 还可以分前中后添加*/
    }

    //对userdetail 的 验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(yhlUserDetailsService);
    }

}
