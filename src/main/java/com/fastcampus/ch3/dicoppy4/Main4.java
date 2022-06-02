package com.fastcampus.ch3.dicoppy4;

import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
class Car {
    @Resource
    Engine engine;
    @Resource
    Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}

@Component
class SportsCar extends Car {
}

@Component
class Truck extends Car {
}

@Component
class Engine {
}

@Component
class Door {

}

class AppContext {
    Map map;  //객체 저장

    AppContext() {
        map = new HashMap();
        doComponentScan();
        doAutowired();
        doResource();
    }

    private void doResource() {
        //map에 저장된 객체의 iv 중이 @Resource 붙어있으면 연결
        // map 에서 iv이름에 맞는 객체의 주소를 iv에저장
        try {
            for (Object bean : map.values()) {
                for (Field fld : bean.getClass().getDeclaredFields()) {
                    if (fld.getAnnotation(Resource.class) != null) {
                        fld.set(bean, getBean(fld.getName()));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void doAutowired() {
        //map에 저자오딘 객체의 iv 중이 @Autowired가 붙어있으면 ㅇ녀결
        //객체의 주소를 iv에저장

        try {
            for (Object bean : map.values()) {
                for (Field fld : bean.getClass().getDeclaredFields()) {
                    if (fld.getAnnotation(Autowired.class) != null) {
                        fld.set(bean, getBean(fld.getType()));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void doComponentScan() {
        try {
            //패키지 내의 클래스 목록을 가져온다.
            //2. 반복문으로 클래스를 하나씩 읽어와서 @component 붙어있는지 확인
            //3 @componet가 붙어있는 객체를 생성해서 map에저장
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.dicoppy4");

            for (ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if (component != null) {
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Object getBean(String key) {
        return map.get(key);
    }

    Object getBean(Class clazz) {  //매개변수를 클레스 객체로 받는다
        for (Object obj : map.values()) {
            if (clazz.isInstance(obj))
                return obj;
        }
        return null;
    }
}

public class Main4 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car"); //byname
        Engine engine = (Engine) ac.getBean("engine");
        Door door = (Door) ac.getBean(Door.class); //byType

//        car.engine = engine;
//        car.door = door;

        System.out.println("car=" + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }
}
