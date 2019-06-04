package com.cl.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.cl.constants.Constants;

public class FileUploadUtils {
    
    public static final String upload(String ip,String port,MultipartFile file, Integer uploadType)throws Exception{
        String uploadPath = getUploadPath(uploadType);
    	String filename = extractFilename(file, uploadPath);
        File desc = getAbsoluteFile(filename);
        file.transferTo(desc);
        filename = getAccessPath(ip, port, filename);
        System.out.println(filename);
        
        return filename;
    }
    
    
    private static final File getAbsoluteFile (String filename) throws IOException {
//        File desc = new File(uploadDir + "/" + filename);
        File desc = new File(filename);
        if(!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();        }
        if(!desc.exists()) {
            desc.createNewFile();        }
        return desc;
    }

    public static final String extractFilename(MultipartFile file, String baseDir) throws UnsupportedEncodingException {
        String filename = file.getOriginalFilename();
        int slashIndex = filename.indexOf("/");
        if (slashIndex >= 0) {
            filename = filename.substring(slashIndex + 1);
        }
        filename = baseDir + "/" + filename;
        return filename;
    }
    public static final String extractUploadDir(HttpServletRequest request) {
        return request.getServletContext().getRealPath("/");
    }
    
    public static String getUploadPath(Integer uploadType) {
    	String uploadPath = "";
		try {
			uploadPath = PropertiesUtil.readProperties("jdbc.properties").getProperty("filepath");
			switch (uploadType) {
			case Constants.VEDIO_TYPE:
				uploadPath = uploadPath + "/vedio";
				break;
			case Constants.GIF_TYPE:
				uploadPath = uploadPath + "/gif";
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uploadPath;
	}
    
    public static String getAccessPath(String ip,String port,String filename) {
    	String uploadPath = "";
    	try {
			uploadPath = PropertiesUtil.readProperties("jdbc.properties").getProperty("filepath");
			filename.replaceAll(uploadPath, ip+port+"/upload");
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return filename;
	}
}
