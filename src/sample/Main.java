package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxmldir/sample.fxml"));
        primaryStage.setTitle("Garlic");
        Image applicationIcon = new Image(getClass().getResourceAsStream("img/icon.png"));
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        stage = primaryStage;
    }

    public static void restart(){
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
