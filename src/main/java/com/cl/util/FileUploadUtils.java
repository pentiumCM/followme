package com.cl.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.cl.constants.Constants;

public class FileUploadUtils {

	
	// "/upload" 是自定义的虚拟上传路径，需要到tomcat里面 server.xml 里面重新配置修改
	private static String virtualPath = "/upload";

	public static Log log = LogFactory.getLog(FileUploadUtils.class);
	
	/**
	 * 
	 * @param ip 服务ip
	 * @param port 服务端口
	 * @param file 处理的文件
	 * @param uploadType 文件类型
	 * @return
	 */
	public static final String upload(String ip, String port, MultipartFile file, Integer uploadType) {
		String uploadPath = getUploadPath(uploadType);
		String filename = extractFilename(file, uploadPath);
		File desc = getAbsoluteFile(filename, uploadType);
		try {
			file.transferTo(desc);
			filename = getAccessPath(ip, port, filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage() + "MultipartFile 转 file 错误");
			e.printStackTrace();
		} 
		return filename;
	}

	// 将MP4转成gif，并返回视频时长和gifpath
	public static final String[] convert2GIF(String ip, String port, String vedioPath, Integer uploadType)
			 {
		String[] gifParam = new String[2];
		String gifUploadPath = getUploadPath(uploadType);
		String vedioUploadPath = getUploadPath(Constants.VEDIO_TYPE);
		String gifPath = "";
		// gif的文件名和vedio名称一样
		gifPath = vedioPath.replace(ip + ":" + port + virtualPath + "/vedio", gifUploadPath).replace(".mp4", ".gif");
		vedioPath = vedioPath.replace(ip + ":" + port + virtualPath + "/vedio", vedioUploadPath);
		// 创建gif文件目录，不写gif文件
		File desc = getAbsoluteFile(gifPath, uploadType);
		// 获取视频时长
		long duration = ReadVideoTime(new File(vedioPath));
		// 将vedio转成gif
		try {
			FFMpegUtil.convetor(duration, "00:00:00", vedioPath, gifPath);
			gifPath = getAccessPath(ip, port, gifPath);
			gifParam[0] = String.valueOf(duration);
			gifParam[1] = String.valueOf(gifPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage() + "MP4转GIF错误");
			e.printStackTrace();
		}
		
		return gifParam;
	}

	/**
	 * 
	 * @param filename 文件名
	 * @param uploadType 文件类型
	 * @return
	 */
	private static final File getAbsoluteFile(String filename, Integer uploadType) {
		File desc = new File(filename);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		// 如果不是gif类型，需要写文件，是的话，就不需要
		if (uploadType != Constants.GIF_TYPE) {
			if (!desc.exists()) {
				try {
					desc.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage() + "创建文件错误");
					e.printStackTrace();
				}
			}
		}

		return desc;
	}

	/**
	 * 获取写到本地的名称
	 * @param file 文件
	 * @param baseDir 文件路径
	 * @return
	 */
	public static final String extractFilename(MultipartFile file, String baseDir) {
		String filename = file.getOriginalFilename();
		filename = UUID.randomUUID() + filename;
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

	/**
	 * 根据文件类型，获取放置文件的路径
	 * @param uploadType
	 * @return
	 */
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
			case Constants.JPG_TYPE:
				uploadPath = uploadPath + "/jpg";
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage() + "读取配置文件信息错误");
			e.printStackTrace();
		}

		return uploadPath;
	}

	/**
	 * 获取文件的访问路径
	 * @param ip
	 * @param port
	 * @param filename
	 * @return
	 */
	public static String getAccessPath(String ip, String port, String filename) {
		String uploadPath = "";
		try {
			uploadPath = PropertiesUtil.readProperties("jdbc.properties").getProperty("filepath");
			filename = filename.replace(uploadPath, ip + ":" + port + virtualPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage() + "读取配置文件信息错误");
			e.printStackTrace();
		}
		return filename;
	}

	/**
	 * 获取视频文件的长度
	 * @param source
	 * @return
	 */
	public static long ReadVideoTime(File source) {
		Encoder encoder = new Encoder();
		long length = 0;
		try {
			MultimediaInfo m = encoder.getInfo(source);
			length = m.getDuration() / 1000;
		} catch (Exception e) {
			log.error(e.getMessage() + "获取MP4视频长度错误");
			e.printStackTrace();
		}
		return length;
	}

}
