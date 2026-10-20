package com.itheima.ui;

import com.itheima.domin.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    //这个类表示的就是登陆注册的主页面（控制台的方式）
    public void start(){
        System.out.println("游戏的登陆注册页面打开了");
        ArrayList<User> list = new ArrayList<>();

        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");


            Scanner sc = new Scanner(System.in);
            String choice = sc.next();

            switch (choice){
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" ->{
                    System.out.println("您选择了退出");
                    System.exit(0);
                }
                default -> System.out.println("您选择了错误的操作");
            }
        }
    }

    public void login(ArrayList<User> list){
        System.out.println("您选择了登录");
        //判断用户是否存在
        //不存在 提示用户先注册
        //存在：禁用 提示用户联系客服
        //存在：验证验证码
        //验证密码是否正确

        Scanner sc = new Scanner(System.in);

        //1.录入用户名
        System.out.println("请输入用户名：");
        String username = sc.next();

        //2.判断用户是否存在
        if (!contains(list, username)) {
            System.out.println("用户名不存在，请先注册");
            return;
        }

        //3.找到这个用户对象
        int index = findIndex(list, username);
        User u = list.get(index);

        //4.判断用户是否禁用
        if (!u.isStatus()) {
            System.out.println("该账号已被禁用，请联系客服");
            return;
        }
        //5.验证验证码




        //6.录入密码并校验


        String rightPassword = u.getPassword();
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码：");
            String password = sc.next();
            while (true)  {
                String rightcode = getCode();
                System.out.println("正确的验证码是：" + rightcode);
                System.out.println("请输入验证码：" );
                String inputCode = sc.next();

                if (rightcode.equalsIgnoreCase(inputCode)) {
                    System.out.println("验证码正确");
                    break;
                }else{
                    System.out.println("验证码错误，请重新输入");
                    continue;
                }

            }
            if (rightPassword.equals(password)) {
                System.out.println("密码正确");
                break;
            }else{
                System.out.println("密码错误，请重新输入");
                if(i == 2){
                    System.out.println("3次密码都错误，登录失败");
                    return;
                }else{
                    System.out.print("密码错误，还剩" + (2 - i) + "次机会");
                }
                }

        }

        //7.登录成功
        System.out.println("登录成功！欢迎你，" + username);
        //创建FightingGame对象,并调用方法启动游戏
        FightingGame fg = new FightingGame();
        fg.startGame(username);

    }

    public void register(ArrayList<User> list){
        //什么叫做注册
        //键盘录入用户名和密码 ---> 生成一个User对象 ---> 加入到list集合中

        Scanner sc = new Scanner(System.in);
        User u = new User();

        //1.录入并校验用户名
        while (true) {
            System.out.println("请输入用户名：");
            String username = sc.next();

            //长度校验
            if (!checkLen(3, 16, username)) {
                continue;
            }

            //字母数字组成，不能纯数字
            if (!checkUsername(username)) {
                System.out.println("用户名只能由字母、数字组成，不能是纯数字");
                continue;
            }

            //唯一性校验
            if (contains(list, username)) {
                System.out.println("用户名已存在，请重新输入");
                continue;
            }
        //当代码执行到这里 表示用户名username长度，内容，唯一性校验都通过了
            u.setUsername(username);
            break;
        }

        //2.录入并校验密码
        while (true) {
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请再次输入密码确认：");
            String password2 = sc.next();

            if (!checkLen(3, 8, password1)) {
                System.out.println("密码长度必须在3-8位之间");
                continue;
            }
            //只能是字母加数字的组合
            if (!checkPassword(password1)) {
                System.out.println("密码只能由字母和数字组成，且必须同时包含字母和数字");
                continue;
            }
            if (!password1.equals(password2)) {
                System.out.println("两次密码输入不一致");
                continue;
            }
          //当代码执行到这里 表示密码password1长度，内容，唯一性校验都通过了
            u.setPassword(password1);
            break;
        }

        //3.加入集合
        list.add(u);
        //4.提示注册成功
        System.out.println("注册成功！您的账号ID是：" + u.getID());
    }

    //作用：校验字符串的长度是否在指定范围内
    public boolean checkLen(int minLen, int maxLen, String str){
        return str.length() >= minLen && str.length() <= maxLen;
    }

    //作用：校验用户名是否由字母+数字组成 数字可有可无
    public boolean checkUsername(String username){
        int[] arr = getCount(username);
        return arr[0] > 0 && arr[2] == 0;
    }
    //作用：校验密码是否由字母+数字组成 字母至少一个 数字至少一个
    public boolean checkPassword(String password){
        int[] arr = getCount(password);
        return arr[0] > 0 && arr[1] > 0 && arr[2] == 0;
    }

    //作用：判断用户名在集合中是否包含
    public boolean contains(ArrayList<User> list, String username){
        for (int i = 0; i < list.size(); i++) {
            User u  = list.get(i);
            if (username.equals(u.getUsername())) {
                return true;
            }
        }
        return false;
    }

    //作用：根据用户名查找用户在集合中的索引，找不到返回 -1
    public int findIndex(ArrayList<User> list, String username){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    //统计字符串中字母、数字、其他字符的数量
    //返回数组：[字母数, 数字数, 其他字符数]
    public int[] getCount(String userInfo){
        int charCount = 0;
        int numCount  = 0;
        int otherCount = 0;

        for (int i = 0; i < userInfo.length(); i++) {
            char c = userInfo.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                charCount++;
            } else if (c >= '0' && c <= '9') {
                numCount++;
            } else {
                otherCount++;
            }
        }
        return new int[]{charCount, numCount, otherCount};
    }

    //作用：生成5位验证码
    //由4位大写或小写字母 + 1位数字组成，数字位置随机
    //例如：aQa1K
    public static String getCode(){
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a' + i));
            list.add((char)('A' + i));
        }
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            char c = list.get(index);
            sb.append(c);
        }
        sb.append(r.nextInt(10));

        char[] arr = sb.toString().toCharArray();
        int randomIndex = r.nextInt(arr.length);
        char temp = arr[randomIndex];
        arr[randomIndex] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;

        return new String(arr);
    }
}