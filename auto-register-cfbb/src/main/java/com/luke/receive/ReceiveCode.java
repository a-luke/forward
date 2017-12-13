package com.luke.receive;

import com.luke.http.client.JsoupUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by yangf on 2017/12/13/0013.
 */
@Service
public class ReceiveCode {

    private final static String GET_TOKEN_URL = "http://api.xingjk.cn/api/do.php?action=loginIn&name={name}&password={password}";
    private final static String GET_PHONE_URL = "http://api.xingjk.cn/api/do.php?action=getPhone&sid={sid}&token={token}";
    private final static String SECURITY_CODE_URL = "http://api.xingjk.cn/api/do.php?action=getMessage&phone={phone}&sid={sid}&token={token}";

    private final static String NAME = "api-doszhf5y";
    private final static String PASSWORD = "asd12580";
    private final static String SID = "27952";

    private static String TOKEN = "";

    public void getToken() throws IOException {
        String url = GET_TOKEN_URL.replace("{name}", NAME).replace("{password}", PASSWORD);
        TOKEN = getResult(url, "用户token获取失败");
    }

    public String getPhone() throws IOException {
        String url = GET_PHONE_URL.replace("{sid}", SID).replace("{token}", TOKEN);
        return getResult(url, "用户Phone获取失败");
    }

    public String getSecurityCode(String phone) throws IOException {
        String url = SECURITY_CODE_URL.replace("{phone}", phone).replace("{token}", TOKEN).replace("{sid}", SID);
        return getResult(url, "用户验证码获取失败");
    }

    private String getResult(String url, String info) throws IOException {
        String content = JsoupUtil.getText(url);
        Receive receive = new Receive(content);
        if (receive.isSuccess()) {
            return receive.getMessage();
        } else {
            throw new RuntimeException("ReceiveCode " + info);
        }
    }
}
