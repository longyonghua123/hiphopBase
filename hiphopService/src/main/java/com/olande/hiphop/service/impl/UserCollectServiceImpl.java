package com.olande.hiphop.service.impl;

import com.olande.hiphop.dao.IUserCollectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userCollectService")
public class UserCollectServiceImpl implements IUserCollectDao {
    @Autowired
    @Qualifier("userCollectDao")
    private IUserCollectDao userCollectDao;
}
