package com.luke.receive;

/**
 * Created by yangf on 2017/12/13/0013.
 */
public class Receive {

    public Receive(){}
    public Receive(String content){
        String[] arr = content.split("\\|");
        status = Integer.valueOf(arr[0]);
        message = arr[1];
    }

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        if(message.contains("验证码")){
            message = message.substring(message.indexOf("为：") + 2, message.indexOf("，"));
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess(){
        return status == 1;
    }

    public static void main(String[] args) {
        Receive receive = new Receive("1|qweqwasdfasdf");
        receive.setMessage("【财富中国】您的手机验证码为：11545，请在页面中输入以完成验证，验证码5分钟内有效。(来自106909993658)");
        System.out.println(receive.getMessage());
    }
}
