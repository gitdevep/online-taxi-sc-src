package com.online.taxi.dao;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.online.taxi.entity.PassengerWallet;
import com.online.taxi.mapper.PassengerWalletMapper;

/**
 * @date 2018/8/20
 */
@Repository
@RequiredArgsConstructor
public class PassengerWalletDao {

    @NonNull
    private PassengerWalletMapper passengerWalletMapper;

    public int insertSelective(PassengerWallet record){
        return passengerWalletMapper.insertSelective(record);
    }

}
