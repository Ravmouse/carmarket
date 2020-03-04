package ru.vitaly.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ru.vitaly.utils.Utils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
public class FileUploadServlet extends HttpServlet {
    /**
     * Аплоад файлов.
     */
    private ServletFileUpload upload;
    /**
     * Путь для сохранения.
     */
    private String path;
    /**
     * Логгер.
     */
    private static final Logger LOGGER = Logger.getLogger(Utils.getNameOfTheClass());

    /**
     * @throws ServletException искл.
     */
    @Override
    public void init() throws ServletException {
        final DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024 * 3);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 40);
        upload.setSizeMax(1024 * 1024 * 50);

        path = String.format("%s", getServletContext().getRealPath("/resources/img"));

        final File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException искл.
     * @throws IOException искл.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            req.setAttribute("message", "Error!");
        } else {
            try {
                final List<FileItem> items =  upload.parseRequest(req);
                if (items != null && items.size() > 0) {
                    items.stream().filter(i -> !i.isFormField()).forEach(i -> {
                        try {
                            i.write(new File(String.format("%s%s%s", path, File.separator, i.getName())));
                            req.setAttribute("message", "Загрузка файла выполнена успешно!");
                        } catch (Exception e) {
                            LOGGER.warn("Ошибка при записи файла на диск.");
                        }
                    });
                }
            } catch (FileUploadException fue) {
                LOGGER.warn("Ошибка во время парсинга запроса.");
            }
        }
        doGet(req, resp);
    }

    /**
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException искл.
     * @throws IOException искл.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String message = (String) req.getAttribute("message");
        resp.setContentType("application/text");
        try (final PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.append(message);
            writer.flush();
        }
    }
}