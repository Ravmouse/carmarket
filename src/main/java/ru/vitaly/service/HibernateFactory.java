package ru.vitaly.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class HibernateFactory {
    /**
     * @param name имя файла конфигурации.
     * @return фабрику сессий.
     */
    public static SessionFactory getFactory(final String name) {
        return new Configuration().configure(name).buildSessionFactory();
    }
}