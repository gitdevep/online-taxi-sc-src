package com.online.taxi.mapper;

import com.online.taxi.dto.push.PushLoopMessageDto;
import com.online.taxi.entity.PushLoopMessage;

import java.util.List;

/**
 */
public interface PushLoopMessageMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(PushLoopMessage record);

    int insertBatch(List<PushLoopMessage> list);

    PushLoopMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PushLoopMessage record);

    List<PushLoopMessageDto> selectUnreadMessageListByIdentityAndAcceptId(PushLoopMessage pushLoopMessage);

    int updateReadById(List<Integer> ids);

}
