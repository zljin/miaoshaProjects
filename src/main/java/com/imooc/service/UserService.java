package com.imooc.service;

import com.imooc.error.BusinessException;
import com.imooc.service.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;

    /**
     *
     * @param telphone:用户注册的手机
     * @param encrptPassword:用户加密的密码
     * @throws BusinessException
     */
    UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
}
