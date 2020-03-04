package ru.vitaly.service;

import ru.vitaly.entity.Offer;
import ru.vitaly.entity.User;
import ru.vitaly.model.TransactionManager;
import ru.vitaly.model.impl.TxManager;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class Validate {
    /**
     * Экз. данного класса.
     */
    private static final Validate INSTANCE = new Validate();
    /**
     * Менеджер транзакций.
     */
    private static final TransactionManager<Offer> MANAGER = TxManager.getInstance();

    /**
     * Приватный конструктор.
     */
    private Validate() { }

    /**
     * @return экз. класса.
     */
    public static Validate getInstance() {
        return INSTANCE;
    }

    /**
     * @param name имя пользователя для проверки в БД.
     * @param password пароль пользователя для проверки в БД.
     * @return true или false.
     */
    public boolean userExist(String name, String password) {
        final User user = MANAGER.getUser(name, password);
        return user != null;
    }
}