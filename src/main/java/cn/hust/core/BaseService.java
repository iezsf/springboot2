package cn.hust.core;

import java.util.List;
import java.util.Map;

import java.util.List;
import java.util.Map;

public abstract class BaseService<T> {	
	private String error = "";
	
	/**
	 * 根据ID查询一条数据
	 * @param id int  ID
	 * @return T
	 */
	public T find(int id) {
		BaseMapper<T> mapper = this.getMapper();
		return mapper.find(id);
	}
	
	
	/**
	 * 把物理删除改成逻辑删除
	 * 根据ID删除一条数据
	 * @param id int  ID
	 * @return int 删除的行数
	 */
	public int delete(int id) {
		return this.getMapper().disable(id);
	}
	
	/**
	 * 根据ID禁用一条数据
	 * @param id int  ID
	 * @return int 禁用的数据行数
	 */
	public int disable(int id) {
		return this.getMapper().disable(id);
	}
	
	/**
	 * 根据ID启用一条数据
	 * @param id int  ID
	 * @return int 启用的数据行数
	 */
	public int enable(int id) {
		return this.getMapper().enable(id);
	}
	
	/**
	 * 更新一条数据
	 * @param bean T  要更新的数据
	 * @return int 更新的数据行数
	 */
	public int update(T bean) {
		return this.getMapper().update(bean);
	}
	
	/**
	 * 插入一条数据 
	 * @param bean T 要插入的数据
	 * @return int 插入的行数
	 */
	public int insert(T bean) {
		return this.getMapper().insert(bean);
	}
	
	/**
	 * 分页查询
	 * @param where Map<String,Object> 查询条件
	 * @param page int 当前页码
	 * @param rows int 返回条数
	 * @param order String 排序方式
	 * @return List<T>
	 */
	public List<T> pageSelect(Map<String,Object> where){
		return this.getMapper().pageSelect(where);
	}
	
	/**
	 * 查询总数
	 * @param where Map<String,Object> 查询条件
	 * @return int
	 */
	public int pageCount(Map<String,Object> where) {
		return this.getMapper().pageCount(where);
	}
	
	/**
	 * 分页查询
	 * @return List<T>
	 */
	public List<T> selectAll(Map<String,Object> map){
		return this.getMapper().selectAll(map);
	}
	
	abstract public BaseMapper<T> getMapper();

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
