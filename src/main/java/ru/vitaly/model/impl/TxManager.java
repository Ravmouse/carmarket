package ru.vitaly.model.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.vitaly.service.Wrapper;
import ru.vitaly.entity.Brand;
import ru.vitaly.entity.Offer;
import ru.vitaly.entity.User;
import ru.vitaly.model.TransactionManager;
import ru.vitaly.utils.Utils;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class TxManager implements TransactionManager<Offer> {
    /**
     * Экз. данного класса.
     */
    private static final TxManager INSTANCE = new TxManager();
    /**
     * Фабрика сессий.
     */
    public final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    /**
     * Логгер.
     */
    private static final Logger LOG = Logger.getLogger(Utils.getNameOfTheClass());

    /**
     * Приватный конструктор.
     */
    private TxManager() { }

    /**
     * @return экз. этого класса.
     */
    public static TxManager getInstance() {
        return INSTANCE;
    }

    /**
     * @param offer объявление для добавления в БД.
     * @return добавленное объявление.
     */
    @Override
    public Offer addOffer(Offer offer) {
        return new Wrapper(factory).perform(session -> {
            session.save(offer);
            return offer;
        });
    }

    /**
     * @param offerId номер объявления.
     * @return найденное объявление по номеру.
     */
    @Override
    public Offer findById(int offerId) {
        return new Wrapper(factory).perform(session -> {
            final Offer offer = session.get(Offer.class, offerId);
            return Optional.ofNullable(offer).orElse(new Offer());
        });
    }

    /**
     * @return список всех объявлений из БД.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Offer> selectAllOffers() {
        return new Wrapper(factory).perform(session -> {
            final Query query = session.createQuery("from Offer o order by o.id");
            return query.list();
        });
    }

    /**
     * @param name имя пользователя из БД.
     * @param password пароль пользователя из БД.
     * @return экземпляр класса User.
     */
    @Override
    public User getUser(String name, String password) {
        return new Wrapper(factory).perform(session -> {
            User user = null;
            try {
                user = session.createQuery("from UserOffer u WHERE u.name = :name AND u.password = :password", User.class)
                        .setParameter("name", name)
                        .setParameter("password", password)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.warn(e);
            }
            return user;
        });
    }

    /**
     * @return список объявлений за текущий день.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Offer> selectTodayOffers() {
        return new Wrapper(factory).perform(session -> {
            final Query query = session.createQuery("from Offer o where extract(day from o.createDate)"
                    + " = extract(day from current_date()) order by o.id");
            return query.list();
        });
    }

    /**
     * Создается экземпляр класса Brand из БД по id.
     * Далее - запрос в БД на получение списка объявлений, в которых марки машин равны полученному экз.класса Brand.
     * @param id порядковый номер машины.
     * @return список объявлений, в которых марки машин равны id.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Offer> selectOffersByCar(int id) {
        return new Wrapper(factory).perform(session -> {
            final Brand brand = session.find(Brand.class, id);
            final Query query = session.createQuery("from Offer o join fetch o.car c where c.brand = :brand")
                    .setParameter("brand", brand);
            return query.list();
        });
    }

    /**
     * Закрывает фабрику.
     * @throws Exception искл.
     */
    @Override
    public void close() throws Exception {
        if (factory != null) {
            factory.close();
        }
    }
}