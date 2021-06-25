package Controller;

import Entities.WorkoutEntity;
import UseCases.ScheduleManager;
import Entities.ScheduleEntity;
import java.util.Map;

public class ScheduleSystem {
    private final ScheduleManager scheduleManager;

    public ScheduleSystem(){
        scheduleManager = new ScheduleManager();
    }

    public int createSchedule(String name){
        if (scheduleManager.checkForSchedule(name)){
            return 1;
        }
        else{
            scheduleManager.addSchedule(name);
            return 0;
        }
    }

    public int deleteSchedule(String name){
        if (!scheduleManager.checkForSchedule(name)){
            return 1;
        }
        else{
            scheduleManager.deleteSchedule(name);
            return 0;
        }
    }

    public Map<String, ScheduleEntity> getSchedules(){
        return scheduleManager.getSchedules();
    }

    public int addWorkout(String scheduleName, WorkoutEntity workout, int index){
        if (!scheduleManager.checkForSchedule(scheduleName)){
            return 1;
        }
        else{
            scheduleManager.addWorkout(scheduleName, workout, index - 1);
            return 0;
        }
    }

    public int removeWorkout(String scheduleName, int index){
        if (!scheduleManager.checkForSchedule(scheduleName)){
            return 1;
        }
        else if (scheduleManager.getSchedules().get(scheduleName).getSchedule().size() < index - 1){
            return 2;
        }
        else{
            scheduleManager.removeWorkout(scheduleName, index - 1);
            return 0;
        }
    }

    public boolean checkForSchedule(String name){
        return scheduleManager.checkForSchedule(name);
    }

}
