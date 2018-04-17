/**
 * @fileName:  FileController.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu[www.codelion.cn]
 * @CreateDate:  2018年4月17日 下午4:19:34
 */ 
package cn.codelion.record.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.codelion.core.util.StringUtils;
import cn.codelion.core.web.BaseController;
import cn.codelion.core.web.bean.RestResult;
import cn.codelion.plugins.alioss.util.ThirdAliOSSUtil;

/** 
 * @author  QiaoYu[www.codelion.cn]
 */
@RestController()
@RequestMapping(value = "/file/")
public class FileController extends BaseController{
	
	@Autowired
	ThirdAliOSSUtil thirdAliOSSUtil;
	@RequestMapping(value = "download.do", method = RequestMethod.GET)
	public void download(String type, String id) {
		if (null == id || id.trim().equals("")) {
		} else {
			String filename = UUID.randomUUID().toString().replace("-", "");
			if (type.equals("mp3")) {
				filename = filename + ".mp3";
				response.setContentType("audio/mpeg");
			} else {
				filename = filename + ".jpg";
				response.setContentType("image/jpeg");
			}
			if (StringUtils.isNotNullUnDefined(id)) {
				InputStream inputStream = null;
				OutputStream outputStream = null;
				try {
					outputStream = response.getOutputStream();
					inputStream = thirdAliOSSUtil.downloadFile(id);
					int read = 0;
					byte[] bytes = new byte[1024];
					while ((read = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, read);
					}
					outputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("下载文件出现错误!");
				} finally {
					try {
						if (null != outputStream) {
							outputStream.close();
						}
						if (null != inputStream) {
							inputStream.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				logger.info("文件id不能为空!");
			}
		}

	}
	
	@RequestMapping(value = "uploadAudio.do", method = RequestMethod.POST)
	public RestResult<String> uploadAudio(@RequestParam("file") MultipartFile file) {
		try {
			if (null != file && !file.isEmpty()) {
				String uuid = thirdAliOSSUtil.uploadFile(file.getInputStream(), "studentaudio", "mp3");
				return ok(uuid);
			} else {
				return failed("", "文件不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return failed("", "上传出现错误");
		}
	}
}
