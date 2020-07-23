package com.olande.hiphop.service.impl;

import com.olande.hiphop.dao.IUserSharedDao;
import com.olande.hiphop.service.IUserSharedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userSharedService")
public class UserSharedServiceImpl implements IUserSharedService {
    @Autowired
    @Qualifier("userSharedDao")
    private IUserSharedDao userSharedDao;
}
