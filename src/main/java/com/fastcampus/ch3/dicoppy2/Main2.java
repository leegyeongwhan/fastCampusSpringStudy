package com.fastcampus.ch3.dicoppy2;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class Car {
}

class SportsCar extends Car {
}

class Truck extends Car {
}

class Engine {
}

class AppContext {
    Map map;  //객체 저장

    AppContext() {
        try {
            Properties p = new Properties();
            p.load(new FileReader("config.txt"));

            map = new HashMap(p);

            for (Object key : map.keySet()) {
                Class clazz = Class.forName((String) map.get(key));
                map.put(key, clazz.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) {
        return map.get(key);
    }
}

public class Main2 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car");
        Engine e = (Engine) ac.getBean("engine");
        System.out.println("car=" + car);
        System.out.println("engine = " + e);
    }
}
