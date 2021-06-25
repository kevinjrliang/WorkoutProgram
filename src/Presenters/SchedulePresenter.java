package Presenters;

import Controller.WorkoutSystem;
import Entities.ScheduleEntity;
import Entities.WorkoutEntity;
import GUI.ClearScreen;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class SchedulePresenter {
    private final WorkoutSystem workoutSystem;
    private final ClearScreen cl = new ClearScreen();

    public SchedulePresenter(WorkoutSystem workoutSystem){
        this.workoutSystem = workoutSystem;
    }

    public void createScheduleScreen(){
        System.out.println("Enter the name of your new schedule:");
        String name = workoutSystem.textInput();
        int err = workoutSystem.createSchedule(name);
        if (err == 1){
            System.out.println("Schedule with this name already exists.\n");
        }
        else{
            System.out.println("Schedule created successfully!\n");
        }
        System.out.println("\nPress \"Enter\" to continue.");
        workoutSystem.textInput();
    }

    public void deleteScheduleScreen(){
        System.out.println("Enter the name of the schedule you want to delete:");
        String name = workoutSystem.textInput();
        int err = workoutSystem.deleteSchedule(name);
        if (err == 1){
            System.out.println("There is no schedule with this name.\n");
        }
        else{
            System.out.println("Schedule deleted!\n");
        }
        System.out.println("\nPress \"Enter\" to continue.");
        workoutSystem.textInput();
    }

    public void startWorkout(){
        boolean exit = false;
        while (!exit) {
            cl.clear();
            System.out.println("Here is a list of your schedules:");
            System.out.println(mapToString(workoutSystem.getSchedules()));

            System.out.println("Enter the name of a schedule to start the workout. \n" +
                    "Enter 1 to delete a workout from a schedule. \n" +
                    "Enter 2 to add a workout to a schedule. \n" +
                    "Enter 3 to delete a schedule. \n" +
                    "Enter 4 to create a schedule. \n" +
                    "Enter 0 to go back.\n");
            String input = workoutSystem.textInput();
            switch (input) {
                case "0":
                    exit = true;
                    break;
                case "1": {
                    deleteFromSchedule();
                    break;
                }
                case "2": {
                    addtoSchedule();
                    break;
                }
                case "3":
                    deleteScheduleScreen();
                    break;
                case "4":
                    createScheduleScreen();
                    break;
                default:
                    if (workoutSystem.checkForSchedule(input)) {
                        beginWorkout(input);
                    } else {
                        System.out.println("This schedule does not exist.\n");
                    }
                    break;
            }
        }

    }

    private void addtoSchedule(){
        System.out.println("Enter the name of the schedule you want to add to:");
        String name = workoutSystem.textInput();
        System.out.println("Enter the name of the workout you want to add:");
        String workout = workoutSystem.textInput();
        System.out.println("Enter the number where you want this workout to be inserted at:");
        String id = workoutSystem.textInput();
        try {
            int err = workoutSystem.addWorkoutToSchedule(name, workout, Integer.parseInt(id));
            if (err == 1) {
                System.out.println("This schedule does not exist.\n");
            } else if (err == 2) {
                System.out.println("This workout does not exist.\n");
            } else if (err == 0) {
                System.out.println("Workout added successfully!\n");
            } else {
                System.out.println("This message should not appear. If it has, a bug has been encountered.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number! Please enter an integer.\n");
        }
        System.out.println("\nPress \"Enter\" to continue.");
        workoutSystem.textInput();
    }

    private void deleteFromSchedule(){
        System.out.println("Enter the name of the schedule you want to delete from:");
        String name = workoutSystem.textInput();
        System.out.println("Enter the number of the workout you want to delete");
        String id = workoutSystem.textInput();
        try {
            int err = workoutSystem.deleteWorkoutFromSchedule(name, Integer.parseInt(id));
            if (err == 1) {
                System.out.println("This schedule does not exist.\n");
            } else if (err == 2) {
                System.out.println("This number has no associated workout.\n");
            } else if (err == 0) {
                System.out.println("Workout deleted successfully!\n");
            } else {
                System.out.println("This message should not appear. If it has, a bug has been encountered.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number! Please enter an integer.\n");
        }
        System.out.println("\nPress \"Enter\" to continue.");
        workoutSystem.textInput();
    }

    private void beginWorkout(String name){
        List<WorkoutEntity> workoutList = workoutSystem.getSchedules().get(name).getSchedule();
        cl.clear();
        System.out.println(name + " started!\n");
        final int[] input = {0, 0};
        for (WorkoutEntity workout: workoutList) {
            System.out.println("Beginning workout. Press \"Enter\" to start.\n");
            workoutSystem.textInput();
            int setsLeft = workout.getSet();
            while (setsLeft > 0) {
                cl.clear();
                System.out.println("Starting " + workout.getName() + ": " + setsLeft + " sets left." +
                        "Press \"Enter\" to finish a set (for rep exercises) or to skip set (for timed exercises).\n");
                int countIn = 5;
                if (setsLeft < workout.getLength()){
                    countIn = workout.getRestPerSet();
                }
                for (int i = countIn; i > 0; i--){
                    try {
                        System.out.println("Starting in " + i);
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (InterruptedException e){
                        System.out.println("Count in timer was interrupted!\n");
                    }
                }
                System.out.println("Begin!\n");
                if (workout.getLength() == 0){
                    workoutSystem.textInput();
                }
                else{
                    input[0] = 0;
                    ForkJoinPool forkJoinPool = new ForkJoinPool(2);
                    RecursiveAction inputTask = new RecursiveAction() {
                        @Override
                        protected void compute() {
                            workoutSystem.textInput();
                            input[0] = 1;
                            input[1] = 0;
                        }
                    };

                    RecursiveAction timerTask = new RecursiveAction() {
                        @Override
                        protected void compute() {
                            try {
                                for (int i = workout.getLength(); i >= 0; i--) {
                                    if (((i) % 10 == 0 || (i <= 5)) && i != 0) {
                                        System.out.println(i + " seconds left!");
                                        if (input[0] != 0){
                                            return;
                                        }
                                        TimeUnit.SECONDS.sleep(1);
                                    }
                                }
                                input[0] = 1;
                            } catch (InterruptedException e) {
                                System.out.print("");
                            }
                        }
                    };
                    if (input[1] == 0){
                        input[1] = 1;
                        forkJoinPool.execute(inputTask);
                    }
                    forkJoinPool.execute(timerTask);
                    do {
                        System.out.print("");
                    } while (input[0] == 0);

                    forkJoinPool.shutdownNow();
                }
                System.out.println("Set finished!\n");
                setsLeft--;
            }
            System.out.println("Workout Complete! Press \"Enter\" to continue.\n");
            if (input[1] == 1){
                do {
                    System.out.print("");
                } while (input[1] == 1);
            }
            else{
                workoutSystem.textInput();
            }
        }
    }

    private String mapToString(Map<String, ScheduleEntity> map){
        String outputString = "";
        for (String schedule: map.keySet()){
            ScheduleEntity scheduleEntity = map.get(schedule);
            String singleSchedule = "Name: " + scheduleEntity.getName() + "\nList of Workouts:\n";
            for (int i = 0; i < scheduleEntity.getSchedule().size(); i++){
                WorkoutEntity workout = scheduleEntity.getSchedule().get(i);
                singleSchedule = singleSchedule.concat(i+1 + ": " + workout.getName() + "\n");
            }
            outputString = outputString.concat(singleSchedule + "\n------------------------------------------------\n");
        }
        return outputString;
    }


}
