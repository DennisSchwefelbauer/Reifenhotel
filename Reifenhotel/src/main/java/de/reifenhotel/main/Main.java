package de.reifenhotel.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/de/reifenhotel/main/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Reifenhotel");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
}

//TODO ExceptionHandling vervollständigen
//TODO Bugs
        //cID in CustomerEdit, Problem mit der übertragenen cID wenn Kunde gesucht wurde
//TODO Database aufräumen und auf Klassen aufteilen