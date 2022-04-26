package org.pipeman.smcc.options;

public class Options {
    public static String key;
    public static boolean encryptTeamMessages = false;
    public static boolean encryptTell = false;
    public static boolean encryptNormalChat = false;

    public static void readConfigFile() {
        key = "1234";
        encryptTeamMessages = true;
        encryptTell = true;
        encryptNormalChat = true;
    }
}
