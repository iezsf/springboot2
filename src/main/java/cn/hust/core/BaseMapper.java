package cn.hust.core;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
	/**
	 * 根据ID查询一条数据
	 * @param id int  ID
	 * @return T
	 */
	public T find(int id);
	
	/**
	 * 根据ID删除一条数据
	 * @param id int  ID
	 * @return int
	 */
	public int delete(int id);
	
	/**
	 * 根据ID禁用一条数据
	 * @param id int  ID
	 * @return int 禁用的数据行数
	 */
	public int disable(int id);
	
	/**
	 * 根据ID启用一条数据
	 * @param id int  ID
	 * @return 启用的数据行数
	 */
	public int enable(int id);
	
	/**
	 * 更新一条数据
	 * @param bean T  要更新的数据
	 * @return int
	 */
	public int update(T bean);
	
	/**
	 * 插入一条数据 
	 * @param bean T 要插入的数据
	 * @return 插入的行数
	 */
	public int insert(T bean);
	
	/**
	 * 分页查询
	 * @param where Map<String,String> 查询条件
	 * @return List<T>
	 */
	public List<T> pageSelect(Map<String,Object> where);
	
	/**
	 * 查询总数
	 * @param map Map<String,String> 查询条件
	 * @return int
	 */
	public int pageCount(Map<String,Object> map);
	
	/**
	 * 查询全部
	 * @return List<T> 
	 * @param map Map<String,String> 查询条件
	 */
	public List<T> selectAll(Map<String,Object> map);
	
}
