package com.online.taxi.service.impl;

import com.online.taxi.dao.PushLoopMessageDao;
import com.online.taxi.dto.push.PushLoopBatchRequest;
import com.online.taxi.dto.push.PushLoopMessageDto;
import com.online.taxi.entity.PushLoopMessage;
import com.online.taxi.service.PushLoopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 */
@Service
public class PushLoopServiceImpl implements PushLoopService {

    @Autowired
    private PushLoopMessageDao pushLoopMessageDao;

    /**
     * 插入单条消息
     *
     * @param pushLoopMessage
     * @return
     */
    @Override
    public int insert(PushLoopMessage pushLoopMessage) {
        pushLoopMessage = setProperties(pushLoopMessage);
        return pushLoopMessageDao.insert(pushLoopMessage);
    }

    private PushLoopMessage setProperties(PushLoopMessage pushLoopMessage){
        Date nowTime = new Date();
        pushLoopMessage.setCreateTime(nowTime);
        Long expireTime = nowTime.getTime()+1000*60;
        pushLoopMessage.setExpireTime(new Date(expireTime));
        pushLoopMessage.setReadFlag(0);
        return  pushLoopMessage;
    }
    /**
     * 插入批量消息
     *
     * @return
     */
    @Override
    public int insertBatch(PushLoopBatchRequest pushLoopBatchRequest) {
        List<String> acceptIds = pushLoopBatchRequest.getAcceptIds();
        List<PushLoopMessage> item = new ArrayList<PushLoopMessage>();
        for (String acceptId : acceptIds) {
            PushLoopMessage pushLoopMessage = new PushLoopMessage();
            BeanUtils.copyProperties(pushLoopBatchRequest,pushLoopMessage);
            pushLoopMessage.setAcceptId(acceptId);
            pushLoopMessage = setProperties(pushLoopMessage);
            item.add(pushLoopMessage);
        }

        return pushLoopMessageDao.insertBatch(item);
    }

    @Override
    public List<PushLoopMessageDto> selectUnreadMessageListByIdentityAndAcceptId(Integer acceptIdentity, String acceptId){

        List<PushLoopMessageDto> list = pushLoopMessageDao.selectUnreadMessageListByIdentityAndAcceptId(acceptIdentity,acceptId);
        List<Integer> ids = new ArrayList<>();
        for (PushLoopMessageDto pushLoopMessageDao:
             list) {
            ids.add(pushLoopMessageDao.getId());
        }
        if(!ids.isEmpty()){
            pushLoopMessageDao.updateReadById(ids);
        }

        return list;
    }
}
