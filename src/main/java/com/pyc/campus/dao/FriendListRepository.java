//IntelliJ IDEA
//campus
//FriendListRepository
//2020/12/28
// Author:御承扬
//E-mail:2923616405@qq.com


package com.pyc.campus.dao;

import com.pyc.campus.domain.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendListRepository extends JpaRepository<FriendList,Long> {
    @Modifying
    @Transactional
    @Query("select fl from FriendList fl where fl.fromName=?1 and fl.status=false ")
    List<FriendList> toNameIsFalseByFromName(String fromName);
    @Modifying
    @Transactional
    @Query("select fl from FriendList fl where fl.fromName=?1 and fl.status=true ")
    List<FriendList> findMyFriendsByFromName(String fromName);
    @Modifying
    @Transactional
    @Query("select fl from FriendList fl where fl.toName=?1 and fl.status=true")
    List<FriendList> findMyFriendsByToName(String toName);
    @Modifying
    @Transactional
    @Query("update FriendList fl set fl.onlineStatus=?1 where fl.fromName=?2")
    void setOnlineStatus(Boolean onlineStatus,String fromName);

}
