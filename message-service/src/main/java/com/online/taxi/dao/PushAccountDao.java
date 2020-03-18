package com.online.taxi.dao;

import com.online.taxi.entity.PushAccount;
import com.online.taxi.mapper.PushAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Repository
public class PushAccountDao {

    @Autowired
    private PushAccountMapper pushAccountMapper;

    public List<PushAccount> selectByIdentityAndYid(Integer identityStatus , String yid){
        PushAccount pushAccount = new PushAccount();
        pushAccount.setIdentityStatus(identityStatus);
        pushAccount.setYid(yid);
        return pushAccountMapper.selectByIdentityAndYid(pushAccount);
    }
}
