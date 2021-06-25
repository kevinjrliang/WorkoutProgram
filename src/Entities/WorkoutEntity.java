package Entities;

import java.io.Serializable;

public class WorkoutEntity implements Serializable {
    private final int length;
    private final int difficulty;
    private final String name;
    private final String group;
    private final int set;
    private String description;
    private final int restPerSet;

    public WorkoutEntity(String name, int length, int difficulty, int set, int restPerSet, String group){
        this.name = name;
        this.length = length;
        this.difficulty = difficulty;
        this.group = group;
        this.description = "This exercise has no description.";
        this.set = set;
        this.restPerSet = restPerSet;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription(String message){
        this.description = message;
    }

    public String getName(){
        return name;
    }
    public String getGroup(){
        return group;
    }
    public int getLength(){
        return length;
    }
    public int getDifficulty(){
        return difficulty;
    }
    public int getSet(){
        return set;
    }
    public int getRestPerSet(){
        return restPerSet;
    }
}
