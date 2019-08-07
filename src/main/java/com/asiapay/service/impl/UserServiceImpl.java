package com.asiapay.service.impl;


import com.asiapay.dao.IUserDao;
import com.asiapay.model.User;
import com.asiapay.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements IUserService {
    @Resource  
    private IUserDao userDao;
    
    public User getUserById(int userId) {
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }

    public User selectByName(String userName) {
        return this.userDao.selectByName(userName);
    }

}  
