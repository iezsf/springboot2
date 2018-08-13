package cn.hust.sys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.hust.core.BaseMapper;
import cn.hust.core.BaseService;
import cn.hust.sys.bean.Role;
import cn.hust.sys.mapper.RoleMapper;

@Service
public class RoleService extends BaseService<Role>{

	@Resource
	private RoleMapper roleMapper;

	@Override
	public BaseMapper<Role> getMapper() {
		// TODO Auto-generated method stub
		return roleMapper;
	}
	
	/**
	 * 给角色分配多个菜单
	 * @param roleId
	 * @param menus
	 * @return
	 */
	public boolean updateRoles(int roleId, String menus) {
		if(roleMapper.find(roleId) == null) return false;

		roleMapper.deleteAllMenu(roleId);
		System.out.println(menus);
		System.out.println("++++++++++++++++++++++++++++++++");
		String[] menuIds = menus.split(",");
		for(String mid:menuIds) {
			if(mid != null && !mid.equals("") )
			roleMapper.addMenu(roleId, Integer.parseInt(mid));
		}
		return true;
	}	
	
	
	public boolean addRoles(int roleId, String menus) {
		if(roleMapper.find(roleId) == null) return false;

		roleMapper.deleteAllMenu(roleId);
		System.out.println(menus);
		System.out.println("++++++++++++++++++++++++++++++++");
		String[] menuIds = menus.split(",");
		for(String mid:menuIds) {
			if(mid != null && !mid.equals("") )
			roleMapper.addMenu(roleId, Integer.parseInt(mid));
		}
		return true;
	}	
	
	
	
	
	/**
	 * 进行批量插入数据
	 */
	public int insertAll(List<Role> lists) {
		int count = 0;
		for(Role role : lists) {
			if(this.insert(role) == 0) {
				continue;
			}else {
				count ++;
			}
		}
		return count;
	}

 
}
