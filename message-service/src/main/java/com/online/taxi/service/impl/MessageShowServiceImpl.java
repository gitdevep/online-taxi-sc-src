package com.online.taxi.service.impl;

import com.online.taxi.dao.MessageShowDao;
import com.online.taxi.entity.MessageShow;
import com.online.taxi.service.MessageShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @date 2018/9/8
 */
@Service
public class MessageShowServiceImpl implements MessageShowService {

    @Autowired
    private MessageShowDao messageShowDao;

    @Override
    public int saveMessageShow(String title, String content, String yid, int acceptIdentity, int pushType) {
        Date nowTime = new Date();
        MessageShow messageShow = new MessageShow();
        messageShow.setTitle(title);
        messageShow.setContent(content);
        messageShow.setYid(yid);
        messageShow.setAcceptIdentity(acceptIdentity);
        messageShow.setSendTime(nowTime);
        messageShow.setPushType(2);
        messageShow.setStatus(1);
        messageShow.setCreateTime(nowTime);
        messageShow.setPushType(pushType);

        messageShowDao.insertSelective(messageShow);
        return 0;
    }
}
