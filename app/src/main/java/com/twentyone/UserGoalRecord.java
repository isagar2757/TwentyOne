package com.twentyone;

public class UserGoalRecord {


    int RecordNo, GoalId, GoalAction;

    public UserGoalRecord(int goalId, String name,int goalAction,  String goalActionDate) {
        GoalId = goalId;
        GoalAction = goalAction;
        Name = name;
        GoalActionDate = goalActionDate;
    }

    public int getRecordNo() {
        return RecordNo;
    }

    public void setRecordNo(int recordNo) {
        RecordNo = recordNo;
    }

    public int getGoalId() {
        return GoalId;
    }

    public void setGoalId(int goalId) {
        GoalId = goalId;
    }

    public int getGoalAction() {
        return GoalAction;
    }

    public void setGoalAction(int goalAction) {
        GoalAction = goalAction;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGoalActionDate() {
        return GoalActionDate;
    }

    public void setGoalActionDate(String goalActionDate) {
        GoalActionDate = goalActionDate;
    }

    String Name,GoalActionDate;

}

