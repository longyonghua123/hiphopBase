package com.olande.hiphop.dao.impl;

import com.olande.hiphop.dao.IUserSharedDao;
import com.olande.hiphop.mapper.UserSharedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("userSharedDao")
public class UserSharedDaoImpl implements IUserSharedDao {
    @Autowired
    @Qualifier("userSharedMapper")
    private UserSharedMapper userSharedMapper;
}
