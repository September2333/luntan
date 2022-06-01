package com.zzj.luntan.controller;

import com.zzj.luntan.dto.AccessTokenDTO;
import com.zzj.luntan.dto.GithubUser;
import com.zzj.luntan.mapper.UserMapper;
import com.zzj.luntan.model.User;
import com.zzj.luntan.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired              //将GithubProvider注入
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code, @RequestParam(name="state")String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser= githubProvider.getUser(accessToken);
        //githubUser不为空则为登陆成功
        if(githubUser!=null){
            //登陆成功,写cookie，session
            User user = new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);//使用UUID的形式生成UUID.randomUUID().toString()
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //github授权登录后，生成token，并将用户信息放在user对象中，存储在数据库中，并将token放在cookie中
            response.addCookie(new Cookie("token",token));

            //request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }
        else{
            //失败，重新登陆
            return "redirect:/";
        }
    }
}
