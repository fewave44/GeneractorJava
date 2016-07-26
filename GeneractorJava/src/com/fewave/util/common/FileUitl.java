package com.fewave.util.common;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * �ļ�����
 * @author fewave
 *
 */
public class FileUitl {

	/**
	 * �����ļ���
	 * 
	 * @param name
	 *            �ļ�����
	 */
	public static void createFolder(String name) {
		File myFolderPath = new File(name);
		try {
			if (!myFolderPath.exists()) {
				myFolderPath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("�½�Ŀ¼��������");
			e.printStackTrace();
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param name
	 *            �ļ���
	 * @param txt
	 *            �ļ�����
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
			System.out.println("�½��ļ���������");
			e.printStackTrace();
		}
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param name
	 */
	public static void deleteFile(String name) {
		File myDelFile = new File(name);
		try {
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("ɾ���ļ���������");
			e.printStackTrace();
		}
	}

	/**
	 * ɾ���ļ��м�������ļ�
	 * 
	 * @param name
	 *            �ļ�����
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
