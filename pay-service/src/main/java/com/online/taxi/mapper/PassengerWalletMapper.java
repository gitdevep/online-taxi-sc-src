package com.online.taxi.mapper;

import com.online.taxi.entity.PassengerWallet;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 */
@Service
public interface PassengerWalletMapper {
    /**
     * 删除记录
     * @param id id
     * @return int
     */
    int deleteByPrimaryKey(Integer id);

    int insert(PassengerWallet record);

    int insertSelective(PassengerWallet record);

    PassengerWallet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PassengerWallet record);

    int updateByPrimaryKey(PassengerWallet record);

    PassengerWallet selectByPassengerInfoId(Integer passengerInfoId);

    int updateBalanceByPassengerInfoId(Map<String,Object> param);

    int unfreezeBalanceByPassengerInfoId(Map<String,Object> param);

}