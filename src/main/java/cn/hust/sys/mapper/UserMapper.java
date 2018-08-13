package cn.hust.sys.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.hust.core.BaseMapper;
import cn.hust.sys.bean.Role;
import cn.hust.sys.bean.User;
import cn.hust.sys.bean.UserInfo;

import java.util.List;

@MapperScan
public interface UserMapper extends BaseMapper<User>{  


    int insert(User record);

    List<User> selectUsers();
    
    User login(@Param("userName")String userName , @Param("password")String password);

	User findbyname(@Param("userName")String username);
	
	
	/**
	 * 添加角色
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int addRole(@Param("userId")int userId, @Param("roleId")int roleId);

	/**
	 * 查询用户全部角色
	 * @param userId
	 * @return
	 */
	public List<Role> getRoles(@Param("userId")int userId);
	
	/**
	 * 删除用户全部角色
	 * @param id
	 * @return
	 */
	public int deleteRoles(int id);
	
	/**
	 * 保存用户详细信息
	 * @param info UserInfo
	 * @return int
	 */
	public int addInfo(UserInfo info);
	
	/**
	 * 更新用户详细信息
	 * @param info UserInfo
	 * @return int
	 */
	public int updateInfo(UserInfo info);
	
	/**
	 * 查询用户详细信息
	 * @param id
	 * @return
	 */
	public UserInfo getInfo(int id);
	
}