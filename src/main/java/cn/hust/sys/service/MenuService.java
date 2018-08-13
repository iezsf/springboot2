package cn.hust.sys.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hust.core.BaseMapper;
import cn.hust.core.BaseService;
import cn.hust.sys.bean.Menu;
import cn.hust.sys.mapper.MenuMapper;

@Service(value = "menuService")
public class MenuService extends BaseService<Menu>{
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public BaseMapper<Menu> getMapper() {
		// TODO Auto-generated method stub
		return menuMapper;
	}

	/**
	 * 根据父id来查找所有的子节点
	 * @param zhuyi
	 * @return
	 */
	public List<Menu> findChild(int parent){
		List<Menu> lists =  menuMapper.findChild(parent);
		return lists;
	}
	
	public List<Menu> selectUserMenu(int userId){
		return menuMapper.selectUserMenu(userId);
	}
	
	public List<Menu> selectRoleMenu(int roleId){
		return menuMapper.selectRoleMenu(roleId);
	}
	
	@Override
	public int insert(Menu bean) {
		int count = menuMapper.insert(bean);
		
		/*if(bean.getRoles() == null && bean.getRoles().size() == 0) {
			
		}else {
			menuMapper.addRoleMenu(bean.getRoles().get(0).getId(), bean.getId());
		}*/
				
		return count;
	}
	
	public String getIds(int roleid) {
		List<Integer> all = menuMapper.findByRole(roleid);
		StringBuffer stringBuffer = new StringBuffer();
		for(Integer m : all) {
			stringBuffer.append(m);
			stringBuffer.append(",");
		}
		return stringBuffer.toString();
	}
	
	//一次性查询所有的菜单，并递归产生树结构,用于权限的那一部分
	public List<Menu> findAll(int id) {
		List<Menu> root = menuMapper.findChild(0);
		List<Menu> all = menuMapper.findAll();
		List<Integer> allRole = menuMapper.findByRole(id);
		Iterator iterator1 = root.iterator();
		Iterator iterator2 = all.iterator();
		while(iterator1.hasNext()) {
			Menu m1 = (Menu) iterator1.next();
			for(Integer i : allRole) {
				if(m1.getId() == i) {
					m1.setChecked(true);
				}
			}
		}
		while(iterator2.hasNext()) {
			Menu m2 = (Menu) iterator2.next();
			for(Integer i : allRole) {
				if(m2.getId() == i) {
					m2.setChecked(true);
				}
			}
		}
		if(root == null || root.size() == 0) {
			return null;
		}
		if(all == null || all.size() == 0) {
			return root;
		}
		for(Menu menu : root) {
			recur(menu , all);
		}
		return root;
		
	}
	
	public List<Menu> findByGrant(int id){
		List<Menu> root = menuMapper.findChild(0);
		List<Menu> all = menuMapper.findAll();
		List<Integer> allRole = menuMapper.findByRole(id);
		Iterator iterator1 = root.iterator();
		Iterator iterator2 = all.iterator();
		while(iterator1.hasNext()) {
			Menu m1 = (Menu) iterator1.next();
			for(Integer i : allRole) {
				if(m1.getId() == i) {
					m1.setChecked(true);
				}
			}
			if(!m1.isChecked()) {
				iterator1.remove();
			}
		}
		while(iterator2.hasNext()) {
			Menu m2 = (Menu) iterator2.next();
			for(Integer i : allRole) {
				if(m2.getId() == i) {
					m2.setChecked(true);
				}
			}
			if(!m2.isChecked()) {
				iterator2.remove();
			}
		}
		if(root == null || root.size() == 0) {
			return null;
		}
		if(all == null || all.size() == 0) {
			return root;
		}
		for(Menu menu : root) {
			recur(menu , all);
		}
		return root;
		
	}
	
	//递归生成子菜单
	private void recur(Menu menu, List<Menu> all) {
		List<Menu> list = new ArrayList<Menu>();
		for(int i=0;i<all.size();i++) {
			Menu m = all.get(i);
			if(m != null) {
				if(m.getPid() == menu.getId()) {
					list.add(m);
					all.set(i, null);
				}
			}
		}
		
		if(list.size() == 0) {
			return ;
		}else {
			menu.setList(list);
			for(Menu menu1 : list) {
				recur(menu1, all);
			}
		}
		
		
	}

	public List<Menu> getNavList(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
