package com.khesl.ftploader.FtpLoader.utils;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

public class MyParametersController {
    private static final String CONFIG_PATH = "src/main/resources/config.ini";

    public MyParametersController(){ super(); }

    public static void createConfig(String path) throws IOException {
        // create props file
        Properties props = new Properties();
        props.store(new FileOutputStream(path), "create new property files by " + Calendar.getInstance().getTime());
    }

    public static String getProperties(String propertyName) throws IOException {
        // меняем свойства
        Properties props = new Properties();
        props.load(new FileReader(CONFIG_PATH));
        return props.get(propertyName).toString();
    }

    public static void setProperties(String propertyName, String propertyValue) throws IOException {
        // меняем свойства
        Properties props = new Properties();
        props.load(new FileReader(CONFIG_PATH));
        props.setProperty(propertyName, propertyValue);
        props.store(new FileOutputStream(CONFIG_PATH),
                "change property '" + propertyName + "' to value '" + propertyValue + "' by " + Calendar.getInstance().getTime());
    }
}
