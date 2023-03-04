package com.fastcampus.ch3.diCopy4;

import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component class Car {

    @Resource Engine engine;
    @Resource Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }



}
@Component class SportsCar extends Car{}
@Component class Truck extends Car{}
@Component class Engine{}
@Component class Door{}

class AppContext {
    Map map; // 저장소

    AppContext() {
        map = new HashMap();
        doComponentScan();
        doAutowired();
        doResouce();
    }

    private void doResouce() {
        // map 에 저장된 객체의 iv중에 @Resource가 붙어 있으면
        // map 에 iv의 이름에 맞는 객체를 찾아서 연결(객체의 주소를 iv 저장)
        try{
            for(Object bean : map.values()){
                for( Field fld : bean.getClass().getDeclaredFields()) {
                    if (fld.getAnnotation(Resource.class) != null) { // byName
                        fld.set(bean, getBean(fld.getName())); // car.engine = obj
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doAutowired() {
        // map 에 저장된 객체의 iv중에 @Autowired가 붙어 있으면
        // map 에 iv의 타입에 맞는 객체를 찾아서 연결(객체의 주소를 iv 저장)
        try{
            for(Object bean : map.values()){
                for( Field fld : bean.getClass().getDeclaredFields()) {
                    if (fld.getAnnotation(Autowired.class) != null) { // byType
                        fld.set(bean, getBean(fld.getType())); // car.engine = obj
                        }
                    }
                }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void doComponentScan() {
        try {
            // 1. 해당 패키지 내의 클래스 목록 set에 가져온다.
            // 2. 반복문으로 클래스 하나씩 읽어와서 @Component이 붙어 있는지 확인
            // 3. @Component가 붙어 있으면 객체를 생성해서 map에 저장
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");

            for (ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component componet = (Component) clazz.getAnnotation(Component.class);
                if (componet != null) {
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // byName
    Object getBean(String key) {
        return map.get(key);
    }

    //byType
    Object getBean(Class clazz) {
        for (Object obj : map.values()) {
            // 이 obj가 해당 class의 자손이면 true
            if (clazz.isInstance(obj)) return obj;
        }
        return null;
    }

}

public class Main4 {
    public static void main(String[] args) throws Exception{
        AppContext ac = new AppContext();
        Car car = (Car)ac.getBean("car"); // byName으로 객체 검색
        //Car car2 = (Car)ac.getBean(Car.class); // byType으로 객체 검색
        Engine engine = (Engine)ac.getBean("engine");
        // getBean할때 해당 class가 생성되서 반환

        Door door= (Door) ac.getBean(Door.class);
        car.engine = engine;
        car.door = door;

        System.out.println("car = "+ car);
        System.out.println("engine = "+ engine);
        System.out.println("door = "+ door);


    }
}
