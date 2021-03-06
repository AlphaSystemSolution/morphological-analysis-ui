package com.alphasystem.morphologicalengine.ui;

import com.alphasystem.arabic.model.ArabicLetterType;
import com.alphasystem.arabic.model.NamedTemplate;
import com.alphasystem.fx.ui.Browser;
import com.alphasystem.morphologicalanalysis.morphology.model.ConjugationData;
import com.alphasystem.morphologicalanalysis.morphology.model.ConjugationTemplate;
import com.alphasystem.morphologicalanalysis.morphology.model.RootLetters;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author sali
 */
public class MorphologicalChartViewerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Morphological Chart Viewer");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.setWidth(bounds.getWidth() / 4);
        primaryStage.setHeight(bounds.getHeight() / 4);

        ConjugationTemplate conjugationTemplate = new ConjugationTemplate();
        ConjugationData conjugationData = new ConjugationData();
        conjugationData.setTemplate(NamedTemplate.FORM_X_TEMPLATE);
        conjugationData.setTranslation(null);
        conjugationData.setRootLetters(new RootLetters(ArabicLetterType.GHAIN, ArabicLetterType.FA, ArabicLetterType.RA));
        conjugationTemplate.getData().add(conjugationData);

        /*MorphologicalChartEngine engine = new MorphologicalChartEngine(conjugationTemplate);
        final MorphologicalChart morphologicalChart = engine.createMorphologicalCharts().get(0);

        MorphologicalChartViewerControl morphologicalChartViewer = new MorphologicalChartViewerControl();
        morphologicalChartViewer.setMorphologicalChart(morphologicalChart);*/

        final Browser browser = new Browser();
        browser.loadUrl("http://ejtaal.net/aa/index.html#bwq=fEl");
        final BorderPane borderPane = new BorderPane();
        borderPane.setCenter(browser);

        Scene scene = new Scene(borderPane);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
