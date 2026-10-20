package oopextendstest;

public class GeneralTeacher extends Teacher{

    public GeneralTeacher() {
    }

    public GeneralTeacher(String name, int age, String subject) {
        super(name, age, subject);
    }

    @Override
    public void teach() {
        System.out.println("研究生正在攻读硕士课程");
    }
}
