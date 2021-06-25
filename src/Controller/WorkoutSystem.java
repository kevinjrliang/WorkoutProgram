package Controller;

import Entities.ScheduleEntity;
import Entities.WorkoutEntity;
import UseCases.WorkoutManager;
import static Constants.Constants.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WorkoutSystem {
    WorkoutManager wm;
    ScheduleSystem scheduleSystem;

    public WorkoutSystem(){
        wm = new WorkoutManager();
        scheduleSystem = new ScheduleSystem();
    }

    public String textInput(){
        String str;
        Scanner scanner = new Scanner(System.in);
        str = scanner.nextLine();
        return str;
    }

    public int createWorkout(String name, int length, int difficulty, int set, int restPerSet, String group,
                             String description){
        if (wm.getSortByName().containsKey(name)){
            return 1;
        }
        else if (difficulty < 1 || difficulty > 5){
            return 2;
        }
        else if (wm.getNumWorkouts() > MAX_WORKOUTS){
            return 3;
        }
        else if (wm.getSortByGroup().size() > MAX_GROUPS){
            return 4;
        }
        else if (length < 0 || length > 120){
            return 5;
        }
        else{
            wm.createWorkout(name, length, difficulty, set, restPerSet, group);
            wm.changeDescription(name, description);
            return 0;
        }
    }

    public int changeDescription(String name, String message){
        return wm.changeDescription(name, message);
    }

    public int getNumWorkouts(){
        return wm.getNumWorkouts();
    }

    public int deleteWorkout(String name){
        return wm.deleteWorkout(name);
    }

    public Map<String, List<WorkoutEntity>> getSortByName(){
        return wm.getSortByName();
    }

    public Map<String, List<WorkoutEntity>> getSortByGroup(){
        return wm.getSortByGroup();
    }

    public Map<Integer, List<WorkoutEntity>> getSortByLength(){
        return wm.getSortByLength();
    }

    public Map<Integer, List<WorkoutEntity>> getSortByDifficulty(){
        return wm.getSortByDifficulty();
    }

    public int createSchedule(String name){
        return scheduleSystem.createSchedule(name);
    }

    public int deleteSchedule(String name){
        return scheduleSystem.deleteSchedule(name);
    }

    public int addWorkoutToSchedule(String scheduleName, String workoutName, int index){
        if (!wm.getSortByName().containsKey(workoutName)){
            return 2;
        }
        else{
            WorkoutEntity workout = wm.getSortByName().get(workoutName).get(0);
            return scheduleSystem.addWorkout(scheduleName, workout, index);
        }
    }

    public int deleteWorkoutFromSchedule(String scheduleName, int index){
        return scheduleSystem.removeWorkout(scheduleName, index);
    }

    public Map<String, ScheduleEntity> getSchedules(){
        return scheduleSystem.getSchedules();
    }

    public boolean checkForSchedule(String name){
        return scheduleSystem.checkForSchedule(name);
    }
}
