package sample;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class CourseTemplateController extends HBox {
    @FXML
    private Label courseName;

    @FXML
    private Label courseTheme;

    @FXML
    private Label courseLevel;

    @FXML
    private Label courseExcerciseCount;

    @FXML
    private Button open_button;

    public CourseTemplateController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "fxmldir/CourseTemplate.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setCourseName(String value) {
        nameProperty().set(value);
    }

    public StringProperty nameProperty() {
        return courseName.textProperty();
    }

    public void setCourseTheme(String value) {
        themeProperty().set(value);
    }

    public StringProperty themeProperty() {
        return courseTheme.textProperty();
    }

    public void setCourseLevel(String value) {
        courseLevelProperty().set(value);
    }

    public StringProperty courseLevelProperty() {
        return courseLevel.textProperty();
    }

    public void setCourseExcerciseCount(String value) {
        courseExcerciseCountProperty().set(value);
    }

    public StringProperty courseExcerciseCountProperty() {
        return courseExcerciseCount.textProperty();
    }

    public Button getButtonObject(){
        return open_button;
    }
}
