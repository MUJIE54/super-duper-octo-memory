package com.itheima.domin;

public class EnemyCharacter extends Character {
    public String skill;
    public EnemyCharacter() {
        super();
    }
    public EnemyCharacter(String name, int HP,  int attack, int defence,String skill) {
        super(name, HP, attack, defence);
        this.skill = skill;
    }

    //重写一下父类的方法
    @Override
    public void takeDamage(int damage){
        //如果敌人是防御状态，那么伤害会减半
        int actual = damage;
        if (defanding) {
            actual = damage /2 >1?damage/2:1;
            defanding=false;//防御状态只持续一次，所以需要重置

        }
        //调用父类的方法 扣除血量
        super.takeDamage(actual);
    }
}