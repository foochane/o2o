package com.chane.util;


public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String  basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/code/springboot/o2o/image";
        }else {
            basePath = "home/chane/image";
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    public static String getShopImage(long shopId){
        String imagePath = "/upload/item/shop/"+ shopId + "/";
        return imagePath.replace("/",separator);
    }

//    public static void main(String[] args){
//        System.out.println(getImgBasePath());
//        System.out.println(getShopImage(12));
//    }
}
