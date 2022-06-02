package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
class Car {

    @Value("red")
    String color;
    @Value("100")
    int oil;
    @Autowired               //byType
    @Qualifier("superEngine")
    Engine engine;
    @Autowired
    Door[] doors;

    public Car() {
    }

    public Car(String color, int oil, Engine engine, Door[] doors) {
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }


    public void setColor(String color) {
        this.color = color;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }
}

//@Component
class Engine {
}

@Component
class SuperEngine extends Engine {
}

@Component
class TurboEngine extends Engine {
}

@Component
class Door {

}

public class SpringDiTest {
    public static void main(String[] args) {
        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
        //Car car = (Car) ac.getBean("car"); //byname 아래문장과 같다
        Car car = ac.getBean("car", Car.class);
//        Car car2 = (Car) ac.getBean(Car.class); //byname
//
//        Engine engine = (Engine) ac.getBean("superEngine");
// //        Engine engine = (Engine) ac.getBean(Engine.class);    타입이 같은게 많으면 에러
//        Door door = (Door) ac.getBean("door");

//        car.setColor("red");
//        car.setOil(100);
//        car.setEngine(engine);
//        car.setDoors(new Door[]{ac.getBean("door", Door.class), ac.getBean("door", Door.class)});
        System.out.println("car = " + car);
    }
}
