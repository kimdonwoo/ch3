package com.fastcampus.ch3.diCopy1;

import java.io.FileReader;
import java.util.Properties;

class Car {}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}

public class Main1 {
    public static void main(String[] args) throws Exception{
        Car car = getCar();
        Car car2 = (Car)getObject("car");
        Engine engine = (Engine)getObject("engine");
        // getObject할때 해당 class가 생성되서 반환
        System.out.println("car = "+ car);
        System.out.println("car2 = "+ car2);
        System.out.println("engine = "+ engine);

    }

    static Car getCar() throws Exception{
        //config.txt내용을 읽어서 Properties에 map으로 저장
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty("car"));

        return (Car)clazz.newInstance();
    }

    static Object getObject(String key) throws Exception{
        //config.txt내용을 읽어서 Properties에 map으로 저장
        Properties p = new Properties();
        p.load(new FileReader("config.txt"));

        //클래스 객체(설계도)를 얻어서
        Class clazz = Class.forName(p.getProperty(key));

        // 객체를 생성해서 반환
        return clazz.newInstance();
    }

}
