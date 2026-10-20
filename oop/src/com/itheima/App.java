package com.itheima;

import com.itheima.ui.FightingGame;
import com.itheima.ui.Login;

public class App {
    public static void main(String[] args) {
//        Login l = new Login();
//        l.start();
        FightingGame fg = new FightingGame();
        fg.startGame("zhangsan");

    }
}
