package com.luke.http.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luke.model.Proxy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yangf on 2017/12/13/0013.
 */
public class ProxyUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static Proxy getProxy() {
        Proxy proxy = null;
        try {
            String content = JsoupUtil.getText("http://webapi.http.zhimacangku.com/getip?num=1&type=2&pro=0&city=0&yys=0&port=1&time=1&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=2&regions=");
            Map map = mapper.readValue(content, Map.class);
            List list = (List) map.get("data");
            Map result = (Map)list.get(0);
            proxy = new Proxy(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxy;
    }

    public static void main(String[] args) {
        System.out.println(getProxy());
    }
}
