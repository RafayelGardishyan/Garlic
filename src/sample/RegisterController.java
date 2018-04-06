package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sample.data.Const;
import sample.data.Values;
import sample.web_requesters.JsonReader;
import sample.web_requesters.PostSender;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginbutton;

    @FXML
    private TextField username_f;

    @FXML
    private TextField email_f;

    @FXML
    private PasswordField pw_f;


    @FXML
    private ChoiceBox<String> level_selector;

    @FXML
    void initialize() throws JSONException, IOException {
        JSONArray levels = JsonReader.GetJsonArray(Const.server_base_url + "level?format=json");
        for (int i = 0; i<levels.length(); i++){
            JSONObject level = levels.getJSONObject(i);
            level_selector.getItems().add(level.get("name").toString());
        }
        level_selector.getItems().add("Level");
        level_selector.getSelectionModel().select("Level");
        loginbutton.setOnAction((ActionEvent event) -> {
            int levelId = 0;
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
                    String current = level.get("name").toString();
                    if (level_selector.getValue().equals(current)){
                        levelId = level.getInt("id");
                        levelText = level.get("name").toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    exit();
                }
            }
            PostSender.add_param("Username", username_f.getText());
            PostSender.add_param("Password", pw_f.getText());
            PostSender.add_param("Email", email_f.getText());
            PostSender.add_param("Level", Integer.toString(levelId));
            PostSender.add_param("Completed_Excercises", Integer.toString(1));
            PostSender.add_param("Completed_Courses", Integer.toString(1));
            PostSender.add_param("Current_Course", Integer.toString(1));
            try {
                PostSender.sendPost(Const.server_base_url + "user/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            PostSender.remove_all_params();

            Values.setUsername(username_f.getText());
            Values.setCurrent_course(1);
            Values.setLevel(levelId);
            Values.setCompleted_excercises(new JSONArray().put(1));
            Values.setCompleted_courses(new JSONArray().put(1));
            Values.setLevel_text(levelText);


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
            username_f.getScene().getWindow().hide();
            stage.setResizable(false);
            stage.showAndWait();
        });
    }
}