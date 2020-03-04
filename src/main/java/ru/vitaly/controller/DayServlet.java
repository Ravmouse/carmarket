package ru.vitaly.controller;

import org.apache.log4j.Logger;
import ru.vitaly.entity.Offer;
import ru.vitaly.model.TransactionManager;
import ru.vitaly.model.impl.TxManager;
import ru.vitaly.utils.Utils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static ru.vitaly.utils.Utils.jsonFromList;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class DayServlet extends HttpServlet {
    /**
     * Логгер.
     */
    private static final Logger LOGGER = Logger.getLogger(Utils.getNameOfTheClass());
    /**
     * Менеджер транзакций.
     */
    private static final TransactionManager<Offer> MANAGER = TxManager.getInstance();

    /**
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException искл.
     * @throws IOException искл.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        try (final PrintWriter w = resp.getWriter()) {
            final String results = jsonFromList(MANAGER.selectTodayOffers());
            resp.setContentType("application/json");
            w.write(results);
            w.flush();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }
}