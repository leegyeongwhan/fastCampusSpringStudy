package com.fastcampus.ch3.aop;

import org.checkerframework.checker.units.qual.C;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception {
        MyAdive myAdive = new MyAdive();

        Class myclass = Class.forName("com.fastcampus.ch3.aop.MyClass");
        Object obj = myclass.newInstance();

        for (Method m : myclass.getDeclaredMethods()) {
            myAdive.invoke(m, obj, null);
        }
    }
}

class MyAdive {
    Pattern p = Pattern.compile("a.*");

    boolean matches(Method method) {
        Matcher matcher = p.matcher(method.getName());
        return matcher.matches();
    }

    void invoke(Method m, Object obj, Object... args) throws Exception {
        if (m.getAnnotation(Transactional.class) != null)
            System.out.println("brfore");
        m.invoke(obj, args);
        if (m.getAnnotation(Transactional.class) != null)
            System.out.println("after");
    }
}

class MyClass {

    @Transactional
    void aaa() {
        System.out.println("aaa1() is called");
    }

    void aaa2() {
        System.out.println("aaa2() is called");
    }

    void bbb() {
        System.out.println("bbb() is called");
    }

}