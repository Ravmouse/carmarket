package ru.vitaly.service;

import ru.vitaly.entity.Offer;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class OfferCreator {
    /**
     * @param brand марка.
     * @param model модель.
     * @param year год.
     * @param body кузов.
     * @param transmission коробка передач.
     * @param engine двигатель.
     * @param image изображение.
     * @return объявление.
     */
    public Offer createOffer(int brand, int model, String year, int body, int transmission, int engine, String image) {
        final Offer offer = new Offer();
        offer.setImgName(image);
        offer.setSold(false);
        offer.setCar(new CarCreator().createCar(brand, model, year, body, transmission, engine));
        return offer;
    }
}