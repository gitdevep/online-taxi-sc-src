package com.online.taxi.task;

import lombok.Data;

import java.util.List;

/**
 */
@Data
public class TaskCondition {
    private int freeTimeBefor;
    private int freeTimeAfter;
    private int distance;
    private int nextTime;
    private int driverNum;
    private List<Integer> distanceList;
    private int compareType;

    public TaskCondition(int freeTimeBefor, int freeTimeAfter, int distance, int nextTime, int driverNum, List<Integer> distanceList, int compareType) {
        this.freeTimeBefor = freeTimeBefor;
        this.freeTimeAfter = freeTimeAfter;
        this.distance = distance;
        this.nextTime = nextTime;
        this.driverNum = driverNum;
        this.distanceList = distanceList;
        this.compareType = compareType;
    }
}
