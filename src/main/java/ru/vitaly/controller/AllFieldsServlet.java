package ru.vitaly.controller;

import org.apache.log4j.Logger;
import ru.vitaly.utils.Utils;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class AllFieldsServlet extends HttpServlet {
    /**
     * Логгер.
     */
    private static final Logger LOG = Logger.getLogger(Utils.getNameOfTheClass());
    /**
     * Для хранения JSON-данных.
     */
    private final StringBuilder data = new StringBuilder();

    /**
     * Данные из файла fields.json кэшируются в StringBuilder'е при создании сервлета.
     * @param sc servletConfig.
     * @throws ServletException искл.
     */
    @Override
    public void init(ServletConfig sc) throws ServletException {
        try (final BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                Utils.getResourcePath("fields.json")), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException io) {
            LOG.warn("Исключение в методе init() сервлета AllFieldsServlet.", io);
        }
    }

    /**
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException искл.
     * @throws IOException искл.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        try (final PrintWriter w = resp.getWriter()) {
            w.append(data);
            w.flush();
        }
    }
}