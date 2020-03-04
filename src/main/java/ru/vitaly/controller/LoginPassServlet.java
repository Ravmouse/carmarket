package ru.vitaly.controller;

import org.apache.log4j.Logger;
import ru.vitaly.service.Validate;
import ru.vitaly.utils.Utils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static ru.vitaly.utils.Utils.getRequestString;
import static ru.vitaly.utils.Utils.parse;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class LoginPassServlet extends HttpServlet {
    /**
     * Проверка.
     */
    private static final Validate VALIDATE = Validate.getInstance();
    /**
     * Логгер.
     */
    private static final Logger LOGGER = Logger.getLogger(Utils.getNameOfTheClass());

    /**
     * @param req запрос.
     * @param res ответ.
     * @throws ServletException исключение.
     * @throws IOException исключение.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String str = getRequestString(req);
        final String name = parse("name", str);
        final String password = parse("password", str);

        boolean answer = VALIDATE.userExist(name, password);

        res.setCharacterEncoding("UTF-8");
        try (final PrintWriter w = res.getWriter()) {
            w.write(Boolean.toString(answer));
            w.flush();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }
}