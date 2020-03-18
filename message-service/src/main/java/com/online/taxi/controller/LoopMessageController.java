package com.online.taxi.controller;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.push.PushLoopBatchRequest;
import com.online.taxi.dto.push.PushLoopMessageDto;
import com.online.taxi.entity.PushLoopMessage;
import com.online.taxi.service.PushLoopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@RestController
@RequestMapping("/loop")
public class LoopMessageController {

    @Autowired
    private PushLoopService pushLoopService;

    @RequestMapping(value = "/message",method = RequestMethod.POST)
    public ResponseResult message(@RequestBody PushLoopMessage pushLoopMessage){

        int result = pushLoopService.insert(pushLoopMessage);
        return ResponseResult.success("");
    }

    @RequestMapping(value = "/message/{acceptIdentity}/{acceptId}",method = RequestMethod.GET)
    public ResponseResult getMessage(@PathVariable("acceptIdentity") Integer acceptIdentity, @PathVariable("acceptId") String acceptId){

        List<PushLoopMessageDto> list = pushLoopService.selectUnreadMessageListByIdentityAndAcceptId(acceptIdentity,acceptId);
        return ResponseResult.success(list);
    }

    @RequestMapping(value = "/batch/message",method = RequestMethod.POST)
    public ResponseResult batcgMessage(@RequestBody PushLoopBatchRequest pushLoopBatchRequest){

        int result = pushLoopService.insertBatch(pushLoopBatchRequest);
        return ResponseResult.success("");
    }
}
