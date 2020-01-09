package com.twentyone;

public class UserGoalList {
    int GoalId,IsGoalFinished,TotalDaysOfGoal;


    String Name;
    String GoalCreatedDate;

    public String getGoalEndDate() {
        return GoalEndDate;
    }

    public void setGoalEndDate(String goalEndDate) {
        GoalEndDate = goalEndDate;
    }

    String GoalEndDate;
    String Goal;

    public int getGoalCompletionDays() {
        return GoalCompletionDays;
    }

    public void setGoalCompletionDays(int goalCompletionDays) {
        GoalCompletionDays = goalCompletionDays;
    }

    int GoalCompletionDays;

    public UserGoalList() {
    }

    public int getTotalDaysOfGoal() {

        return TotalDaysOfGoal;
    }

    public void setTotalDaysOfGoal(int totalDaysOfGoal) {
        TotalDaysOfGoal = totalDaysOfGoal;
    }

    public UserGoalList(int goalId, String name, String goal) {
        GoalId = goalId;
        Name = name;
        Goal = goal;
    }



    public int getIsGoalFinished() {
        return IsGoalFinished;
    }

    public void setIsGoalFinished(int isGoalFinished) {
        IsGoalFinished = isGoalFinished;
    }

    public String getGoalCreatedDate() {
        return GoalCreatedDate;
    }

    public void setGoalCreatedDate(String goalCreatedDate) {
        GoalCreatedDate = goalCreatedDate;
    }

    public int getGoalId() {
        return GoalId;
    }

    public void setGoalId(int goalId) {
        GoalId = goalId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGoal() {
        return Goal;
    }

    public void setGoal(String goal) {
        Goal = goal;
    }


}
