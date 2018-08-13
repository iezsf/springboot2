package cn.hust.sys.service;

import cn.hust.core.BaseMapper;
import cn.hust.core.BaseService;
import cn.hust.sys.bean.Role;
import cn.hust.sys.bean.User;
import cn.hust.sys.bean.UserInfo;
import cn.hust.sys.mapper.UserMapper;
import cn.hust.utils.Md5Util;
import cn.hust.utils.StringUtils;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/4/19.
 */

@Service(value = "userService")
public class UserService extends BaseService<User> {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    public int addUser(User user) {
        return userMapper.insert(user);
    }

    

	public User login(String userName, String password) {
		// TODO Auto-generated method stub
		User u = userMapper.login(userName, password);
		return u;
	}

	@Override
	public BaseMapper<User> getMapper() {
		// TODO Auto-generated method stub
		return userMapper;
	}



	public User findbyname(String username) {
		// TODO Auto-generated method stub
		return userMapper.findbyname(username);
	}
	

    /**
	 * 插入一条数据 
	 * @param bean T 要插入的数据
	 * @return int 插入的行数
	 */
    @Override
	public int insert(User user) {
		user.setStatus('1');
		user.setDel(false);
		user.setCreateTime(new Timestamp(System.currentTimeMillis()));
		user.setUpdateTime(user.getCreateTime());
		user.setSalt(StringUtils.random(6));
		//密码
		String password = Md5Util.getInstance().password("123456");
		user.setPassword(Md5Util.getInstance().password(password+user.getSalt()));
		if(userMapper.insert(user) <= 0) return 0;

		UserInfo info = user.getInfo();
		if(info == null) {
			info = new UserInfo();
			info.setUserId(user.getId());
			info.setLoginCount(0);
		}
		userMapper.addInfo(info);
		
		if(user.getRoles() == null || user.getRoles().size() < 1) return 1;
		
		for(Role role : user.getRoles()) {
			userMapper.addRole(user.getId(), role.getId());
		}
		return 1;
	}
    
    /**
     * 更新用户信息
     */
    @Override
    public int update(User user) {
		
		if(userMapper.update(user) <= 0) return 0;

		UserInfo info = user.getInfo();
		if(info != null) {
			info.setUserId(user.getId());
			if(userMapper.getInfo(user.getId()) != null) {
				userMapper.updateInfo(info);
			}else {
				userMapper.addInfo(info);				
			}
		}else if(info == null) {
			info = userMapper.getInfo(user.getId());
			if(info == null) {
				info = new UserInfo();
				info.setUserId(user.getId());
				info.setLoginCount(0);
				userMapper.addInfo(info);
			}
		}		
		
		if(user.getRoles() == null || user.getRoles().size() < 1) return 1;
		
		userMapper.deleteRoles(user.getId());
		
		for(Role role : user.getRoles()) {
			userMapper.addRole(user.getId(), role.getId());
		}
		return 1;
    }
    
    /**
     * 更新用户信息
     */
    public int updateInfo(UserInfo info,User user) {
		
		if(userMapper.update(user) <= 0) return 0;

		if(info != null) {
			info.setUserId(user.getId());
			if(userMapper.getInfo(user.getId()) != null) {
				userMapper.updateInfo(info);
			}else {
				userMapper.addInfo(info);				
			}
		}else if(info == null) {
			info = userMapper.getInfo(user.getId());
			if(info == null) {
				info = new UserInfo();
				info.setUserId(user.getId());
				info.setLoginCount(0);
				userMapper.addInfo(info);
			}
		}		
		return 1;
    }
}
