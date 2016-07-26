package com.fewave.util.common;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 文件工具
 * @author fewave
 *
 */
public class FileUitl {

	/**
	 * 创建文件夹
	 * 
	 * @param name
	 *            文件夹名
	 */
	public static void createFolder(String name) {
		File myFolderPath = new File(name);
		try {
			if (!myFolderPath.exists()) {
				myFolderPath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param name
	 *            文件名
	 * @param txt
	 *            文件内容
	 */
	public static void createFile(String name, String txt) {
		File myFilePath = new File(name);
		try {
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.println(txt);
			myFile.flush();
			resultFile.close();
		} catch (Exception e) {
			System.out.println("新建文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param name
	 */
	public static void deleteFile(String name) {
		File myDelFile = new File(name);
		try {
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹及里面的文件
	 * 
	 * @param name
	 *            文件夹名
	 */
	public static void deleteFolder(String name) {
		LinkedList folderList = new LinkedList<String>();
		folderList.add(name);
		while (folderList.size() > 0) {
			File file = new File((String) folderList.poll());
			File[] files = file.listFiles();
			ArrayList<File> fileList = new ArrayList<File>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					folderList.add(files[i].getPath());
				} else {
					fileList.add(files[i]);
				}
			}
			for (File f : fileList) {
				f.delete();
			}
		}
		folderList = new LinkedList<String>();
		folderList.add(name);
		while (folderList.size() > 0) {
			File file = new File((String)folderList.getLast());
			if (file.delete()) {
				folderList.removeLast();
			} else {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					folderList.add(files[i].getPath());
				}
			}
		}
	}

}
