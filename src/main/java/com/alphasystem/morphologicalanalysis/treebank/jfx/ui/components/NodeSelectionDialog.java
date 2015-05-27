package com.alphasystem.morphologicalanalysis.treebank.jfx.ui.components;

import com.alphasystem.morphologicalanalysis.model.Chapter;
import com.alphasystem.morphologicalanalysis.model.Token;
import com.alphasystem.morphologicalanalysis.treebank.jfx.ui.Global;
import com.alphasystem.morphologicalanalysis.treebank.jfx.ui.model.TokenListCell;
import com.alphasystem.morphologicalanalysis.treebank.jfx.ui.util.RepositoryTool;
import com.alphasystem.morphologicalanalysis.ui.util.ChapterAdapter;
import com.alphasystem.morphologicalanalysis.ui.util.VerseAdapter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.String.format;
import static javafx.scene.Cursor.*;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

/**
 * @author sali
 */
public class NodeSelectionDialog extends Dialog<List<Token>> {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources");
    private static final String STYLE = format("-fx-font-family: %s;-fx-font-size: %s;-fx-font-weight:bold",
            "\"Arabic Typesetting\"", "20pt");

    // UI elements
    private ComboBox<ChapterAdapter> chapterNameComboBox;
    private ComboBox<VerseAdapter> verseAdapterComboBox;
    private ListView<TokenListCell> tokensList;

    // other elements
    private RepositoryTool repositoryTool = RepositoryTool.getInstance();
    private List<ChapterAdapter> chapters;
    private ChapterAdapter selectedChapter;
    private VerseAdapter selectedVerse;
    private List<Token> selectedItems = new ArrayList<>();

    public NodeSelectionDialog() {
        setTitle("Import Tokens");
        setHeaderText("Select chapter and verse.");

        Service<List<ChapterAdapter>> service = repositoryTool.getAllChapters();
        service.start();
        service.setOnSucceeded(event -> {
            chapters = (List<ChapterAdapter>) event.getSource().getValue();
            initDialog();
            Global.getInstance().getGlobalScene().setCursor(DEFAULT);
        });
    }

    private void initDialog() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        verseAdapterComboBox = new ComboBox<>();
        verseAdapterComboBox.setStyle(STYLE);
        verseAdapterComboBox.setOnAction(event -> {
            selectedVerse = verseAdapterComboBox.getSelectionModel().getSelectedItem();
            if (selectedVerse == null) {
                return;
            }
            loadTokens();
        });

        Label label = new Label(RESOURCE_BUNDLE.getString("chapterName.label"));
        grid.add(label, 0, 0);

        chapterNameComboBox = new ComboBox();
        chapterNameComboBox.setStyle(STYLE);
        chapterNameComboBox.getItems().addAll(chapters.toArray(new ChapterAdapter[chapters.size()]));
        chapterNameComboBox.getSelectionModel().select(0);
        selectedChapter = chapters.get(0);
        chapterNameComboBox.setOnAction(event -> {
            selectedChapter = chapterNameComboBox.getSelectionModel().getSelectedItem();
            initVerseComboBox();
        });
        label.setLabelFor(chapterNameComboBox);
        grid.add(chapterNameComboBox, 0, 1);

        label = new Label(RESOURCE_BUNDLE.getString("verseNumber.label"));
        grid.add(label, 1, 0);
        initVerseComboBox();
        grid.add(verseAdapterComboBox, 1, 1);

        grid.add(new Separator(), 0, 2, 2, 1);

        tokensList = new ListView<>();
        tokensList.setStyle(STYLE);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tokensList);
        grid.add(borderPane, 0, 3, 2, 1);

        ButtonType okButton = new ButtonType("OK", OK_DONE);
        setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return selectedItems;
            }
            return new ArrayList<Token>();
        });
        ButtonType cancelButton = new ButtonType("Cancel", CANCEL_CLOSE);
        getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
        getDialogPane().setContent(grid);
        getDialogPane().setPrefWidth(400);
    }

    private void loadTokens() {
        if (selectedChapter == null && selectedVerse == null) {
            return;
        }
        Global.getInstance().getGlobalScene().setCursor(WAIT);
        Service<List<Token>> service = repositoryTool.getTokens(selectedVerse.getChapterNumber(), selectedVerse.getVerseNumber());
        service.start();
        service.setOnSucceeded(event1 -> {
            List<Token> tokens = (List<Token>) event1.getSource().getValue();
            initTokenList(tokens);
            Global.getInstance().getGlobalScene().setCursor(HAND);
        });
    }

    private void initVerseComboBox() {
        ObservableList<VerseAdapter> items = verseAdapterComboBox.getItems();
        items.remove(0, items.size());

        List<VerseAdapter> verseNumbers = new ArrayList<>();
        if (selectedChapter != null) {
            Chapter chapter = selectedChapter.getChapter();
            int verseCount = chapter.getVerseCount();
            for (int i = 1; i <= verseCount; i++) {
                verseNumbers.add(new VerseAdapter(chapter.getChapterNumber(), i));
            }
        }
        int size = verseNumbers.size();
        verseAdapterComboBox.getItems().addAll(verseNumbers.toArray(new VerseAdapter[size]));
        if (size > 0) {
            verseAdapterComboBox.getSelectionModel().select(0);
            selectedVerse = verseAdapterComboBox.getItems().get(0);
            loadTokens();
        }
        verseAdapterComboBox.requestLayout();
    }

    private void initTokenList(List<Token> tokens) {
        selectedItems.clear();
        ObservableList<TokenListCell> items = tokensList.getItems();
        items.remove(0, items.size());
        ObservableList<TokenListCell> listItems = FXCollections.observableArrayList();
        for (Token token : tokens) {
            TokenListCell cell = new TokenListCell(token);
            cell.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    selectedItems.add(cell.getToken());
                }
            });
            listItems.add(cell);
        }
        tokensList.setItems(listItems);
        Callback<TokenListCell, ObservableValue<Boolean>> getProperty = new Callback<TokenListCell, ObservableValue<Boolean>>() {
            @Override
            public BooleanProperty call(TokenListCell layer) {
                return layer.selectedProperty();

            }
        };
        Callback<ListView<TokenListCell>, ListCell<TokenListCell>> forListView = CheckBoxListCell.forListView(getProperty);
        tokensList.setCellFactory(forListView);
        tokensList.requestLayout();
    }
}