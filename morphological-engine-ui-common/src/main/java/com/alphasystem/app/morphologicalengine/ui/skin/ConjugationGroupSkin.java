package com.alphasystem.app.morphologicalengine.ui.skin;

import com.alphasystem.app.morphologicalengine.ui.ConjugationGroupView;
import com.alphasystem.app.morphologicalengine.ui.util.MorphologicalEnginePreferences;
import com.alphasystem.arabic.model.ArabicWord;
import com.alphasystem.arabic.ui.ArabicLabelView;
import com.alphasystem.fx.ui.util.UiUtilities;
import com.alphasystem.morphologicalengine.model.ConjugationGroup;
import com.alphasystem.morphologicalengine.model.ConjugationTuple;
import com.alphasystem.util.AppUtil;
import com.alphasystem.util.GenericPreferences;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author sali
 */
abstract class ConjugationGroupSkin<G extends ConjugationGroup, C extends ConjugationGroupView<G>> extends BorderPane {

    protected final C control;

    @FXML protected ArabicLabelView termLabel;
    @FXML protected ArabicLabelView row11;
    @FXML protected ArabicLabelView row12;
    @FXML protected ArabicLabelView row13;
    @FXML protected ArabicLabelView row21;
    @FXML protected ArabicLabelView row22;
    @FXML protected ArabicLabelView row23;

    private final MorphologicalEnginePreferences preferences;

    ConjugationGroupSkin(C control) {
        this.control = control;
        this.preferences = GenericPreferences.getInstance(MorphologicalEnginePreferences.class);
        try {
            UiUtilities.loadFXML(this, AppUtil.getUrl(String.format("fxml.%s.fxml", control.getClass().getSimpleName())), null);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @FXML
    void initialize() {
        control.groupProperty().addListener((observable, oldValue, newValue) -> {
            Paint disableStroke = control.isEmpty() ? Color.TRANSPARENT : Color.LIGHTGRAY;
            setDisableStroke(disableStroke);
            termLabel.setLabel(newValue.getTermType());
            setupRows(newValue);
        });

        final G group = control.getGroup();
        termLabel.setLabel((group == null) ? null : group.getTermType());
        setupRows(group);
        setFont(preferences.getArabicFont());
        Paint disableStroke = control.isEmpty() ? Color.TRANSPARENT : Color.LIGHTGRAY;
        setDisableStroke(disableStroke);
    }

    protected abstract void setupRows(G group);

    void setupRow(ConjugationTuple tuple, ArabicLabelView... labels) {
        ArabicWord[] data = getData(tuple);
        if (data == null) {
            data = new ArabicWord[labels.length];
        }
        for (int i = 0; i < labels.length; i++) {
            labels[i].setLabel(data[i]);
        }
    }

    private ArabicWord[] getData(ConjugationTuple tuple) {
        if (tuple == null) {
            return null;
        }
        ArabicWord[] words = new ArabicWord[3];
        String value = tuple.getSingular();
        if (!StringUtils.isBlank(value)) {
            words[0] = ArabicWord.fromUnicode(value);
        }
        value = tuple.getDual();
        if (!StringUtils.isBlank(value)) {
            words[1] = ArabicWord.fromUnicode(value);
        }
        value = tuple.getPlural();
        if (!StringUtils.isBlank(value)) {
            words[2] = ArabicWord.fromUnicode(value);
        }
        return words;
    }

    protected void setDisableStroke(Paint disableStroke) {
        termLabel.setDisabledStroke(disableStroke);
        row11.setDisabledStroke(disableStroke);
        row12.setDisabledStroke(disableStroke);
        row13.setDisabledStroke(disableStroke);
        row21.setDisabledStroke(disableStroke);
        row22.setDisabledStroke(disableStroke);
        row23.setDisabledStroke(disableStroke);
    }

    protected void setFont(Font font) {
        row11.setFont(font);
        row12.setFont(font);
        row13.setFont(font);
        row21.setFont(font);
        row22.setFont(font);
        row23.setFont(font);
    }
}
