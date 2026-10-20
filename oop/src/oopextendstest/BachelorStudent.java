package oopextendstest;

public class BachelorStudent extends Student {

    //私有化成员变量不写 没有独有的

    //空参构造

    //带全部参数额构造
    public BachelorStudent() {

    }


    public BachelorStudent(String name, int age, String grade) {
        super(name, age, grade);
    }

    @Override
    public void study() {
        System.out.println("本科生正在攻读本科课程");
    }
}
