package com.imooc.dao;

import com.imooc.dataobject.userPasswordDO;
import org.springframework.stereotype.Repository;

@Repository
public interface userPasswordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 30 10:55:25 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 30 10:55:25 CST 2019
     */
    int insert(userPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 30 10:55:25 CST 2019
     */
    int insertSelective(userPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 30 10:55:25 CST 2019
     */
    userPasswordDO selectByPrimaryKey(Integer id);

    userPasswordDO selectByUserId(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 30 10:55:25 CST 2019
     */
    int updateByPrimaryKeySelective(userPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Sun Jun 30 10:55:25 CST 2019
     */
    int updateByPrimaryKey(userPasswordDO record);
}