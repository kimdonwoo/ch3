package com.fastcampus.ch3.aop;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception{

        MyAdvice myAdvice = new MyAdvice();

        // MyClass의 객체를 생성해서 MyAdvice의 invoke 메서드로 넘겨줄거임
        Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass");
        Object obj = myClass.newInstance();

        for(Method m : myClass.getDeclaredMethods()){
            myAdvice.invoke(m,obj,null);
        }
    }
}

// 메서드들을 포함하는 별도의 MyClass를 만들어 호출
class MyAdvice{
    Pattern p = Pattern.compile("a.*");

    // 패턴에 맞는것만
    boolean matches(Method m){
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }
    void invoke(Method m, Object obj, Object... args) throws Exception{
        if(m.getAnnotation(Transactional.class)!= null) System.out.println("[before]{");
        m.invoke(obj,args);
        if(m.getAnnotation(Transactional.class)!= null) System.out.println("}[after]");

    }
}

class MyClass{
    @Transactional
    void aaa1(){
        System.out.println("aaa1() is called.");
    }
    void aaa2(){
        System.out.println("aaa2() is called.");
    }
    void bbb(){
        System.out.println("bbb() is called.");
    }
}
