package com.olande.hiphop.service.impl;

import com.olande.hiphop.dao.IUserReplyDao;
import com.olande.hiphop.service.IUserReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userReplyService")
public class UserReplyServiceImpl implements IUserReplyService {
    @Autowired
    @Qualifier("userReplyDao")
    private IUserReplyDao userReplyDao;
}
