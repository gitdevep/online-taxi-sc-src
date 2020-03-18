package com.online.taxi.dao;

import com.online.taxi.entity.PassengerWalletFreezeRecord;
import com.online.taxi.mapper.PassengerWalletFreezeRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2018/8/22
 */
@Repository
public class PassengerWalletFreezeRecordDao {

    @Autowired
    private PassengerWalletFreezeRecordMapper passengerWalletFreezeRecordMapper;

    public int insertSelective(PassengerWalletFreezeRecord record){
        return passengerWalletFreezeRecordMapper.insertSelective(record);
    }

    public PassengerWalletFreezeRecord selectByPrimaryKey(Integer id){
        return passengerWalletFreezeRecordMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(PassengerWalletFreezeRecord record){
        return passengerWalletFreezeRecordMapper.updateByPrimaryKeySelective(record);
    }

    public PassengerWalletFreezeRecord selectByOrderIdAndYid(Integer orderId,Integer passengerInfoId){
        Map<String,Object> param = new HashMap<String,Object>(2);
        param.put("orderId",orderId);
        param.put("passengerInfoId",passengerInfoId);
        return passengerWalletFreezeRecordMapper.selectByOrderIdAndYid(param);
    }
}
