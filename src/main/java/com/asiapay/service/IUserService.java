package com.asiapay.service;


import com.asiapay.model.User;

public interface IUserService {
    public User getUserById(int userId);

    public User selectByName(String userName);

}  