package com.chinasoft.mybatis_redis.util;

import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

public class FileUtil {


    /*springboot项目获取项目所在目录  并在同级目录下创建文件夹存放文件*/
    public static String getWebFile(){
        String jar_parent="";
        try {
            jar_parent = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent();
            jar_parent+= File.separator+"LQTProject_file"+File.separator;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return jar_parent;
    }

    public static String fileUpload(MultipartFile file,HttpServletRequest request) {
        /*获取 上传文件对象 后缀名 然后与UUID 随机文件名字符串拼接 相加
         * 获取新上传的文件名 后缀名文件类型不变*/
        String fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".")+1).toString();
       /*filePath值为 上传文件夹相对目录+UUID文件名+文件后缀名
       * 真实上传路径  按年月区分上传时间段*/
       String uploadTime= "/"+Calendar.getInstance().get(Calendar.YEAR)+
               (Calendar.getInstance().get(Calendar.MONTH )+1<10 ? "0" + (Calendar.getInstance().get(Calendar.MONTH )+1):Calendar.getInstance().get(Calendar.MONTH )+1)+"/";
String uploadStatic="pj_static"+uploadTime;
        try {
            File  path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");
        }
            File upload = new File(path.getAbsolutePath(), uploadStatic);
            if (!upload.exists()) {
                upload.mkdirs();
            }
        /*File file1=new File(request.getSession().getServletContext().getRealPath(uploadTime));
        if(!file1.exists()){
            file1.mkdirs();
        }*/
      /*  String filePath= request.getSession().getServletContext().getRealPath(uploadTime)+fileName;*/
        /*实例化一个新的文件对象 有参路径 构造*/
        File newFile=new File(path.getAbsolutePath(),uploadStatic+fileName);

            file.transferTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("后端上传文件失败");
            return  "上传文件路径暂无";
        }
        return  uploadTime+fileName;
    }

    public static void fileDelete(String[] filePaths,HttpServletRequest request){
        try {
            for(String filePath:filePaths){ /*for高级迭代*/
              /*  String url=request.getSession().getServletContext().getRealPath("");
                File file=new File(url+filePath);*/
                File  path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()) {
                    path = new File("");
                }
                File file=new File(path.getAbsolutePath(),"pj_static/"+filePath);
                /*判断文件已存在  则删除文件*/
                if(file.exists()){
                    file.delete();
                }
            }
        }catch (Exception e){
          e.printStackTrace();
          System.out.println("删除文件失败！");
        }

    }
}
