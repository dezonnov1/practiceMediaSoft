package org.coolCompany;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;


public class AppConfig {
    public static final Locale APP_LOCALE = new Locale("ru", "RU");
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String TIME_PATTERN = "HH:mm XXX";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN /*,APP_LOCALE*/);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN /*,APP_LOCALE*/);
    public static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM.yyyy"/*, APP_LOCALE*/);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN/*, APP_LOCALE*/);
    public static final WeekFields WEEK_FIELDS = WeekFields.of(APP_LOCALE);

    // java -Dmyapp.home=/opt/myapp -Dmyapp.output=/data/reports -jar app.jar
    // Основные директории
    public static final File APP_HOME = initDir(System.getProperty("myapp.home", System.getProperty("user.home") + "/myapp"));
    public static final File OUTPUT_DIR = initDir(System.getProperty("myapp.output", APP_HOME + "/output"));
    public static final File INPUT_DIR = initDir(System.getProperty("myapp.input", APP_HOME + "/input"));
//    public static final File LOGS_DIR = initDir(System.getProperty("myapp.logs", APP_HOME + "/logs"));
    public static final File TEMP_DIR = initDir(System.getProperty("java.io.tmpdir") + "/myapp");
//    public static final File CONFIG_FILE = new File(APP_HOME, "config.properties");

    private static File initDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.err.println("⚠ Не удалось создать директорию: " + path);
            }
        }
        return dir;
    }

    // Для отладки
    public static void printConfig() {
        System.out.println("App home: " + APP_HOME.getAbsolutePath());
        System.out.println("Output dir: " + OUTPUT_DIR.getAbsolutePath());
//        System.out.println("Logs dir: " + LOGS_DIR.getAbsolutePath());
        System.out.println("Temp dir: " + TEMP_DIR.getAbsolutePath());
//        System.out.println("Config file: " + CONFIG_FILE.getAbsolutePath());
    }



}
