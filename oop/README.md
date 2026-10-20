# 文字格斗游戏（Console Fighting Game）

一个纯 Java 控制台小游戏，模拟 RPG 回合制战斗流程。包含登录注册、角色属性分配、多敌人随机遭遇、技能选择、升级成长等完整玩法。

## 功能一览

- **登录注册系统**
  - 用户名 3–16 位，字母+数字组合，不允许纯数字、不允许重复
  - 密码 3–8 位，必须同时包含字母和数字，两次输入一致
  - 5 位随机验证码（4 位字母 + 1 位数字）
  - 密码错误 3 次锁定本次登录
  - 用户 ID 自动生成：`heima` + 5 位随机数字
  - 账号支持启用/禁用状态
- **角色创建**
  - 共 20 点属性点自由分配
    - 生命值：每点 +10 HP
    - 攻击力：每点 +2 ATK
    - 防御力：每点 +1 DEF
- **回合制战斗**
  - 玩家三选一行动：
    1. **普通攻击** —— 稳定伤害
    2. **强力攻击** —— 消耗 10 HP，造成 1.8 倍伤害
    3. **生命汲取** —— 消耗 10 HP，随机回复 0–19 HP
  - 敌人 50% 概率放技能，50% 普通攻击
- **四种敌人**

  | 敌人 | 特点 | 技能 |
  |---|---|---|
  | 初级战士 | 均衡型 | 猛击（1.5 倍伤害）|
  | 敏捷刺客 | 高攻低血 | 快速攻击（两段 0.5 倍伤害）|
  | 重装坦克 | 高血高防 | 防御姿态（下次受伤减半）|
  | 神秘法师 | 高爆发 | 火球术（1.8 倍伤害）|

- **成长机制**
  - 每战胜一场，回复 20–40 HP
  - 每连胜 3 场升级：HP +20、ATK +5、DEF +3
  - 敌人随胜场数逐渐变强（HP +10、ATK +3、DEF +2）

## 运行方式

### 环境要求

- JDK 8 及以上（纯 Java SE 项目，无第三方依赖）
- 任意 IDE（IDEA / VS Code / Eclipse）或命令行

### 启动入口

直接运行 `com.itheima.App` 的 main 方法：

```java
public class App {
    public static void main(String[] args) {
        FightingGame fg = new FightingGame();
        fg.startGame("zhangsan");   // 传入用户名直接进入游戏
    }
}
```

> 默认跳过登录页直接开始游戏。如需体验登录/注册流程，把 `App` 里的注释切换为 `Login l = new Login(); l.start();`。

### 命令行编译运行

```bash
cd oop/src
javac com/itheima/App.java
java com.itheima.App
```

## 项目结构

```
oop/src/com/itheima/
├── App.java                    # 程序入口
├── domin/                      # 领域对象
│   ├── Character.java          # 角色父类（HP/ATK/DEF、受伤、回血）
│   ├── HeroCharacter.java      # 我方角色（持有技能列表 skillList）
│   ├── EnemyCharacter.java     # 敌方角色（持技能名，重写受伤逻辑）
│   └── User.java               # 用户账号（id/username/password/status）
└── ui/                         # 交互界面
    ├── Login.java              # 登录/注册主菜单
    └── FightingGame.java       # 战斗主循环
```

## 面向对象要点

- **继承体系**：`HeroCharacter` / `EnemyCharacter` 继承自 `Character`，复用 HP/ATK/DEF 与 `takeDamage`、`heal`、`isAlive` 等方法。
- **方法重写**：`EnemyCharacter#takeDamage` 重写父类方法，加入「防御姿态伤害减半」逻辑。
- **封装**：`User` 的 id、status 通过构造器自动初始化，对外只暴露 getter/setter。
- **集合使用**：玩家技能列表 `ArrayList<String>`、用户列表 `ArrayList<User>`、敌人列表 `ArrayList<EnemyCharacter>`。

## 示例战斗输出

```
╔═════════════════════════════╗
    🎮 欢迎来到文字格斗游戏 🎮
╚═════════════════════════════╝
角色创建成功！
🌟 初始属性为：zhangsan[当前生命：120. 攻击：14. 防御：3]
🌟 拥有的技能：普通攻击,强力攻击,生命汲取
--------------------------
⚔️第1场战斗开始！对手：神秘法师
--------------------------
⚔️第1回合！
你：zhangsan:[████████████████████]120/120HP
敌：神秘法师:[████████████        ]70/70HP
```
