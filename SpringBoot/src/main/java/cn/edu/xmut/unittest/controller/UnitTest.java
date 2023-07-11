package cn.edu.xmut.unittest.controller;

import cn.edu.xmut.unittest.entity.Student;

public class UnitTest {
    public static void main(String[] args) {
        Student s = new Student();
        s.setName("xmut");
        s.setNo("40");
        s.setAge(40);
        s.setHeight(1981.0);

        s.setBlFlag(true);

        System.out.println(s.isBlFlag());
    }
}
