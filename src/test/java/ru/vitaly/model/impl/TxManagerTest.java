package ru.vitaly.model.impl;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.vitaly.entity.Car;
import ru.vitaly.entity.Brand;
import ru.vitaly.entity.CarBody;
import ru.vitaly.entity.Engine;
import ru.vitaly.entity.Model;
import ru.vitaly.entity.Offer;
import ru.vitaly.entity.Transmission;
import ru.vitaly.entity.User;
import ru.vitaly.service.Wrapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class TxManagerTest {
    /**
     * Менеджер транзакций.
     */
    private static final TxManager MANAGER = TxManager.getInstance();
    /**
     * Список объявлений.
     */
    private static final List<Offer> OFFERS = new ArrayList<>();

    /**
     * @param bName марка.
     * @param mName модель.
     * @param tName коробка передач.
     * @param cbName кузов.
     * @param eName двигатель.
     * @param date дата создания объявления
     * @return экз. класса Offer.
     */
    private static Offer createAndPersist(String bName, String mName, String tName, String cbName, String eName, String date, String name, String password) {
        Brand brand = Brand.newBuilder().setName(bName).build();
        Model model = Model.newBuilder().setName(mName).build();
        Transmission transmission = Transmission.newBuilder().setName(tName).build();
        CarBody body = CarBody.newBuilder().setName(cbName).build();
        Engine engine = Engine.newBuilder().setName(eName).build();
        LocalDate year = LocalDate.parse(LocalDate.now().toString());

        Car car = Car.newBuilder()
                .setBrand(brand)
                .setModel(model)
                .setCarbody(body)
                .setTransmission(transmission)
                .setEngine(engine)
                .setYear(year)
                .build();

        User user = User.newBuilder().setName(name).setPassword(password).build();
        final Offer offer = Offer.newBuilder().setCar(car).setCreateDate(LocalDate.parse(date)).setImgName("test.jpg").setUser(user).build();

        new Wrapper(MANAGER.factory).perform(session -> {
            session.save(brand);
            session.save(model);
            session.save(transmission);
            session.save(body);
            session.save(engine);
            session.save(car);
            session.save(user);
            session.save(offer);
            return offer;
        });
        return offer;
    }

    /**
     * Добавление в список 3-х объявлений, которые на момент добавления уже в персист.состоянии.
     */
    @BeforeClass
    public static void init() {
        String today = LocalDate.now().toString();
        OFFERS.add(createAndPersist("bmw", "x5", "Автомат", "Внедорожник", "Бензин", today, "bill", "123"));
        OFFERS.add(createAndPersist("kia", "ceed", "Ручная", "Хэтч", "Бензин", "2020-02-02", "don", "test"));
        OFFERS.add(createAndPersist("ford", "focus", "Авто", "Хэтч", "Дизель", today, "tom", "home"));
    }

    /**
     * Проверка того, что объявление, полученное по id, идентично объявлению, полученному из БД по тому же id.
     */
    @Test
    public void findByIdTest() {
        Offer offer = OFFERS.get(0);
        int id = offer.getId();
        assertThat(offer, is(MANAGER.findById(id)));

        offer = OFFERS.get(1);
        id = offer.getId();
        assertThat(offer, is(MANAGER.findById(id)));

        offer = OFFERS.get(2);
        id = offer.getId();
        assertThat(offer, is(MANAGER.findById(id)));
    }

    /**
     * Проверка того, что имеющийся список с объявлениями идентичен полученному списку со всеми объявлениями из БД.
     */
    @Test
    public void selectOffersTest() {
        assertEquals(OFFERS, MANAGER.selectAllOffers());
    }

    /**
     * Проверка того, что пользователь из объявления идентичен пользователю, найденному по имени и паролю.
     */
    @Test
    public void getUserTest() {
        User user = OFFERS.get(0).getUser();
        String name = user.getName();
        String password = user.getPassword();
        assertThat(user, is(MANAGER.getUser(name, password)));

        user = OFFERS.get(OFFERS.size() - 1).getUser();
        name = user.getName();
        password = user.getPassword();
        assertEquals(user, MANAGER.getUser(name, password));
    }

    /**
     * Проверка того, что лист объявлений, созданных сегодня, идентичен списку, полученному из БД.
     */
    @Test
    public void selectOffersByDayTest() {
        final Map<LocalDate, List<Offer>> map = OFFERS.stream().collect(Collectors.groupingBy(Offer::getCreateDate));
        assertThat(map.get(LocalDate.now()), is(MANAGER.selectTodayOffers()));
    }

    /**
     * Проверка того, что лист объявлений, полученный по номеру машины, идентичен списку, полученному из БД также по
     * номеру машины.
     */
    @Test
    public void selectOffersByCarTest() {
        final Offer offer = OFFERS.get(0);
        int id = offer.getCar().getId();
        assertThat(Collections.singletonList(offer), is(MANAGER.selectOffersByCar(id)));
    }
}