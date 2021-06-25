package Saving;

import Controller.WorkoutSystem;
import Entities.ScheduleEntity;
import Entities.WorkoutEntity;
import Presenters.SchedulePresenter;
import Presenters.WorkoutPresenter;

import java.io.*;
import java.util.List;
import java.util.Map;

public class LoadAndSave {
    public int save(WorkoutSystem workoutSystem) {
        try {
            FileOutputStream fileStream = new FileOutputStream("Files/saveFile.txt");

            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            Map<String, List<WorkoutEntity>> namesMap = workoutSystem.getSortByName();
            Map<String, ScheduleEntity> schedulesMap = workoutSystem.getSchedules();

            for (String name : namesMap.keySet()) {
                objectStream.writeObject(namesMap.get(name).get(0));
            }

            for (String name : schedulesMap.keySet()) {
                objectStream.writeObject(schedulesMap.get(name));
            }

            objectStream.close();

            fileStream.close();

            System.out.println("Saved!\n");
            return 0;
        } catch (FileNotFoundException e) {
            System.out.println("Saves files not found.\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception while saving.\n");
        }
        return 1;
    }

    public int load(WorkoutSystem workoutSystem) {
        try {
            FileInputStream fileStream = new FileInputStream("Files/saveFile.txt");
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            try {
                System.out.println("Loading...");
                while (true) {
                    Object obj = objectStream.readObject();

                    if (obj instanceof WorkoutEntity) {
                        WorkoutEntity workout = (WorkoutEntity) obj;
                        workoutSystem.createWorkout(workout.getName(), workout.getLength(), workout.getDifficulty(),
                                workout.getSet(), workout.getRestPerSet(), workout.getGroup(), workout.getDescription());
                    } else if (obj instanceof ScheduleEntity) {
                        ScheduleEntity schedule = (ScheduleEntity) obj;
                        workoutSystem.createSchedule(schedule.getName());
                        for (int i = 0; i < schedule.getSchedule().size(); i++) {
                            System.out.println(i);
                            workoutSystem.addWorkoutToSchedule(schedule.getName(),
                                    schedule.getSchedule().get(i).getName(), i + 1);
                        }
                    } else {
                        break;
                    }
                }
                System.out.println("This message should not appear. If you see this, you've encountered a bug.");
                return 1;
            } catch (IOException e) {
                System.out.println("Loaded successfully!");
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found while loading.\n");
                return 1;
            }
            fileStream.close();
            objectStream.close();
            return 0;
        }
        catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
        catch (IOException e ){
            System.out.println("IOException while loading.");
            e.printStackTrace();
        }
        return 1;
    }
}
