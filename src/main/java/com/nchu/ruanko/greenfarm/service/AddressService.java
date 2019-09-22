package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import com.nchu.ruanko.greenfarm.pojo.entity.Province;
import java.util.List;

public interface AddressService {

    List<Province> listAllProvinces();

    List<Address> listAddressesByUserUID(String userUid);

    Address getAddressByAddressUID(String addressUid);

    void addAddress(Address address, String userUid);

    void modifyAddressByAddressUID(Address address, String addressUid);

    void removeAddressByAddressUID(String addressUid);

}