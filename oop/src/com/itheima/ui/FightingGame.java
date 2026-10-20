package com.itheima.ui;

import com.itheima.domin.EnemyCharacter;
import com.itheima.domin.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {

    public void startGame(String username) {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚═════════════════════════════╝");

        HeroCharacter player = createCharacter(username);
        System.out.println("角色创建成功！");
        System.out.println("\uD83C\uDF1F 初始属性为：" + player.show());
        System.out.println("\uD83C\uDF1F 拥有的技能：" + player.showSkill());

        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));

        int count = 1;
        int wins = 0;
        Random r = new Random();

        while (player.isAlive()) {
            if (wins != 0) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHP = c.maxHP + 10;
                    c.HP = c.maxHP;
                    c.attack += 3;
                    c.defence += 2;
                    c.defanding = false;
                }
            }

            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);

            System.out.println("--------------------------");
            System.out.println("⚔️第" + count + "场战斗开始！对手：" + enemy.name);
            //战斗开始

            int round = 1;
            while (player.isAlive() && enemy.isAlive()) {
                System.out.println("--------------------------");
                System.out.println("⚔️第" + round + "回合！");
                //打印敌我双方的血条
                System.out.println("你：" + getHealthBar(player.name, player.HP, player.maxHP));
                System.out.println("敌：" + getHealthBar(enemy.name, enemy.HP, enemy.maxHP));

                playerTurn(player, enemy);

                if (!enemy.isAlive()) {
                    System.out.println("🎉 击败了 " + enemy.name + "！");
                    wins++;
                    break;
                }

                enemyTurn(enemy, player);

                if (!player.isAlive()) {
                    System.out.println("💀 你被击败了...");
                    break;
                }

                round++;
            }

            //跟一个敌人的战斗结束之后，玩家胜利（继续战斗） 玩家失败（游戏停止）
            if (player.isAlive()) {
                //恢复玩家的血量

                int healHP = r.nextInt(21)+20;
                player.heal(healHP);
                //提示
                //💚 战斗结束！你恢复了 36 点生命值
                System.out.println("💚 战斗结束！你恢复了 " + healHP + " 点生命值！");
                //🏆 当前胜场: 1
                System.out.println("🏆 当前胜场: " + wins);
                System.out.println("--------------------------");

            }
            //每胜利三场，人物的属性就需要增加
            if(player.isAlive()&&wins%3==0){
                System.out.println("恭喜你，你成功升级了！");
                player.maxHP+=20;
                player.attack+=5;
                player.defence+=3;

                System.out.println("升级后属性为：" + player.show());
                System.out.println("--------------------------");

            }
            //询问玩家是否继续战斗
            if(player.isAlive()){
                System.out.println("是否继续战斗？（y/n）");
                Scanner sc = new Scanner(System.in);
                String choice = sc.nextLine();
                if("y".equalsIgnoreCase(choice)){
                    //战斗继续
                    count++;
                    continue;
                }else if("n".equalsIgnoreCase(choice)){
                    //游戏结束
                    break;
                }else{
                    System.out.println("没有这个选项默认继续战斗");
                    continue;
                }
            }



        }

        System.out.println("\n=== 游戏结束 ===");
        System.out.println("你共击败了 " + wins + " 个敌人");
    }
