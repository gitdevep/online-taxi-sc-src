package com.online.taxi.dao;

import com.online.taxi.dto.push.PushLoopMessageDto;
import com.online.taxi.entity.PushLoopMessage;
import com.online.taxi.mapper.PushLoopMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Repository
public class PushLoopMessageDao {

    @Autowired
    private PushLoopMessageMapper pushLoopMessageMapper;

    public int insert(PushLoopMessage pushLoopMessage){
       return pushLoopMessageMapper.insertSelective(pushLoopMessage);
    }

    public int insertBatch(List<PushLoopMessage> pushLoopMessageList){
        return pushLoopMessageMapper.insertBatch(pushLoopMessageList);
    }

    public List<PushLoopMessageDto> selectUnreadMessageListByIdentityAndAcceptId(Integer acceptIdentity, String acceptId){
        PushLoopMessage pushLoopMessage = new PushLoopMessage();
        pushLoopMessage.setAcceptId(acceptId);
        pushLoopMessage.setAcceptIdentity(acceptIdentity);
        return pushLoopMessageMapper.selectUnreadMessageListByIdentityAndAcceptId(pushLoopMessage);
    }

    public int updateReadById(List<Integer> ids){
        return pushLoopMessageMapper.updateReadById(ids);
    }

}
