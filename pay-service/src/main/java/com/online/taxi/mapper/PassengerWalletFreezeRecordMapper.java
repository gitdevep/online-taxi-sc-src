package com.online.taxi.mapper;

import com.online.taxi.entity.PassengerWalletFreezeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 */
public interface PassengerWalletFreezeRecordMapper {
    /**
     * 删除
     * @param id id
     */
    int deleteByPrimaryKey(Integer id);

    int insert(PassengerWalletFreezeRecord record);

    int insertSelective(PassengerWalletFreezeRecord record);

    PassengerWalletFreezeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerWalletFreezeRecord record);

    int updateByPrimaryKey(PassengerWalletFreezeRecord record);

    PassengerWalletFreezeRecord selectByOrderIdAndYid(Map<String,Object> param);

    /**
     *查询钱包冻结
     * @param date
     * @return
     */
    List<PassengerWalletFreezeRecord> selectPurseThaw(Date date);

    /**
     * 钱包解冻
     */
    int updatePassengerWallet(Integer orderId);
}
