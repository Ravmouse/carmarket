package ru.vitaly.service;

import org.hibernate.SessionFactory;
import ru.vitaly.entity.Brand;
import ru.vitaly.entity.Car;
import ru.vitaly.entity.CarBody;
import ru.vitaly.entity.Engine;
import ru.vitaly.entity.Model;
import ru.vitaly.entity.Transmission;
import ru.vitaly.model.impl.TxManager;
import java.time.LocalDate;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class CarCreator {
    /**
     * Фабрика сессий.
     */
    private final SessionFactory factory = TxManager.getInstance().factory;

    /**
     * @param brand марка.
     * @param model модель.
     * @param year год.
     * @param body кузов.
     * @param transmission коробка передач.
     * @param engine двигатель.
     * @return персистентный объект (машина).
     */
    public Car createCar(int brand, int model, String year, int body, int transmission, int engine) {
        final Car car = new Car();
        car.setYear(LocalDate.parse(year));
        new Wrapper(factory).perform(session -> {
            car.setBrand(session.get(Brand.class, brand));
            car.setModel(session.get(Model.class, model));
            car.setTransmission(session.get(Transmission.class, transmission));
            car.setCarBody(session.get(CarBody.class, body));
            car.setEngine(session.get(Engine.class, engine));
            return car;
        });
        return car;
    }
}