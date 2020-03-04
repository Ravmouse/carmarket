package ru.vitaly.utils;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class UtilsTest {
    /**
     * Проверяется работа метода parse().
     */
    @Test
    public void parseTest() {
        String field = "{\"brand\":\"2\",\"model\":\"1\",\"year\":\"2020-02-10\",\"carBody\":\"2\","
                + "\"transmission\":\"1\",\"engine\":\"2\",\"imgName\":\"opel.jpg\"}";
        assertThat(Utils.parse("brand", field), is("2"));
        assertThat(Utils.parse("model", field), is("1"));
        assertThat(Utils.parse("year", field), is("2020-02-10"));
        assertThat(Utils.parse("carBody", field), is("2"));
        assertThat(Utils.parse("transmission", field), is("1"));
        assertThat(Utils.parse("engine", field), is("2"));
        assertThat(Utils.parse("imgName", field), is("opel.jpg"));
    }
}