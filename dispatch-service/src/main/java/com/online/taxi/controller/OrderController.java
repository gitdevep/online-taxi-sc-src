package com.online.taxi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.online.taxi.db.RedisDb;
import com.online.taxi.dto.ResponseResult;
import com.online.taxi.request.DispatchOrderRequest;
import com.online.taxi.request.DispatchRequest;
import com.online.taxi.schedule.TaskManager;
import com.online.taxi.service.DispatchService;

/**
 */
@RestController
@Slf4j
@RequestMapping("/dispatch")
public class OrderController {
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private RedisDb redisDb;

    @RequestMapping("/")
    public String home() {
        return "dispatch";
    }

    /**
     * 派单
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping(value = "/dispatchOrder", produces = "application/json; charset=utf-8")
    public ResponseResult dispatchOrder(@RequestBody DispatchOrderRequest request) throws InterruptedException {
        int orderId = request.getOrderId();
        taskManager.dispatch(orderId);
        return ResponseResult.success("success");
    }

    @PostMapping("/vehicleDispatch")
    public ResponseResult dispatch(@RequestBody DispatchRequest dispatchRequest) {
        return DispatchService.ins().dispatch(dispatchRequest);
    }

}
