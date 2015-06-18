package com.alphasystem.morphologicalanalysis.ui.common;

import com.alphasystem.morphologicalanalysis.wordbyword.model.support.PartOfSpeech;
import javafx.scene.text.Font;

import java.util.List;
import java.util.ResourceBundle;

import static com.alphasystem.morphologicalanalysis.wordbyword.model.support.PartOfSpeech.DEFINITE_ARTICLE;
import static com.alphasystem.morphologicalanalysis.wordbyword.model.support.PartOfSpeech.QURANIC_PUNCTUATION;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontPosture.REGULAR;
import static javafx.scene.text.FontWeight.BOLD;

/**
 * @author sali
 */
public class Global {

    public static final List<PartOfSpeech> PART_OF_SPEECH_EXCLUDE_LIST = asList(
            DEFINITE_ARTICLE,
            QURANIC_PUNCTUATION);
    public static final String TREE_BANK_STYLE_SHEET = Global.class.getResource("/treebank.css").toExternalForm();
    public static final String ARABIC_FONT_NAME = System.getProperty("arabic-font-name", "Traditional Arabic");
    public static final Font ARABIC_FONT_BIG = font(ARABIC_FONT_NAME, REGULAR, 48);
    public static final Font ARABIC_FONT_MEDIUM = font(ARABIC_FONT_NAME, REGULAR, 36);
    public static final Font ARABIC_FONT_MEDIUM_BOLD = font(ARABIC_FONT_NAME, BOLD, REGULAR, 30);
    public static final Font ARABIC_FONT_SMALL = font(ARABIC_FONT_NAME, REGULAR, 24);
    public static final String NONE_SELECTED = "None selected";
    public static final Font ENGLISH_FONT = font("Candara", REGULAR, 16);
    public static final Font ENGLISH_FONT_SMALL = font("Candara", REGULAR, 14);
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources");
    private static Global instance;

    private Global() {
    }

    public synchronized static Global getInstance() {
        if (instance == null) {
            instance = new Global();
        }
        return instance;
    }

    public static String getLabel(Class<?> klass, String label) {
        String key = format("%s.%s.label", klass.getSimpleName(), label);
        return RESOURCE_BUNDLE.getString(key);
    }
}