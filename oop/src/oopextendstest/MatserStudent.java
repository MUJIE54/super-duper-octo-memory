package oopextendstest;

public class MatserStudent extends Student {
    public MatserStudent(String name, int age, String grade) {
        super(name, age, grade);
    }

    public MatserStudent() {
    }

    @Override
    public void study() {
        System.out.println("研究生正在攻读硕士课程");
    }
}

