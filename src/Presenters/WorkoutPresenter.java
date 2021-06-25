package Presenters;

import Controller.WorkoutSystem;
import Entities.WorkoutEntity;
import GUI.ClearScreen;

import java.util.List;
import java.util.Map;

public class WorkoutPresenter {
    private final WorkoutSystem workoutSystem;
    private final SchedulePresenter schedulePresenter;
    private final ClearScreen cl = new ClearScreen();
    public WorkoutPresenter(WorkoutSystem workoutSystem){
        this.workoutSystem = workoutSystem;
        this.schedulePresenter = new SchedulePresenter(workoutSystem);
    }

    public void welcome(){
        boolean exit = false;
        while (!exit) {
            cl.clear();
            System.out.println("Welcome to the workout program!\n" +
                    "What would you like to do? (Enter a number)");
            System.out.println("1 - View All My Workouts.\n" +
                    "2 - View my schedules/Begin workout.\n" +
                    "0 - Exit.");
            String input = workoutSystem.textInput();
            switch (input) {
                case "1":
                    viewWorkouts();
                    break;
                case "0":
                    exit = true;
                    break;
                case "2":
                    schedulePresenter.startWorkout();
                    break;
            }
        }
    }

    public void viewWorkouts(){
        boolean exit = false;
        int sortBy = 0;
        while (!exit) {
            cl.clear();
            if (sortBy <= 0){
                sortBy = 0;
                System.out.println("Here are your workouts (by name):\n");
                for (String names : workoutSystem.getSortByName().keySet()) {
                    System.out.println(mapToString(names, workoutSystem.getSortByName()));
                }
                if (workoutSystem.getNumWorkouts() == 0){
                    System.out.println("You have no workouts.\n");
                }
            }
            else if (sortBy == 1){
                System.out.println("Here are your workouts (by group):\n");
                for (String group : workoutSystem.getSortByGroup().keySet()) {
                    System.out.println(mapToString(group, workoutSystem.getSortByGroup()));
                }
                if (workoutSystem.getNumWorkouts() == 0){
                    System.out.println("You have no workouts.\n");
                }
            }
            else if (sortBy == 2){
                System.out.println("Here are your workouts (by difficulty):\n");
                for (int difficulty : workoutSystem.getSortByDifficulty().keySet()) {
                    System.out.println(mapToString(difficulty, workoutSystem.getSortByDifficulty()));
                }
                if (workoutSystem.getNumWorkouts() == 0){
                    System.out.println("You have no workouts.\n");
                }
            }
            else {
                sortBy = 3;
                System.out.println("Here are your workouts (by length):\n");
                for (int length : workoutSystem.getSortByLength().keySet()) {
                    System.out.println(mapToString(length, workoutSystem.getSortByLength()));
                }
                if (workoutSystem.getNumWorkouts() == 0){
                    System.out.println("You have no workouts.\n");
                }
            }
            System.out.println("Enter 0 to go back. \n" +
                    "Press 1 or 2 to change sorting mode. \n" +
                    "Enter 3 to create a new workout. \n" +
                    "Enter 4 to delete an existing workout. \n" +
                    "Enter 5 to change the description of a workout.");
            String input = workoutSystem.textInput();
            switch (input) {
                case "1":
                    sortBy--;
                    break;
                case "2":
                    sortBy++;
                    break;
                case "3":
                    createWorkout();
                    break;
                case "4":
                    deleteWorkout();
                    break;
                case "5":
                    changeDescription();
                    break;
                case "0":
                    exit = true;
                    break;
            }
        }
    }

    public void changeDescription(){
        System.out.println("Enter the name of the workout you would like to modify:");
        String name = workoutSystem.textInput();
        System.out.println("Enter the new description (leave blank to make no change):");
        String desc = workoutSystem.textInput();
        if (workoutSystem.changeDescription(name, desc) == 0){
            System.out.println("Description changed successfully!\n");
        }
        else{
            System.out.println("This workout does not exist.\n");
        }
        System.out.println("\nPress \"Enter\" to continue.");
        workoutSystem.textInput();
    }

    public void deleteWorkout(){
        System.out.println("Enter the name of the workout you would like to delete:");
        String input = workoutSystem.textInput();
        int err = workoutSystem.deleteWorkout(input);
        if (err != 0){
            System.out.println("This workout does not exist. No change has been made.\n");
        }
        else{
            System.out.println("Workout deleted!\n");
        }
        System.out.println("\nPress \"Enter\" to continue.");
        workoutSystem.textInput();
    }

    public void createWorkout(){
        System.out.println("Enter the name of the new workout:");
        String name = workoutSystem.textInput();
        System.out.println("Enter the length, per set, of the new workout (in seconds):\n" +
                "Enter 0 if this exercise is based on reps.");
        String length = workoutSystem.textInput();
        System.out.println("Enter the difficulty of the new workout (1 - 5):");
        String difficulty = workoutSystem.textInput();
        System.out.println("Enter the number of sets of the new workout (integer):");
        String set = workoutSystem.textInput();
        System.out.println("Enter the amount of rest between sets (in seconds):");
        String restPerSet = workoutSystem.textInput();
        System.out.println("Enter the group of the new workout:");
        String group = workoutSystem.textInput();
        System.out.println("Enter the description for your workout:");
        String description = workoutSystem.textInput();
        try {
            int err = workoutSystem.createWorkout(name, Integer.parseInt(length), Integer.parseInt(difficulty),
                    Integer.parseInt(set), Integer.parseInt(restPerSet), group, description);
            if (err == 1){
                System.out.println("Another workout with the same name already exists.\n");
            }
            else if (err == 2){
                System.out.println("Difficulty out of bounds. Must be 1-5 inclusive.\n");
            }
            else if (err == 3){
                System.out.println("Maximum number of workouts exceeded.\n");
            }
            else if (err == 4){
                System.out.println("Exceeded the maximum number of workouts for this group. Try a different group.\n");
            }
            else {
                System.out.println("Workout created successfully!\n");
            }
        }
        catch(Exception e){
            System.out.println("Invalid length and/or difficulty inputs.\n");
        }
        System.out.println("\nPress \"Enter\" to continue.");
        workoutSystem.textInput();
    }

    private String mapToString(String key, Map<String, List<WorkoutEntity>> map){
        String outputString = "";
        for (WorkoutEntity workout: map.get(key)){
            String singleWorkout = workout.getName() + ", length: " + workout.getLength() + ", difficulty: " +
                    workout.getDifficulty() + ", set: " + workout.getSet() + ", rest: " + workout.getRestPerSet()
                    + ", group: " + workout.getGroup() + "\nDescription:\n" +
                    workout.getDescription() + "\n------------------------------------------------------------------\n";
            outputString = outputString.concat(singleWorkout);
        }
        return outputString;
    }

    private String mapToString(int key, Map<Integer, List<WorkoutEntity>> map){
        String outputString = "";
        for (WorkoutEntity workout: map.get(key)){
            String singleWorkout = workout.getName() + ", length: " + workout.getLength() + ", difficulty: " +
                    workout.getDifficulty() + ", set: " + workout.getSet() + ", rest: " + workout.getRestPerSet()
                    + ", group: "
                    + workout.getGroup() + "\nDescription:\n" +
                    workout.getDescription() + "\n------------------------------------------------------------------\n";
            outputString = outputString.concat(singleWorkout);
        }
        return outputString;
    }
}
