package it.saimao.myanmardictionary;

public class Utils {
    private static String savedWord = "";

    public static String getSavedWord() {
        return savedWord;
    }

    public static void setSavedWord(String savedWord) {
        Utils.savedWord = savedWord;
    }
}
