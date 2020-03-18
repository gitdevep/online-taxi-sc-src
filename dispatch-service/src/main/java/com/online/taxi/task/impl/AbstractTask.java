package com.online.taxi.task.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import com.online.taxi.consts.Const;
import com.online.taxi.consts.OrderTypeEnum;
import com.online.taxi.entity.Order;
import com.online.taxi.entity.OrderRulePrice;
import com.online.taxi.entity.TagInfo;
import com.online.taxi.service.DispatchService;
import com.online.taxi.task.ITask;
import com.online.taxi.task.TaskCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @date 2018/9/27
 */
@Data
@Slf4j
public abstract class AbstractTask implements ITask {
    protected int orderId;
    protected long nextExecuteTime;
    protected int type;
    protected int round;
    protected static final int PIRED = 20;
    protected List<TaskCondition> taskConditions = new ArrayList<>();
    protected int status;
    protected List<Integer> usedDriverId = new ArrayList<>();
    protected static final int STATUS_END = -1;

    @Override
    public int getTaskId() {
        return orderId;
    }

    @Override
    public boolean isTime() {
        return System.currentTimeMillis() > nextExecuteTime;
    }

    @Override
    public int getOrderType() {
        return type;
    }

    @Override
    public void setTaskConditions(List<TaskCondition> taskConditions) {
        this.taskConditions = taskConditions;
    }

    @Override
    public int execute(long current) {
        if (current < nextExecuteTime) {
            return status;
        }
        Order order = DispatchService.ins().getOrderById(orderId);
        OrderRulePrice orderRulePrice = DispatchService.ins().getOrderRulePrice(orderId);
        if (order == null || orderRulePrice == null) {
            status = STATUS_END;
            return status;
        }
        //判断是否已有司机接单
        if (order.getStatus() != Const.ORDER_STATUS_ORDER_START) {
            status = STATUS_END;
            return status;
        }
        if (round > taskConditions.size() - 1) {
            status = STATUS_END;
            //推送乘客，没人接单，或者加成功
            log.info("#orderId= " + orderId + "  round = " + round + "派单结束");
            taskEnd(order, orderRulePrice);
            return status;
        }
        int currentRound = round;
        round++;
        TaskCondition taskCondition = taskConditions.get(currentRound);
        log.info("#orderId = " + order.getId() + "  round = " + currentRound + "  派单 round = " + currentRound);

        boolean b = true;
        b = sendOrder(order, orderRulePrice, taskCondition, currentRound);
        nextExecuteTime = current + TimeUnit.SECONDS.toMillis(taskCondition.getNextTime());
        if (!b) {
            nextExecuteTime = 0;
            log.info("#orderId= " + orderId + "  直接下一轮");
            return execute(current);
        }
        return status;
    }

    protected String getTypeDesc2(Order order) {
        int serviceType = order.getServiceType();
        String s = "";
        if (serviceType == OrderTypeEnum.REAL_TIME.getCode()) {
        } else if (serviceType == OrderTypeEnum.NORMAL.getCode()) {
        } else if (serviceType == OrderTypeEnum.AIRPORT_PICKUP.getCode()) {
            s = "接机";
        } else if (serviceType == OrderTypeEnum.AIRPORT_DROPOFF.getCode()) {
            s = "送机";
        } else if (serviceType == OrderTypeEnum.CHARTERED_CAR_HALF.getCode()) {
            s = "包车";
        } else if (serviceType == OrderTypeEnum.CHARTERED_CAR_FULL.getCode()) {
            s = "包车";
        }
        return s;
    }

    protected String getTypeDesc(int serviceType, int isFollowing) {
        String s = "";

        if (serviceType == OrderTypeEnum.NORMAL.getCode()) {
            s = "预约派单";
        } else if (serviceType == OrderTypeEnum.FORCE.getCode()) {
            s += "实时派单";
            if (isFollowing == 1) {
                s = "顺风单";
            }
        } else if (serviceType == OrderTypeEnum.AIRPORT_DROPOFF.getCode()) {
            s += "送机派单";
        } else if (serviceType == OrderTypeEnum.AIRPORT_PICKUP.getCode()) {
            s += "接机派单";
        } else if (serviceType == OrderTypeEnum.CHARTERED_CAR_FULL.getCode() || serviceType == OrderTypeEnum.CHARTERED_CAR_HALF.getCode()) {
            s += "包车派单";
        }
        return s;
    }

    public JSONArray getTagsJson(String useFeature) {
        // "tagList": [ //订单标签
        // {
        //     "tagImg": "http://yesincar-test-source.oss-cn-hangzhou.aliyuncs.com/test",
        //         "tagId": "1"
        // },
        // {
        //     "tagImg": "http://yesincar-test-source.oss-cn-hangzhou.aliyuncs.com/image",
        //         "tagId": "2"
        // },
        //     ],
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isEmpty(useFeature)) {
            return jsonArray;
        }
        String[] ss = useFeature.split(",");
        for (String s : ss) {
            int id = Integer.parseInt(s);
            TagInfo tagInfo = DispatchService.ins().getTagInfo(id);
            if (tagInfo == null) {
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagImg", DispatchService.ins().getOssFileUrl() + tagInfo.getTagImg());
            jsonObject.put("tagId", tagInfo.getId());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
