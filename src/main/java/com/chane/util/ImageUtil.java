package com.chane.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Random;

public class ImageUtil {

	//String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static String basePath = "D:\\code\\springboot\\o2o\\image\\watermark";
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static  final Random r = new Random();

	public static void main(String[] args) throws IOException {

		Thumbnails.of(new File("D:\\code\\springboot\\o2o\\image\\watermark\\xiaoxiang.jpg")).size(200,200)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
				.outputQuality(0.8f).toFile("D:\\code\\springboot\\o2o\\image\\watermark\\xiaoxiangnew.jpg");
	}
	public static String generateThumbnail(InputStream thumbnailInputStream, String fileName,String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnailInputStream).size(200,200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}



	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
}
