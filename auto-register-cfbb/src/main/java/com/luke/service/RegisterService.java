package com.luke.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.http.client.JsoupUtil;
import com.luke.model.User;
import com.luke.receive.ReceiveCode;
import com.luke.utils.RandomStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangf on 2017/12/13/0013.
 */
@Service
public class RegisterService {
    private final static String REFERRER = "http://mobile.cfbb.com.cn/mobile/phone-check?username={username}&password={password}&referrer=&sf=28";
    private final static String IMAGE = "http://mobile.cfbb.com.cn/get_register_imgcode?app_sys=3&sign=1831cwt354551e0a82h&time={time}&version_code=3&mobile={mobile}";

    private static ObjectMapper mapper = new ObjectMapper();
    @Autowired
    ReceiveCode receiveCode ;

    public User create() throws IOException {
        User user = new User();
        user.setName(RandomStr.getRandomStr(6));
        user.setPassword(RandomStr.getRandomStr(6));
        receiveCode.getToken();
        user.setPhone(receiveCode.getPhone());
        return user;
    }


    public String getImage(String phone) throws IOException {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String url = IMAGE.replace("{time}", String.valueOf(dateFormater.format(date))).replace("{mobile}", phone);
        String content = JsoupUtil.mobileText(url, REFERRER);
        return content;
    }

    public static void main(String[] args) throws IOException {
        String content= new RegisterService().getImage("18792833521");
        System.out.println(content);
    }


    public static void register(){

    }

}
