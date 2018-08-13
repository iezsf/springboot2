package cn.hust.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import cn.hust.core.BaseMapper;
import cn.hust.sys.bean.Role;

@MapperScan
public interface RoleMapper extends BaseMapper<Role>
{
	/**
	 * 给角色分配菜单
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	public int addMenu(@Param("roleId")int roleId, @Param("menuId")int menuId);
	
	/**
	 * 删除角色的全部菜单
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int deleteAllMenu(@Param("roleId")int roleId);

}