//敌人的回合
    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println(enemy.name + " 回合！");
        //计算当前是普通攻击的50%
        //还是技能攻击的50% ---猛击 快速攻击 防御姿态 火球术
        //表示：敌人要采取的攻击手段
        String action = "普通攻击";

        //进行几率的计算
        Random r = new Random();
        int num = r.nextInt(10);// 0 1 2 3 4(普通攻击） 5 6 7 8 9(技能攻击）

        if (num >= 5) {
            action = enemy.skill;
        }

        switch (action) {
            case "普通攻击":
                System.out.println(enemy.name + " 使用普通攻击！");
                //计算敌人用普通攻击给我造成了多少点伤害
                int damage1 = calculateDamage(enemy.attack, player.defence);
                //提示
                System.out.println("⚔️ " + enemy.name + "对你使用了普通攻击，造成了 " + damage1 + " 点伤害！");
                //我方扣血
                player.takeDamage(damage1);
                break;
            case "猛击":
                System.out.println(enemy.name + " 使用猛击！");
                int damage2 = calculateDamage((int)(enemy.attack * 1.5), player.defence);
                System.out.println("⚔️ " + enemy.name + "对你使用了猛击，造成了 " + damage2 + " 点伤害！");
                player.takeDamage(damage2);
                break;
            case "快速攻击":
                System.out.println(enemy.name + " 使用快速攻击！");
                int damage3a = calculateDamage((int)(enemy.attack * 0.5), player.defence);
                System.out.println("⚔️ " + enemy.name + " 第一次攻击造成 " + damage3a + " 点伤害！");
                player.takeDamage(damage3a);
                if (player.isAlive()) {
                    int damage3b = calculateDamage((int)(enemy.attack * 0.5), player.defence);
                    System.out.println("⚔️ " + enemy.name + " 第二次攻击造成 " + damage3b + " 点伤害！");
                    player.takeDamage(damage3b);
                }
                break;
            case "防御姿态":
                enemy.defanding = true;
                System.out.println(enemy.name + " 进入防御姿态！");
                int damage4 = calculateDamage(enemy.attack, player.defence);
                System.out.println("⚔️ " + enemy.name + "对你使用了普通攻击，造成了 " + damage4 + " 点伤害！");
                player.takeDamage(damage4);
                break;
            case "火球术":
                System.out.println(enemy.name + " 使用火球术！");
                int damage5 = calculateDamage((int)(enemy.attack * 1.8), player.defence);
                System.out.println("🔥 " + enemy.name + "对你使用了火球术，造成了 " + damage5 + " 点伤害！");
                player.takeDamage(damage5);
                break;
        }
    }

    public String getHealthBar(String name, int hp, int maxHP) {
        int barLength = 20;
        int filled = (int) ((hp * 1.0 / maxHP) * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                sb.append("█");
            } else {
                sb.append(" ");
            }
        }
        sb.append("]").append(hp).append("/").append(maxHP).append("HP");
        return sb.toString();
    }

    public HeroCharacter createCharacter(String username) {
        System.out.println("创建玩家角色：");
        System.out.println("玩家用户名：" + username);

        int point = 20;

        System.out.println("请分配属性 （当前属性点：20）：");
        System.out.println(" 生命值（每点+10HP）：");
        System.out.println(" 攻击力（每点+2ATK）：");
        System.out.println(" 防御力（每点+1DEF）：");
        Scanner sc = new Scanner(System.in);
        String[] prompt = {"生命值", "攻击力", "防御力"};
        int[] values = new int[3];
        for (int i = 0; i < prompt.length; i++) {
            System.out.println("分配点数到" + prompt[i] + "，剩余点数：" + point + "）：");
            int input = sc.nextInt();

            if (input < 0) {
                System.out.println("无效输入！默认分配0点");
                input = 0;
            }
            if (input > point) {
                System.out.println("属性点不足！剩余属性点全部分配到" + prompt[i] + "属性");
                input = point;
            }

            point -= input;
            values[i] = input;
        }
        HeroCharacter player = new HeroCharacter(
                username,
                100 + values[0] * 10,
                10 + values[1] * 2,
                0 + values[2] * 1
        );
        player.skillList.add("普通攻击");
        player.skillList.add("强力攻击");
        player.skillList.add("生命汲取");
        return player;
    }

    //玩家回合 ： 选择行动（1.普通攻击 2.强力攻击 3.生命汲取）
    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("\n【你的回合】请选择技能：");
        System.out.println("1.普通攻击");
        System.out.println("2.强力攻击");
        System.out.println("3.生命汲取");
        System.out.println("请输入：");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        switch (choice) {
            default:
                System.out.println("没有这个操作，默认使用普通攻击！");
            case "1":
                System.out.println("你选择了普通攻击！");
                //我方的攻击力 - 对方的防御力 =伤害值
                int damage1 = calculateDamage(player.attack, enemy.defence);
                //⚔️ ，你对 敏捷刺客 使用了普通攻击，造成 伤害！
                System.out.println("⚔️ 你对 " + enemy.name + " 使用了普通攻击，造成了 " + damage1 + " 点伤害！");
                //扣血操作
                enemy.takeDamage(damage1);
                break;

            case "2":
                if (player.HP > 10) {
                    System.out.println("你选择了强力攻击！");
                    //消耗我方十点生命
                    player.takeDamage(10);
                    int damage2 = calculateDamage((int) (player.attack * 1.8), enemy.defence);
                    //⚔️ ，你对 敏捷刺客 使用了强力攻击，造成 伤害！
                    System.out.println("⚔️ 你对 " + enemy.name + " 使用了强力攻击，造成了 " + damage2 + " 点伤害！");
                    //扣血操作
                    enemy.takeDamage(damage2);
                    break;
                } else {
                    System.out.println("生命值不足，无法使用强力攻击！");
                    int def = calculateDamage(player.attack, enemy.defence);
                    System.out.println("⚔️ 你对 " + enemy.name + " 使用了普通攻击，造成了 " + def + " 点伤害！");
                    enemy.takeDamage(def);
                    break;
                }

            case "3":
                System.out.println("你选择了生命汲取！");
                if (player.HP > 10) {
                    //消耗我方十点生命
                    player.takeDamage(10);
                    Random r = new Random();
                    int healHP = r.nextInt(20);
                    //回复生命值
                    player.heal(healHP);
                    //提示：💚消耗10HP，你使用了生命汲取，恢复了10点生命！
                    System.out.println("💚消耗10HP，你使用了生命汲取，恢复了" + healHP + " 点生命！");
                } else {
                    System.out.println("生命值不足，无法使用生命汲取！");
                }
                break;
        }
    }

    //作用：用来计算双方战斗的时候的伤害值
    //普通攻击的伤害值 = 攻击力 - 防御力
    //技能攻击的伤害值 = 攻击力*百分比 - 防御力
    public int calculateDamage(int Attack, int Defence) {
        int damage = Attack - Defence;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }

}