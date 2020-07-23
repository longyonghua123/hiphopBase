package com.olande.hiphop.dao.impl;

import com.olande.hiphop.dao.IUserActionDao;
import com.olande.hiphop.mapper.UserActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("userActionDao")
public class UserActionDaoImpl implements IUserActionDao {
    @Autowired
    @Qualifier("userActionMapper")
    private UserActionMapper  userActionMapper;
}
