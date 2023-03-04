package com.fastcampus.ch3.diCopy2;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class Car {}
class SportsCar extends Car{}
class Truck extends Car{}
class Engine{}

class AppContext {
    Map map; // 저장소
    AppContext(){

        try {
            // 먼저 Properties를 받음
            Properties p = new Properties();
            p.load(new FileReader("config.txt"));

            // Properties 내용을 map에 저장
            map = new HashMap(p);

            // 반복문으로 map에 저장되어 있는 Class이름을 얻어서 객체를 생성한 후 다시 map에 저장
            for(Object key : map.keySet()){
                Class clazz = Class.forName((String) map.get(key));
                map.put(key,clazz.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    Object getBean(String key){
        return map.get(key);
    }
}

public class Main2 {
    public static void main(String[] args) throws Exception{

        AppContext ac = new AppContext();

        Car car2 = (Car)ac.getBean("car");
        Engine engine = (Engine)ac.getBean("engine");
        // getBean할때 해당 class가 생성되서 반환
        System.out.println("car2 = "+ car2);
        System.out.println("engine = "+ engine);

    }
}
