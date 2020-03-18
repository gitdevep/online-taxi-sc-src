package com.online.taxi.dao;

import com.online.taxi.entity.MessageShow;
import com.online.taxi.mapper.MessageShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @date 2018/9/8
 */
@Repository
public class MessageShowDao {

    @Autowired
    private MessageShowMapper messageShowMapper;

    public int insertSelective(MessageShow messageShow) {
        return messageShowMapper.insertSelective(messageShow);
    }
}
