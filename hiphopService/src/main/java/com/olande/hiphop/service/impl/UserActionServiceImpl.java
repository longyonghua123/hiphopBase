package com.olande.hiphop.service.impl;

import com.olande.hiphop.dao.IUserActionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userActionService")
public class UserActionServiceImpl implements IUserActionDao {
    @Autowired
    @Qualifier("userActionDao")
    private IUserActionDao  userActionDao;
}
