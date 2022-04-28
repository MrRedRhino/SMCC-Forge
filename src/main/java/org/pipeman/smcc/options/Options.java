package org.pipeman.smcc.options;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

import java.io.*;
import java.nio.file.Paths;
import java.util.Properties;

public class Options {
    public static String key = "Super Secure";
    public static boolean encryptTeamMessages = false;
    public static boolean encryptTell = false;
    public static boolean encryptNormalChat = false;

    public static void readConfigFile() {

        File configFile = Paths.get("config/smcc.properties").toFile();
        if (!configFile.exists()) {
            storeConfigFile();
        }

        try (InputStream input = new FileInputStream("config/smcc.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            key = prop.getProperty("key", key);
            encryptNormalChat = Boolean.parseBoolean(prop.getProperty("encryptNormalChat"));
            encryptTell = Boolean.parseBoolean(prop.getProperty("encryptTell"));
            encryptTeamMessages = Boolean.parseBoolean(prop.getProperty("encryptTeamMessages"));

        } catch (IOException ignored) {
        }
    }

    public static void storeConfigFile() {
        try (OutputStream output = new FileOutputStream("config/smcc.properties")) {

            Properties prop = new Properties();

            prop.setProperty("key", key);
            prop.setProperty("encryptNormalChat", String.valueOf(encryptNormalChat));
            prop.setProperty("encryptTell", String.valueOf(encryptTell));
            prop.setProperty("encryptTeamMessages", String.valueOf(encryptTeamMessages));

            prop.store(output, null);

        } catch (IOException ignored) {
        }
    }
}
