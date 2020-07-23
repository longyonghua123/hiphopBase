package com.olande.hiphop.service.impl;

import com.olande.hiphop.dao.IUserCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userCommentService")
public class UserCommentServiceImpl implements IUserCommentDao {
    @Autowired
    @Qualifier("userCommentDao")
    private IUserCommentDao  userCommentDao;
}
