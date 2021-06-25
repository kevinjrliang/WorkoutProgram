package GUI;

import Controller.WorkoutSystem;
import Presenters.WorkoutPresenter;
import Saving.LoadAndSave;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;

//public class Main extends Application {
    /*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        primaryStage.setTitle("Workout Program");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
     */
public class Main{

    public static void main(String[] args) {
        WorkoutSystem workoutSystem = new WorkoutSystem();
        WorkoutPresenter workoutPresenter = new WorkoutPresenter(workoutSystem);
        LoadAndSave loadAndSave = new LoadAndSave();
        int loadErr = loadAndSave.load(workoutSystem);
        try {
            Runtime.getRuntime().exec("cls");
        }
        catch(Exception e){
            System.out.println("An exception occurred while clearing the screen.");
        }
        workoutPresenter.welcome();
        if (loadErr == 0){
            loadAndSave.save(workoutSystem);
        }
    }
}