package com.olande.hiphop.dao.impl;

import com.olande.hiphop.dao.IUserReplyDao;
import com.olande.hiphop.mapper.UserReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("userReplyDao")
public class UserReplyDaoImpl implements IUserReplyDao {
    @Autowired
    @Qualifier("userReplyMapper")
    private UserReplyMapper userReplyMapper;
}
