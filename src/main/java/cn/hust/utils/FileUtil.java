package cn.hust.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;

public class FileUtil {
	private static FileUtil instance = new FileUtil();
	private String baseFolder = "";

	private FileUtil() {
		baseFolder = getClass().getResource(".").getPath();
		baseFolder = baseFolder.substring(0, baseFolder.indexOf("WEB-INF"));
//		baseFolder = "E:\\java\\workspace-hust\\base\\WebContent\\";
		baseFolder = baseFolder+"\\upload\\";
		baseFolder = baseFolder.replaceAll("%(?![0-9a-fA-F]{2})", "%25");  
		try {
			baseFolder = URLDecoder.decode(baseFolder, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static FileUtil getInstance() {
		return instance;
	}

	/**
	 * 创建目录
	 * @param dirPath String 目录名称
	 * @return boolean
	 */
	public boolean mkdir(String dirPath){
		File f = new File(dirPath);
		return f.exists() || f.mkdir();
	}
	
	/**
	 * 循环创建多层目录
	 * @param dirPath String 目录名称
	 * @return boolean
	 */
	public boolean mkdirs(String dirPath){
		File f = new File(dirPath);
		return f.exists() || f.mkdirs();
	}

	/**
	 * 判断文件是否存在
	 * @param filePath Strin 完整的文件路径
	 * @return boolean
	 */
	public boolean exist(String filePath) {
		File f = new File(filePath);
		return f.exists();
	}
	
	/**
	 * 获取随机文件名，不带扩展名
	 * @return String
	 */
	public String getRandomName() {
		Random random = new Random();
		return DateTime.getInstance().file() + ((int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000);

	}
	
	/**
	 * 获取随机文件名，带扩展名
	 * @param ext String 扩展名
	 * @return String
	 */
	public String getRandomName(String ext) {
		return this.getRandomName()+"."+ext;
	}
	
	/**
	 * 根据文件名获取扩展名
	 * @param fileName 文件名
	 * @return String
	 */
	public String getFileExt(String fileName){
		int dot = fileName.lastIndexOf('.');   
        if ((dot >-1) && (dot < (fileName.length() - 1))) {   
            return fileName.substring(dot + 1).toLowerCase();   
        }   
		return fileName.toLowerCase();
	}
	
	/**
	 * 根据扩展名生成新的上传文件名
	 * @param ext String 扩展名
	 * @return String
	 */
	public String newUploadFileName(String ext) {
		String folder = DateTime.getInstance().folder();
		if(mkdirs(baseFolder + folder)) return folder + "/" + getRandomName(ext);
		return null;
	}
	
	/**
	 * 获取当前项目classes目录路径
	 * @return String
	 */
	public String getClassFolder() {
		return getClass().getResource(".").getPath().substring(0, baseFolder.indexOf("WEB-INF"))+"WEB-INF"+File.separator+"classes";
	}
	
	/**
	 * 获取当前项目根路径
	 * @return String
	 */
	public String getBaseFolder() {
		return baseFolder;
	}	

	/**
	 * 设置当前项目根路径
	 * @param baseFolder String 路径
	 */
	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
}
