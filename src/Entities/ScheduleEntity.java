package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleEntity implements Serializable {
    private final String name;
    private final List<WorkoutEntity> schedule;

    public ScheduleEntity(String name){
        this.name = name;
        this.schedule = new ArrayList<>();
    }

    public void addWorkout(WorkoutEntity workout, int index){
        schedule.add(index, workout);
    }

    public void removeWorkout(int index){
        schedule.remove(index);
    }

    public String getName(){
        return name;
    }

    public List<WorkoutEntity> getSchedule(){
        return schedule;
    }

}
