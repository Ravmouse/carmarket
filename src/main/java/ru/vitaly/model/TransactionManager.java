package ru.vitaly.model;

import ru.vitaly.entity.User;
import java.util.List;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public interface TransactionManager<T> extends AutoCloseable {
    /**
     * @param offer объявление для добавления в БД.
     * @return добавленное объявление.
     */
    T addOffer(T offer);
    /**
     * @param offerId номер объявления.
     * @return найденное объявление по номеру.
     */
    T findById(int offerId);
    /**
     * @return список всех объявлений из БД.
     */
    List<T> selectAllOffers();
    /**
     * @param name имя пользователя из БД.
     * @param password пароль пользователя из БД.
     * @return экземпляр класса User.
     */
    User getUser(String name, String password);
    /**
     * @return список объявлений за текущий день.
     */
    List<T> selectTodayOffers();
    /**
     * @param id порядковый номер машины.
     * @return список объявлений, в которых марки машин равны id.
     */
    List<T> selectOffersByCar(int id);
}