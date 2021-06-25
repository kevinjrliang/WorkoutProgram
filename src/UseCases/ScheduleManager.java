package UseCases;

import Entities.ScheduleEntity;
import Entities.WorkoutEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleManager {
    private final Map<String, ScheduleEntity> schedules;

    public ScheduleManager(){
        schedules = new HashMap<>();
    }

    public void addSchedule(String name){
        ScheduleEntity schedule = new ScheduleEntity(name);
        schedules.put(name, schedule);
    }

    public void deleteSchedule(String name){
        schedules.remove(name);
    }

    public void addWorkout(String scheduleName, WorkoutEntity workout, int index){
        schedules.get(scheduleName).addWorkout(workout, index);
    }

    public void removeWorkout(String scheduleName, int index){
        schedules.get(scheduleName).removeWorkout(index);
    }

    public Map<String, ScheduleEntity> getSchedules(){
        return schedules;
    }

    public boolean checkForSchedule(String name){
        return schedules.containsKey(name);
    }
}
