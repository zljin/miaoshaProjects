package com.imooc.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.imooc.dao.UserDOMapper;
import com.imooc.dao.userPasswordDOMapper;
import com.imooc.dataobject.UserDO;
import com.imooc.dataobject.userPasswordDO;
import com.imooc.error.BusinessException;
import com.imooc.error.EmBusinessError;
import com.imooc.service.UserService;
import com.imooc.service.model.UserModel;
import com.imooc.validator.ValidationResult;
import com.imooc.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private userPasswordDOMapper uPm;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO==null){
            return null;
        }
        userPasswordDO userPasswordDO = uPm.selectByUserId(id);
        return convertFromDataObject(userDO,userPasswordDO);
    }


    private UserModel convertFromDataObject(UserDO userDO, userPasswordDO userPasswordDO){
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        return userModel;
    }

    @Override
    @Transactional//声明事务
    public void register(UserModel userModel) throws BusinessException{
        //校验
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if (StringUtils.isEmpty(userModel.getName())
//                || userModel.getGender() == null
//                || userModel.getAge() == null
//                || StringUtils.isEmpty(userModel.getTelephone())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        //框架校验
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        //实现model->dataobject方法
        UserDO userDO = convertFromModel(userModel);
        //insertSelective相对于insert方法，不会覆盖掉数据库的默认值
        userDOMapper.insertSelective(userDO);
        System.out.println(userDO.getId()+"()()()()()()()");
        userModel.setId(userDO.getId());

        userPasswordDO upd = convertPasswordFromModel(userModel);
        uPm.insertSelective(upd);

        return;
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        //通过用户的手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        userPasswordDO userPasswordDO = uPm.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);


        //比对用户信息内加密的密码与手机是否匹配
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;

    }

    private userPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel==null)
            return null;
        userPasswordDO upd = new userPasswordDO();
        upd.setEncrptPassword(userModel.getEncrptPassword());
        upd.setUserId(userModel.getId());
        return upd;
    }


    private UserDO convertFromModel(UserModel userModel){
        if(userModel==null)
            return null;
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }
}
