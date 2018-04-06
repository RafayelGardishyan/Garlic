package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sample.data.Const;
import sample.data.Values;
import sample.web_requesters.JsonReader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Platform.exit;

public class Controller {

    private static final Logger LOGGER = Logger.getLogger( Controller.class.getName() );

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginbutton;

    @FXML
    private TextField username_f;

    @FXML
    private PasswordField pw_f;

    @FXML
    private Text registertext;

    @FXML
    private Label failed_text;

    @FXML
    void initialize() throws IOException, JSONException {
        JSONArray levels = JsonReader.GetJsonArray(Const.server_base_url + "level?format=json");
        failed_text.setVisible(false);
        Values.ValuesLoggerSetup();
        LOGGER.addHandler(new ConsoleHandler());
        LOGGER.addHandler(new FileHandler());
        registertext.setOnMouseClicked((event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("fxmldir/Register.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                Image applicationIcon = new Image(getClass().getResourceAsStream("img/icon.png"));
                stage.getIcons().add(applicationIcon);
                stage.setScene(new Scene(root));
                registertext.getScene().getWindow().hide();
                stage.setResizable(false);
                stage.showAndWait();
        }));


        // Login Check Function
        LOGGER.log(Level.FINE, "Getting user info from server");
        JSONArray json_users = JsonReader.GetJsonArray(Const.server_base_url + "user?format=json");
        LOGGER.log(Level.FINE, "Got user info from server");
        loginbutton.setOnAction((ActionEvent event) -> {
            LOGGER.log(Level.FINE, "Clicked on login button");
            try{
                String enetered_username = username_f.getText();
                String enetered_password = pw_f.getText();
                LOGGER.log(Level.FINE, "Got entered info");
                for (int j=0; j<json_users.length(); j++){
                    JSONObject jsonGeneralData = new JSONObject(json_users.get(j).toString());

                    String Username = jsonGeneralData.get("Username").toString();
                    String Password = jsonGeneralData.get("Password").toString();
                    LOGGER.log(Level.FINE, "Got current user data");

                    if (Username.equals(enetered_username) && Password.equals(enetered_password)){
                        failed_text.setVisible(false);
                        LOGGER.log(Level.FINE, "User match");
                        String levelText = "";
                        for (int i = 0; i<levels.length(); i++){
                            JSONObject level = null;
                            try {
                                level = levels.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                assert level != null;
                                String current = level.get("id").toString();
                                if (jsonGeneralData.get("Level").toString().equals(current)){
                                    levelText = level.get("name").toString();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                exit();
                            }
                        }
                        Values.setUsername(jsonGeneralData.get("Username").toString());
                        Values.setCurrent_course(Integer.valueOf(jsonGeneralData.get("Current_Course").toString()));
                        Values.setLevel(Integer.valueOf(jsonGeneralData.get("Level").toString()));
                        Values.setLevel_text(levelText);
                        Values.setCompleted_excercises(jsonGeneralData.getJSONArray("Completed_Excercises"));
                        Values.setCompleted_courses(jsonGeneralData.getJSONArray("Completed_Courses"));

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("fxmldir/StartPage.fxml"));

                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        Image applicationIcon = new Image(getClass().getResourceAsStream("img/icon.png"));
                        stage.getIcons().add(applicationIcon);
                        stage.setScene(new Scene(root));
                        registertext.getScene().getWindow().hide();
                        stage.showAndWait();
                        break;

                    } else{
                        failed_text.setVisible(true);
                    }
                }
            } catch (JSONException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
            }
        });
    }
}
