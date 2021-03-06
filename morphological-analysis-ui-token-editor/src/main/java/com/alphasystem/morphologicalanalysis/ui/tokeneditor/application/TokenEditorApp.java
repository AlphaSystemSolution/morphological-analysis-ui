package com.alphasystem.morphologicalanalysis.ui.tokeneditor.application;

import com.alphasystem.app.morphologicalengine.spring.MorphologicalEngineConfiguration;
import com.alphasystem.morphologicalanalysis.ui.application.AbstractJavaFxApplicationSupport;
import com.alphasystem.morphologicalanalysis.ui.spring.support.CommonConfiguration;
import com.alphasystem.morphologicalanalysis.ui.tokeneditor.control.TokenEditorPane;
import com.alphasystem.morphologicalanalysis.ui.tokeneditor.spring.TokenEditorConfiguration;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

/**
 * @author sali
 */
@Lazy
@SpringBootApplication
@Import({MorphologicalEngineConfiguration.class, CommonConfiguration.class, TokenEditorConfiguration.class})
public class TokenEditorApp extends AbstractJavaFxApplicationSupport {

    @Value("${app.ui.title}") private String windowTitle;
    @Autowired private TokenEditorPane mainPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

        primaryStage.setTitle(windowTitle);
        final Scene scene = new Scene(mainPane);
        scene.getStylesheets().addAll("/styles/glyphs_custom.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launchApp(TokenEditorApp.class, args);
    }
}
