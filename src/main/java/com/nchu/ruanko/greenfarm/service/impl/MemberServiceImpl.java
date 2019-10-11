package com.nchu.ruanko.greenfarm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nchu.ruanko.greenfarm.dao.AddressDAO;
import com.nchu.ruanko.greenfarm.dao.UserDAO;
import com.nchu.ruanko.greenfarm.pojo.entity.Address;
import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminMemberPageVO;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminMemberVO;
import com.nchu.ruanko.greenfarm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AddressDAO addressDAO;

    /**
     * 加载所有合法的会员信息
     * @param pageNum
     * @param pageSize
     * @param pageNavigationSize
     * @return
     */
    @Override
    public AdminMemberPageVO listAllLegalMemberWithPage(int pageNum, int pageSize, int pageNavigationSize) {
        AdminMemberPageVO vo = new AdminMemberPageVO();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDAO.listAllMembers();
        PageInfo<User> pageInfo = new PageInfo<>(userList, pageNavigationSize);
        List<AdminMemberVO> adminMemberVOList = new ArrayList<>();

        for(User user : userList){
            AdminMemberVO adminMemberVO = new AdminMemberVO();
            List<Address> addressList = addressDAO.listAddressesByUserUID(user.getUserUid());
            adminMemberVO.setAddressList(addressList);
            adminMemberVO.setMember(user);
            adminMemberVOList.add(adminMemberVO);
        }

        vo.setAdminMemberVOList(adminMemberVOList);
        vo.setPageInfo(pageInfo);
        return vo;
    }

    @Override
    public User loadMemberDetailInfoByMemberID(String memberID) {
        return userDAO.getUserByUID(memberID);
    }

    @Override
    public boolean deleteMemberByID(String memberID) {
        if(userDAO.updateUserDateByUserId(memberID,0)>=1)
            return true;
        else
            return false;
    }

    @Override
    public AdminMemberPageVO listAllIllegalMemberWithPage(int pageNum, int pageSize, int pageNavigationSize) {
        AdminMemberPageVO vo = new AdminMemberPageVO();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDAO.listAllIllegalMembers();
        PageInfo<User> pageInfo = new PageInfo<>(userList, pageNavigationSize);
        List<AdminMemberVO> adminMemberVOList = new ArrayList<>();

        for(User user : userList ){
            AdminMemberVO adminMemberVO = new AdminMemberVO();
            List<Address> addressList = addressDAO.listAddressesByUserUID(user.getUserUid());
            adminMemberVO.setAddressList(addressList);
            adminMemberVO.setMember(user);
            adminMemberVOList.add(adminMemberVO);
        }

        vo.setAdminMemberVOList(adminMemberVOList);
        vo.setPageInfo(pageInfo);
        return vo;
    }

    @Override
    public boolean undoDeleteMemberByID(String memberID) {
        System.out.println("撤销删除"+memberID);
        if(userDAO.updateUserDateByUserId(memberID,1)>=1)
            return true;
        else
            return false;
    }
}
