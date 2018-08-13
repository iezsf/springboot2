package cn.hust.sys.bean;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private int id;
	private int pid;
	private String title;
	private String name;
	private int sort;
	private int status=1;
	private String icon;
	private String jump;
	private String type;
	private Integer appId;
	private String isDev;
	private String remark;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getJump() {
		return jump;
	}
	public void setJump(String jump) {
		this.jump = jump;
	}
	//checked的属性用于来显示是否选中
	private boolean checked = false;
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	private List<Menu> list = new ArrayList<Menu>();
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Menu> getList() {
		return list;
	}
	public void setList(List<Menu> list) {
		this.list = list;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsDev() {
		return isDev;
	}
	public void setIsDev(String isDev) {
		this.isDev = isDev;
	}
}
