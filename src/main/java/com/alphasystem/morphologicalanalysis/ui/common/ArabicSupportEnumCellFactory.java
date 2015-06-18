package com.alphasystem.morphologicalanalysis.ui.common;

import com.alphasystem.arabic.model.ArabicSupportEnum;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import static com.alphasystem.morphologicalanalysis.ui.common.ArabicSupportEnumCellFactory.ListType.LABEL_AND_CODE;

/**
 * @author sali
 */
public class ArabicSupportEnumCellFactory<T extends ArabicSupportEnum> implements
        Callback<ListView<T>, ListCell<T>> {

    private final ListType type;

    public ArabicSupportEnumCellFactory() {
        this(LABEL_AND_CODE);
    }

    public ArabicSupportEnumCellFactory(ListType type) {
        this.type = type;
    }

    @Override
    public ListCell<T> call(ListView<T> param) {
        return new ArabicSupportEnumListCell<>(type);
    }

    public enum ListType {
        LABEL_ONLY, CODE_ONLY, LABEL_AND_CODE;
    }
}