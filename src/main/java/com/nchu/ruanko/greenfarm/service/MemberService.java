package com.nchu.ruanko.greenfarm.service;

import com.nchu.ruanko.greenfarm.pojo.entity.User;
import com.nchu.ruanko.greenfarm.pojo.vo.AdminMemberPageVO;

public interface MemberService {


    AdminMemberPageVO listAllLegalMemberWithPage(int pageNum, int pageSize, int pageNavigationSize);

    User loadMemberDetailInfoByMemberID(String memberID);

    boolean deleteMemberByID(String memberID);

    AdminMemberPageVO listAllIllegalMemberWithPage(int pageNum, int pageSize, int pageNavigationSize);

    boolean undoDeleteMemberByID(String memberID);
}
