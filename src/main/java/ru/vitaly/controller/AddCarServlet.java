package ru.vitaly.controller;

import ru.vitaly.entity.Offer;
import ru.vitaly.model.TransactionManager;
import ru.vitaly.model.impl.TxManager;
import ru.vitaly.service.OfferCreator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static ru.vitaly.utils.Utils.getRequestString;
import static ru.vitaly.utils.Utils.parse;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class AddCarServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String str = getRequestString(req);
        MANAGER.addOffer(new OfferCreator()
                .createOffer(
                        Integer.parseInt(parse("brand", str)),
                        Integer.parseInt(parse("model", str)),
                        parse("year", str),
                        Integer.parseInt(parse("carBody", str)),
                        Integer.parseInt(parse("transmission", str)),
                        Integer.parseInt(parse("engine", str)),
                        parse("imgName", str)
        ));
    }
}