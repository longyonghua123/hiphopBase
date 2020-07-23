package com.olande.hiphop.dao.impl;

import com.olande.hiphop.dao.IUserCollectDao;
import com.olande.hiphop.mapper.UserCollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("userCollectDao")
public class UserCollectDaoImpl implements IUserCollectDao {
    @Autowired
    @Qualifier("userCollectMapper")
    private UserCollectMapper userCollectMapper;


}
