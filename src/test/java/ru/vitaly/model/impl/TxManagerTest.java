package ru.vitaly.model.impl;

import org.junit.Test;
import ru.vitaly.entity.Car;
import ru.vitaly.entity.Brand;
import ru.vitaly.entity.Offer;
import ru.vitaly.entity.User;
import java.time.LocalDate;
import java.util.Arrays;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
    private final TxManager manager = new TxManager("hibernate_test.cfg.xml");

    /**
     * Добавляется объявление, которое становится персистентным.
     * У этого объявления берется id, по которому ищется объявление в БД.
     */
    @Test
    public void addOfferTest() {
        Offer offer = new Offer("test.jpg");
        offer.setCar(new Car());
        offer = manager.addOffer(offer);
        int id = offer.getId();
        assertThat(offer, is(manager.findById(id)));
    }

    /**
     * Добавляются три объявления.
     * Сравнивается, что список из этих трех объявлений равен списку, получаемому из БД.
     */
    @Test
    public void selectOffersTest() {
        Offer offerOne = new Offer();
        offerOne.setImgName("test.jpg");
        offerOne.setCar(new Car());

        Offer offerTwo = new Offer();
        offerTwo.setImgName("test2.jpg");
        offerTwo.setCar(new Car());

        Offer offerThree = new Offer();
        offerThree.setImgName("test3.jpg");
        offerThree.setCar(new Car());

        offerOne = manager.addOffer(offerOne);
        offerTwo = manager.addOffer(offerTwo);
        offerThree = manager.addOffer(offerThree);
        assertEquals(Arrays.asList(offerOne, offerTwo, offerThree), manager.selectAllOffers());
    }

    /**
     * Юзер делается персистентным.
     * Сравнивается, что сделанный юзер равен юзеру, полученному из БД.
     */
    @Test
    public void getUserTest() {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        user = manager.addUser(user);
        assertThat(user, is(manager.getUser(user.getName(), user.getPassword())));
    }

    /**
     * Создаются три объявления. В них устанавливаются даты. Потом объявления заносятся в БД.
     * Сравнивается, что список из двух объявлений, равен полученному списку за сегодняшний день.
     */
    @Test
    public void selectOffersByDayTest() {
        final Offer offerOne = new Offer();
        offerOne.setCreateDate(LocalDate.parse(LocalDate.now().toString()));
        offerOne.setCar(new Car());

        final Offer offerTwo = new Offer();
        offerTwo.setCreateDate(LocalDate.parse("2020-02-25"));
        offerTwo.setCar(new Car());

        final Offer offerThree = new Offer();
        offerThree.setCreateDate(LocalDate.parse(LocalDate.now().toString()));
        offerThree.setCar(new Car());

        manager.addOffer(offerOne);
        manager.addOffer(offerTwo);
        manager.addOffer(offerThree);
        assertThat(Arrays.asList(offerOne, offerThree), is(manager.selectTodayOffers()));
        assertThat(offerTwo, not(manager.selectTodayOffers()));
    }

    /**
     * Создается марка машины. Добавляется в БД.
     * Создается машина.
     * Создается объявление. У объявления выставляется машина и марка. После этого объявление добавляется в БД.
     * Из БД получается объявление по марке машины.
     */
    @Test
    public void selectOffersByCarTest() {
        Brand brand = new Brand();
        brand.setName("brand");
        brand = manager.addBrand(brand);

        Offer offer = new Offer();
        Car car = new Car();
        offer.setCar(car);
        offer.getCar().setBrand(brand);

        offer = manager.addOffer(offer);
        assertThat(offer, is(manager.selectOffersByCar(manager.selectAllOffers().get(0).getCar().getId()).get(0)));
    }
}