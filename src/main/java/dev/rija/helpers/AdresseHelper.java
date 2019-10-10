package dev.rija.helpers;

public class AdresseHelper {
    public static boolean isAdresseActive(String active) {
        return "active".equals(active);
    }

    public static boolean sansDateEffet(String condition) {
        return "sans date d'effet".equals(condition);
    }
}
