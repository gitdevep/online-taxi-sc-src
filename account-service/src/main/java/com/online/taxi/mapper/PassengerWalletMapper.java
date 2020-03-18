package com.online.taxi.mapper;

import org.springframework.stereotype.Service;

import com.online.taxi.entity.PassengerWallet;

/**
 * @date 2018/09/05
 */
@Service
public interface PassengerWalletMapper {

    int insertSelective(PassengerWallet record);

}
