package ru.vitaly.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.vitaly.utils.Utils;
import java.util.function.Function;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class Wrapper {
    /**
     * Фабрика сессий.
     */
    private final SessionFactory factory;
    /**
     * Логгер.
     */
    private static final Logger LOG = Logger.getLogger(Utils.getNameOfTheClass());

    /**
     * @param factory фабрика сессий.
     */
    public Wrapper(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * @param command функция для выполнения.
     * @param <T> обобщенный параметр.
     * @return результат выполнения функции.
     */
    public <T> T perform(final Function<Session, T> command) {
        T rsl = null;
        final Session session = factory.openSession();
        final Transaction tr = session.beginTransaction();
        try {
            rsl = command.apply(session);
            tr.commit();
        } catch (final Exception e) {
            LOG.warn("Ошибка при работе с транзакцией: " + e);
            e.printStackTrace();
            tr.rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}