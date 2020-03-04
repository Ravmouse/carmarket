package ru.vitaly.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class Utils {
    /**
     * Логгер.
     */
    private static final Logger LOG = Logger.getLogger(getNameOfTheClass());

    /**
     * Выкидывается исключение и, ввиду того, что этот метод должен запускаться из другого класса, получается имя
     * класса, из которого был вызван данный метод.
     * @return имя класса в виде строки, из которого был выполнен этот метод.
     */
    public static String getNameOfTheClass() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException re) {
            return re.getStackTrace()[1].getClassName();
        }
    }

    /**
     * Находится файл-ресурс и от начала его пути удаляется строка "file:/".
     * @param fileName имя файла-ресурса.
     * @return путь до файла-ресурса.
     */
    public static String getResourcePath(final String fileName) {
        final String temp = Utils.class.getClassLoader().getResource(fileName).toExternalForm();
        return temp.substring(temp.indexOf("C"), temp.length());
    }

    /**
     * @param req запрос.
     * @return строка из запроса.
     */
    public static String getRequestString(final HttpServletRequest req) {
        String result = null;
        try (final BufferedReader reader = req.getReader()) {
            result = reader.readLine();
        } catch (IOException io) {
            LOG.warn(io);
        }
        return result;
    }

    /**
     * @param items лист элементов.
     * @return строка в виде json-объекта.
     * @throws IOException искл.
     */
    public static <T> String jsonFromList(List<T> items) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper.writeValueAsString(items);
    }

    /**
     * @param key ключ, как строка, для поиска значения.
     * @param field строка, представляющая JSON.
     * @return значение из строки field.
     */
    public static String parse(String key, String field) {
        final String str = field.substring(field.indexOf(key) + key.length() + 3);
        return str.substring(0, str.indexOf("\""));
    }
}