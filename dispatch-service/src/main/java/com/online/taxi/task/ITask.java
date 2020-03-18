package com.online.taxi.task;

import java.util.List;

import com.online.taxi.entity.Order;
import com.online.taxi.entity.OrderRulePrice;

/**
 */
public interface ITask {

    public int execute(long current);

    public int getTaskId();

    public boolean isTime();

    public int getOrderType();

    public boolean sendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round);

    public void taskEnd(Order order, OrderRulePrice orderRulePrice);

    public void setTaskConditions(List<TaskCondition> taskConditions);
}
