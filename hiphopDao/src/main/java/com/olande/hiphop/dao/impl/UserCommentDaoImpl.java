package com.olande.hiphop.dao.impl;

import com.olande.hiphop.dao.IUserCommentDao;
import com.olande.hiphop.mapper.UserCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("userCommentDao")
public class UserCommentDaoImpl implements IUserCommentDao {
    @Autowired
    @Qualifier("userCommentMapper")
    private UserCommentMapper  userCommentMapper;
}
