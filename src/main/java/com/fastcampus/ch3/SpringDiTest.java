//package com.fastcampus.ch3;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component class Engine{}
//@Component class SuperEngine extends Engine{}
//@Component class TurboEngine extends Engine{}
//@Component class Door{}
//@Component class Car{
//    @Value("red") String color;
//    @Value("100") int oil;
//    @Autowired Engine engine;
//    @Autowired Door[] doors;
//
//    public Car() {}
//
//    public Car(String color, int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    public void setDoors(Door[] doors) {
//        this.doors = doors;
//    }
//}
//
//
//public class SpringDiTest {
//    public static void main(String[] args){
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//
//        Car car = (Car) ac.getBean("car"); // byName
//        //Car car = ac.getBean("car",Car.class); 위랑 같은 문장
////        Car car2 = (Car) ac.getBean(Car.class); // byType
//        // 싱글톤이라 car2는 car과 같은 객체 반환
//
////        Engine engine = (Engine) ac.getBean("superEngine");
////        Door door = (Door) ac.getBean("door");
//
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        car.setDoors(new Door[]{ac.getBean("door", Door.class),ac.getBean("door", Door.class)});
//
//        System.out.println("car = " + car);
//
//    }
//}
