package sample.data;

import org.json.JSONArray;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Values {
    private static final Logger LOGGER = Logger.getLogger( Values.class.getName() );

    public static void ValuesLoggerSetup() throws IOException {
        LOGGER.addHandler(new ConsoleHandler());
        LOGGER.addHandler(new FileHandler());
    }

    public static String getUsername() {
        LOGGER.log(Level.FINE, "Got username");
        return username;
    }

    public static void setUsername(String username) {
        LOGGER.log(Level.FINE, "Set username");
        Values.username = username;
    }

    public static int getCurrent_course() {
        LOGGER.log(Level.FINE, "Got current course");
        return current_course;
    }

    public static void setCurrent_course(int current_course) {
        LOGGER.log(Level.FINE, "Set current course");
        Values.current_course = current_course;
    }

    public static int getCurrent_excercise() {
        LOGGER.log(Level.FINE, "Got current excercise ");
        return current_excercise;
    }

    public static void setCurrent_excercise(int current_excercise) {
        LOGGER.log(Level.FINE, "Set current excercise");
        Values.current_excercise = current_excercise;
    }

    public static JSONArray getCompleted_courses() {
        LOGGER.log(Level.FINE, "Got completed courses");
        return completed_courses;
    }

    public static void setCompleted_courses(JSONArray completed_courses) {
        LOGGER.log(Level.FINE, "Set completed couses");
        Values.completed_courses = completed_courses;
    }

    public static JSONArray getCompleted_excercises() {
        LOGGER.log(Level.FINE, "Got completed excercises");
        return completed_excercises;
    }

    public static void setCompleted_excercises(JSONArray completed_excercises) {
        LOGGER.log(Level.FINE, "Set completed exercises");
        Values.completed_excercises = completed_excercises;
    }

    public static int getLevel() {
        LOGGER.log(Level.FINE, "Got level");
        return level;
    }

    public static void setLevel(int level) {
        LOGGER.log(Level.FINE, "Set level");
        Values.level = level;
    }

    public static String getLevel_text() {
        LOGGER.log(Level.FINE, "Got level");
        return level_text;
    }

    public static void setLevel_text(String level_text) {
        LOGGER.log(Level.FINE, "Set level");
        Values.level_text = level_text;
    }

    private static String username;
    private static int current_course;
    private static int current_excercise;
    private static JSONArray completed_courses;
    private static JSONArray completed_excercises;
    private static int level;
    private static String level_text;
}
