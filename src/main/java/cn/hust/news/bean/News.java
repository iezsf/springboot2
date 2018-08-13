package cn.hust.news.bean;

public class News implements java.io.Serializable  {
	private int id;
	private String article_name;
	private String type;
	private String lable;
	private String article_url;
	private int pv;
	private int ischange;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArticle_name() {
		return article_name;
	}
	public void setArticle_name(String article_name) {
		this.article_name = article_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getArticle_url() {
		return article_url;
	}
	public void setArticle_url(String article_url) {
		this.article_url = article_url;
	}
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public int getIschange() {
		return ischange;
	}
	public void setIschange(int ischange) {
		this.ischange = ischange;
	}
	
}
