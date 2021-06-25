package UseCases;
import Entities.WorkoutEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutManager {
    private Map<String, List<WorkoutEntity>> sortByName;
    private Map<Integer, List<WorkoutEntity>> sortByLength;
    private Map<Integer, List<WorkoutEntity>> sortByDifficulty;
    private Map<String, List<WorkoutEntity>> sortByGroup;

    public WorkoutManager(){
        sortByDifficulty = new HashMap<>();
        sortByGroup = new HashMap<>();
        sortByLength = new HashMap<>();
        sortByName = new HashMap<>();
    }

    public void createWorkout(String name, int length, int difficulty, int set, int restPerSet, String group){
        WorkoutEntity new_workout = new WorkoutEntity(name, length, difficulty, set, restPerSet, group);
        if (!sortByName.containsKey(name)) {
            sortByName.put(name, new ArrayList<>());
        }
        sortByName.get(name).add(new_workout);

        if (!sortByDifficulty.containsKey(difficulty)) {
            sortByDifficulty.put(difficulty, new ArrayList<>());
        }
        sortByDifficulty.get(difficulty).add(new_workout);

        if (!sortByLength.containsKey(length)){
            sortByLength.put(length, new ArrayList<>());
        }
        sortByLength.get(length).add(new_workout);

        if (!sortByGroup.containsKey(group)){
            sortByGroup.put(group, new ArrayList<>());
        }
        sortByGroup.get(group).add(new_workout);
    }

    public int changeDescription(String name, String message){
        if (!sortByName.containsKey(name)){
            return 1;
        }
        WorkoutEntity workoutEntity = sortByName.get(name).get(0);
        if (message.length() > 0) {
            workoutEntity.setDescription(message);
        }
        return 0;
    }

    public int getNumWorkouts(){
        return sortByName.size();
    }

    public int deleteWorkout(String name){
        WorkoutEntity workout = sortByName.get(name).get(0);
        if (workout == null){
            return 1;
        }
        sortByName.remove(name);
        sortByLength.remove(workout.getLength(), workout);
        sortByGroup.remove(workout.getGroup(), workout);
        sortByDifficulty.remove(workout.getDifficulty(), workout);
        return 0;
    }

    public Map<String, List<WorkoutEntity>> getSortByName(){
        return sortByName;
    }

    public Map<String, List<WorkoutEntity>> getSortByGroup(){
        return sortByGroup;
    }

    public Map<Integer, List<WorkoutEntity>> getSortByLength(){
        return sortByLength;
    }

    public Map<Integer, List<WorkoutEntity>> getSortByDifficulty(){
        return sortByDifficulty;
    }

}
