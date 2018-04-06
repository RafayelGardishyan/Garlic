package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sample.data.Const;
import sample.data.Values;
import sample.web_requesters.JsonReader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcome_message;

    @FXML
    private Label score_message;

    @FXML
    private Label level_message;

    @FXML
    private Label no_course;

    @FXML
    private VBox course_pane;

    @FXML
    void initialize() throws JSONException, IOException {
        Label  course;
        JSONArray courses = JsonReader.GetJsonArray(Const.server_base_url + "course?format=json");
        for (int i = 0; i<courses.length(); i++) {
            JSONObject Ccourse = courses.getJSONObject(i);
            if ((Integer) Ccourse.get("Level") == Values.getLevel()){
                if (Values.getCurrent_course() == (Integer) Ccourse.get("id")) {
                    course = new Label();
                    course.setText((String) Ccourse.get("name"));
                    course.setStyle("-fx-font-size: 30px;");
                    course_pane.getChildren().add(course);
                } else{
                    course = new Label();
                    course.setText((String) Ccourse.get("name"));
                    course_pane.getChildren().add(course);
                }
            }
        }

        welcome_message.setText(String.format("Welcome, %s", Values.getUsername()));
        score_message.setText(String.format("Score: %d", Values.getCompleted_excercises().length()));
        level_message.setText(String.format("Level: %s", Values.getLevel_text()));
        no_course.setVisible(false);
    }
}
