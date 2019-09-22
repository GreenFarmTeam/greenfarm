package com.nchu.ruanko.greenfarm.service.impl;

import com.nchu.ruanko.greenfarm.dao.AddressDAO;
import com.nchu.ruanko.greenfarm.dao.ProvinceDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import com.nchu.ruanko.greenfarm.pojo.entity.Province;
import com.nchu.ruanko.greenfarm.service.AddressService;
import com.nchu.ruanko.greenfarm.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ProvinceDAO provinceDAO;

    @Autowired
    private AddressDAO addressDAO;

    /**
     * 获取全部“省份”
     *
     * @return List
     */
    @Override
    public List<Province> listAllProvinces() {
        return provinceDAO.listAllProvinces();
    }

    /**
     * 获取某个用户/会员的全部收货地址
     *
     * @param userUid uid
     * @return List<Address>
     */
    @Override
    public List<Address> listAddressesByUserUID(String userUid) {
        return addressDAO.listAddressesByUserUID(userUid);
    }

    /**
     * 获取收货地址
     *
     * @param addressUid uid
     * @return Address
     */
    @Override
    public Address getAddressByAddressUID(String addressUid) {
        return addressDAO.getAddressByAddressUID(addressUid);
    }

    /**
     * 添加收货地址
     *
     * @param address Address
     * @param userUid uid
     */
    @Override
    public void addAddress(Address address, String userUid) {
        address.setAddressUid(StringUtils.createUUID());
        addressDAO.insertAddress(address, userUid);
    }

    @Override
    public void modifyAddressByAddressUID(Address address, String addressUid) {
        addressDAO.updateAddressByAddressUID(address, addressUid);
    }

    /**
     * 删除收货地址
     *
     * @param addressUid uid
     */
    @Override
    public void removeAddressByAddressUID(String addressUid) {
        addressDAO.deleteAddressByAddressUID(addressUid);
    }
}
