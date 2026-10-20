package com.itheima.domin;

import java.util.Random;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean status;

    public User(){
        //调用createID方法生成id
        id = createID();
        //修改stasus的值为true
        status = true;


    }

    public User( String username, String password ) {
        id = createID();
        this.username = username;
        this.password = password;
        status = true;
    }
    public String createID(){
        StringBuffer sb = new StringBuffer("heima");

        Random  r = new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);
            sb.append(num);

        }

        return sb.toString();
    }
    public String getID(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
