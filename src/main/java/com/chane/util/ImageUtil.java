package com.chane.util;

import com.chane.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200,200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(337,640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
					.outputQuality(0.9f).toFile(dest);
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

	/**
	 * storePath是文件的路径和目录的路径
	 * @param storePath
	 */
    public static void deleteFileOrPath(String storePath){
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()){
			if(fileOrPath.isDirectory()){
				File files[] = fileOrPath.listFiles();
				for(int i = 0;i<files.length;i++){
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}


	public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
		int count = 0;
		List<String> relativeAddrList = new ArrayList<String>();
		if (imgs != null && imgs.size() > 0) {
			makeDirPath(targetAddr);
			for (CommonsMultipartFile img : imgs) {
				String realFileName = FileUtil.getRandomFileName();
				//String extension = getFileExtension(img);
				//String relativeAddr = targetAddr + realFileName + count + extension;
				String relativeAddr = targetAddr + realFileName + count ;
				File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
				count++;
				try {
					Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
				} catch (IOException e) {
					throw new RuntimeException("创建图片失败：" + e.toString());
				}
				relativeAddrList.add(relativeAddr);
			}
		}
		return relativeAddrList;
	}
}
