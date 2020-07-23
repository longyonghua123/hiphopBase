package com.olande.common.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public final class FileUtils {
	public final static int copyFolder(File srcFolder, String copyFolderPath,
			int fileCount) throws IOException {
		File copyFolder = new File(copyFolderPath);
		if (!copyFolder.isDirectory()) {
			if (!copyFolder.mkdirs()) {
				String message = "can not make folder path:"
						+ srcFolder.getPath();
				throw new IllegalArgumentException(message);
			}
		}
		if (!srcFolder.isDirectory()) {//判断是否为文件夹
			String message = "error resource path:" + srcFolder.getPath();
			throw new IllegalArgumentException(message);
		}

		File files[] = srcFolder.listFiles();
		int length = files.length;
		for (int index = 0; index < length; index++) {
			File file = files[index];
			String copyFilePath = copyFolderPath + "\\" + file.getName();
			System.out.println("FileUtils copyFilePath="+copyFilePath);
			if (file.isFile()) {
				if (copyFile(file, copyFilePath)) {
					fileCount++;
				}
			} else if (file.isDirectory()) {
				fileCount += copyFolder(file, copyFilePath, 0);
			} else {
				String message = "error copy file path:" + srcFolder.getPath();
				throw new IllegalArgumentException(message);
			}
		}
		return fileCount;
	}

	public final static File createFolder(String dirPath) {
		File folder = new File(dirPath);
		if (!folder.isDirectory()) {
			if (!folder.mkdirs()) {
				String message = "can not make folder path:" + dirPath;
				throw new IllegalArgumentException(message);
			}
		}
		return folder;
	}

	public final static File createFile(String filePath) throws IOException {

		File file = new File(filePath);
		if (!file.isFile()) {
			String folderPath = filePath.substring(0,
					filePath.lastIndexOf("\\"));
			createFolder(folderPath);
			if (!file.createNewFile()) {
				String message = "can not make file  path:" + filePath;
				throw new IllegalArgumentException(message);
			}
		}
		return file;
	}

	public final static boolean copyFile(File srcFile, String copyFilePath)
			throws IOException {
		File copyFile = new File(copyFilePath);
		if (copyFile.isFile()) {
			return false;
		} else {
			copyFile = createFile(copyFilePath);
			InputStream in = new FileInputStream(srcFile);
			OutputStream out = new FileOutputStream(copyFile);
			byte bytes[] = new byte[1024];
			while (-1 != in.read(bytes)) {
				out.write(bytes);
			}
			out.close();
			in.close();
		}
		return true;
	}

	public final static void deleteSubFiles(String dirPath) {
		File file = new File(dirPath);
		if (file.isDirectory()) {
			File[] subfiles = file.listFiles();
			int length = subfiles.length;
			for (int index = 0; index < length; index++) {
				if (subfiles[index].isFile()) {
					subfiles[index].delete();
				} else {
					deleteSubFiles(subfiles[index].getPath());
					subfiles[index].delete();
				}
			}
		}
	}

	public final static boolean isValidXMLFile(String xmlFilePath) {
		File xmlFile = new File(xmlFilePath);
		boolean isValid = xmlFile.isFile();
		if (isValid) {
			xmlFilePath = xmlFilePath.toLowerCase();
			isValid = xmlFilePath.endsWith(".xml");
		}
		return isValid;
	}

	/**
	 * 
	 * @describe
	 * @method zoomImage
	 * @param srcImage
	 * @param destFile
	 * @param destWidth
	 * @param destHeight
	 *            void
	 */
	public final static void zoomImage(BufferedImage srcImage, File destFile,
			int destWidth, int destHeight) {
		try {
			int imgWidth = destWidth;
			int imgHeight = destHeight;
			int srcWidth = srcImage.getWidth();
			int srcHeight = srcImage.getHeight();
			if (srcWidth > imgWidth || srcHeight > imgHeight) {
				if (srcHeight >= srcWidth) {
					imgWidth = (int) Math
							.round(((destHeight * 1.0 / srcHeight) * srcWidth));
				} else {
					imgHeight = (int) Math
							.round(((destWidth * 1.0 / srcWidth) * srcHeight));
				}
			} else {
				imgWidth = srcWidth;
				imgHeight = srcHeight;
			}
			BufferedImage destBufferedImage = new BufferedImage(imgWidth,
					imgHeight, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, imgWidth, imgHeight);
			graphics2D.drawImage(srcImage.getScaledInstance(imgWidth,
					imgHeight, Image.SCALE_SMOOTH), 0, 0, null);
			graphics2D.dispose();
			ImageIO.write(destBufferedImage, "JPEG", destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static boolean isImgFile(String imgName) {
		imgName = imgName.toLowerCase();
		Pattern p = Pattern
				.compile("[^\\/\\.<>|\":]+\\.(jpg|gif|png|bmp|jpeg)$");
		return p.matcher(imgName).matches();
	}
    //判断是否为视频文件
	public final static boolean isVideoFile(String imgName) {
		imgName = imgName.toLowerCase();//文件名转为小写
		Pattern p = Pattern
				.compile("[^\\/\\.<>|\":]+\\.(mp4|ogg|m4v|3gpp|mpeg|mov|mkv)$");//视频类型，匹配视频文件的类型
		return p.matcher(imgName).matches();
	}

	public final static List<String> getRawImgNames(String rawImgDirPath) {
		List<String> imgNameList = new ArrayList<String>();
		File rawImgDir = new File(rawImgDirPath);
		if (rawImgDir.isDirectory()) {
			File[] imgFiles = rawImgDir.listFiles();
			for (File file : imgFiles) {
				String fileName = file.getName();
				if (isImgFile(fileName)) {
					fileName = fileName.substring(0, fileName.lastIndexOf('.'));
					imgNameList.add(fileName);
				}
			}
		}
		return imgNameList;
	}
}