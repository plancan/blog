package com.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.filter.JwtAuthenticationTokenFilter;

/**
 * @author 今昔
 * @description spring-security配置类
 * @date 2022/11/9 15:50
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

   @Autowired
   private AuthenticationProvider authenticationProvider;
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //解决跨域问题
        http.cors();
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                .and().authorizeRequests()
                //查询热门文章
                .antMatchers("/blog/article/getHotArticles/**").permitAll()
                //根据标签查询文章
                .antMatchers("/blog/article/getLabelArticles/**").permitAll()
                //根据关键词查询文章
                .antMatchers("/blog/article/getArticlesByKeyword/**").permitAll()
                //查询一篇文章详细信息
                .antMatchers("/blog/article/getArticle/*").hasAnyAuthority("1","2")
                //发表文章
                .antMatchers("/blog/article/addArticle").hasAnyAuthority("1","2")
                //自动保存草稿
                .antMatchers("/blog/article/autoSave").hasAnyAuthority("1","2")
                //获取草稿
                .antMatchers("/blog/article/getScript").hasAnyAuthority("1","2")
                //上传文章图片
                .antMatchers("/blog/article/uploadPicture").hasAnyAuthority("1","2")
                //删除文章
                .antMatchers("/blog/article/deleteArticle/*").hasAnyAuthority("1","2")
                //修改文章
                .antMatchers("/blog/article/updateArticle").hasAnyAuthority("1","2")
                //查看某给用户的文章
                .antMatchers("/blog/article/getUserArticles/**").hasAnyAuthority("1","2")
                //点赞或取消点赞
                .antMatchers("/blog/article/likeArticle/*").hasAnyAuthority("1","2")
                //收藏或取消收藏
                .antMatchers("/blog/article/collectArticle/*").hasAnyAuthority("1","2")
                //查看用户某一类文章
                .antMatchers("/blog/article/getUserArticlesByLabel/**").hasAnyAuthority("1","2")
                //获得当前所有可选列表
                .antMatchers("/blog/article/getLabels").permitAll()
                //用户登陆
                .antMatchers("/blog/user/login/**").permitAll()
                //退出登陆
                .antMatchers("/blog/user/logout").permitAll()
                //获得验证码
                .antMatchers("/blog/user/getVerify/**").permitAll()
                //用户注册
                .antMatchers("/blog/user/register/*").permitAll()
                //获取用户信息
                .antMatchers("/blog/user/getUserInfo/**").hasAnyAuthority("1","2")
                //用户修改自己信息
                .antMatchers("/blog/user/updateUserInfo").hasAnyAuthority("1","2")
                //用户修改邮箱号
                .antMatchers("/blog/user/updateUserEmail").hasAnyAuthority("1","2")
                //用户修改密码
                .antMatchers("/blog/user/updateUserPassword/**").hasAnyAuthority("1","2")
                //用户重置换密码
                .antMatchers("/blog/user/resetPassword/**").hasAnyAuthority("1","2")
                //用户更新图片
                .antMatchers("/blog/user/updatePicture/**").hasAnyAuthority("1","2")
                //用户进行关注
                .antMatchers("/blog/user/follow/*").hasAnyAuthority("1","2")
                //用户发表评论
                .antMatchers("/blog/comment/sendComment").hasAnyAuthority("1","2")
                .antMatchers("/blog/comment/deleteComment").hasAnyAuthority("1","2")
                //获得用户的关注列表
                .antMatchers("/blog/user/getUserFollow/**").hasAnyAuthority("1","2")
                //获得用户的收藏列表

                //聊天websocket
                .antMatchers("/client/**").permitAll()
                //聊天接口
                .antMatchers("/blog/chat/getMessages").hasAnyAuthority("1","2")
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .anyRequest().authenticated();
        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //配置异常处理器
        //认证失败
        //授权失败的处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

}