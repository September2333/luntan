package com.zzj.luntan.controller;

import com.zzj.luntan.mapper.UserMapper;
import com.zzj.luntan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//认证请求授权登录
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();//访问首页，找到token cookie，拿到cookie之后去数据库中查找是否有记录，有则将user放到session中
        if(cookies!=null){
            for (Cookie cookie : cookies) {//少用户量的session和cookie应用
                if (cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        return "index";
    }
}
