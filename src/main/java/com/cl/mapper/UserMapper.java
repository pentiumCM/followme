package com.cl.mapper;

import com.cl.entity.User;

/**
 * @author 陈敏：842679178@qq.com
 * @version 创建时间：2019年5月23日 上午9:13:45 类说明:
 */
public interface UserMapper {
	/**
	 * 通过用户名查询用户
	 * 
	 * @param userName 用户名
	 * @return 用户类
	 */
	User selectByUserName(String userName);

	/**
	 * 通过手机号查询用户
	 * 
	 * @param phone 手机号
	 * @return 用户类
	 */
	User selectByPhone(String phone);

	/**
	 * 通过邮箱查询用户
	 * 
	 * @param email 邮箱
	 * @return 用户类
	 */
	User selectByEmail(String email);

	/**
	 * 添加用户
	 * 
	 * @param user 用户类
	 * @return 受影响的行数
	 */
	int insert(User user);

	/**
	 * 更新用户信息
	 * 
	 * @param user 用户类
	 * @return 受影响的行数
	 */
	int updateById(User user);

	int deleteById(Integer id);

	int deleteComplete(Integer id);

	User selectById(Integer id);
}
