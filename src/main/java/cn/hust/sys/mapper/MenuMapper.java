package cn.hust.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.hust.core.BaseMapper;
import cn.hust.sys.bean.Menu;
@MapperScan
public interface MenuMapper extends BaseMapper<Menu> {

	public List<Menu> selectUserMenu(@Param("userId")int userId);
	
	//根据父id来查找所有的子id
	public List<Menu> findChild(int id);
	/**
	 * 角色授权模块
	 * 根据roleId来获取指定角色能访问的菜单列表
	 * @param roleId
	 * @return
	 */
	public List<Menu> selectRoleMenu(@Param("roleId")int roleId);
	
	/*
	 * 进行插入的菜单的时候，默认超级管理员可以访问。
	 */
	
	public void addRoleMenu(@Param("role_id")int role_id, @Param("menu_id")int menu_id);
	
	//查询所有的菜单
	public List<Menu> findAll();
	
	//查询角色权限下的所有的菜单
	public List<Integer> findByRole(int roleid);
}
