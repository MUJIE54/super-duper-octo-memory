package com.itheima.domin;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defence;
    public boolean defanding;

    public Character() {
    }

    public Character(String name, int HP,  int attack, int defence) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defence = defence;
    }
    //1.判断是否存活
    public boolean isAlive(){
        return HP > 0;
    }
    //2.回复血量
    public void heal(int amount){
        HP += amount;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }
   public int attack(Character target, int multiplier){
       int raw = this.attack * multiplier / 100;
       int actual = raw - target.defence;
       if (actual < 1) actual = 1;
       target.takeDamage(actual);
       return actual;
   }

   // 3. 受到伤害
   public void takeDamage(int damage){
       HP=HP-damage;
       if (HP<0) {
           HP=0;
       }
   }
   //4.展示任务的属性
    public String show(){
        return name+"[当前生命："+HP+". 攻击："+attack+". 防御："+defence+"]";
    }



}