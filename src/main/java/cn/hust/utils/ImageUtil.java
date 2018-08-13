package cn.hust.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

public class ImageUtil {

	public String path = "";
	private BufferedImage bi;
	private int width;
	private int height;

	public ImageUtil(String path) {
		this.path = path;
		try {
			bi = ImageIO.read(new FileInputStream(path));
			this.width = bi.getWidth();
			this.height = bi.getHeight();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ImageUtil resize(int width) {
		if (bi == null)
			return null;

		if (this.width <= width) {
			return this;
		} else {
			this.height = (int) (this.height / this.width * width);
			this.width = width;
			this.compress();
			return this;
		}
	}

	public ImageUtil resize(int width, int height) {

		if (bi == null)
			return null;
		if (this.width <= width && this.height <= height)
			return this;

		if (this.width > width) {
			this.width = width;
			this.height = (int) (this.height / this.width * width);
		}
		if (this.height > height) {
			this.height = height;
			this.width = (int) (this.width / this.height * height);
		}

		this.compress();

		return this;
	}

	public void compress() {

		Image im = bi.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bi.getGraphics();
		graphics.drawImage(im, 0, 0, null);
		graphics.dispose();
	}

	public ImageUtil cut(int x, int y, int w, int h) {
		bi = bi.getSubimage(x, y, w, h);
		return this;
	}

	/**
	 * 保存
	 * 
	 * @param file
	 *            String 文件路径
	 * @param format
	 *            压缩格式: jpg png
	 */
	public void save(String file, String format) {

		try {
			ImageIO.write(bi, format, new FileOutputStream(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存
	 * 
	 * @param os
	 *            OutputStream 输出流
	 * @param format
	 *            压缩格式: jpg png
	 */
	public void save(OutputStream os, String format) {
		try {
			ImageIO.write(bi, format, os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}