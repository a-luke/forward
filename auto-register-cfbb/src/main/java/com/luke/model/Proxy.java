package com.luke.model;

import java.util.Map;

/**
 * Created by yangf on 2017/12/13/0013.
 */
public class Proxy {

    public Proxy(Map map){
        ip = String.valueOf(map.get("ip"));
        port = Integer.valueOf(String.valueOf(map.get("port")));
    }

    private String ip;
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Proxy{" + "ip='" + ip + '\'' + ", port=" + port + '}';
    }
}
