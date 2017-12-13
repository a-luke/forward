package com.luke.faced;

import com.luke.model.User;
import com.luke.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by yangf on 2017/9/4/0004.
 */

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 主页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public User create() throws IOException {
        return registerService.create();
    }
 /**
     * 注册
     *
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public String register() {
        return "123123";
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @RequestMapping("/getSecurityCode")
    @ResponseBody
    public String getSecurityCode(String phone) throws IOException {
        return registerService.getImage(phone);
    }

}
