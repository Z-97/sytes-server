package org.sytes.data.server.fengyun.mapper;
import java.util.List;

import org.sytes.data.server.fengyun.dom.UserInfo;
public interface UserInfoMapper {
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	int insert(UserInfo user);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	UserInfo selectById(Long id);

	/**
	 * 根据手机号查询
	 * @param phone
	 * @return
	 */
	UserInfo selectByPhone(String phone);

	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	UserInfo selectByUserName(String userName);

	List<UserInfo> selectAll();

	/**
	 * 根据id更新
	 * @param user
	 * @return
	 */
	int updateById(UserInfo user);
}