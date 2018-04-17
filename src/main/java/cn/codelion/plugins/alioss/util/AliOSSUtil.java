/**
 * @fileName:  AliOSSUtil.java 
 * @Description:  TODO
 * @CreateName:  QiaoYu 
 * @CreateDate:  2018年2月2日 上午9:53:24
 */
package cn.codelion.plugins.alioss.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;

import cn.codelion.plugins.alioss.config.AliOSSPool;

/**
 * @author QiaoYu
 */
@Service
public class AliOSSUtil {
	private static final Logger logger = LoggerFactory.getLogger(AliOSSUtil.class);
	@Autowired
	AliOSSPool aliOSSPool;
	/**
	 * 上传文件
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月2日 上午10:01:09
	 */
	public String uploadFile(InputStream is, String path, String suffix) {
		String uuid = path + "_" + UUID.randomUUID().toString().replace("-", "") + "." + suffix;
		OSSClient ossClient = aliOSSPool.ossClient;
		ossClient.putObject(new PutObjectRequest(aliOSSPool.BUCKET_NAME, uuid, is));
		logger.debug("阿里oss文件服务上传成功"+uuid);
		return uuid;
	}
	/**
	 * 下载文件
	 * @Description:  TODO
	 * @CreateName:  QiaoYu 
	 * @CreateDate:  2018年2月2日 上午10:01:14
	 */
	public InputStream downloadFile(String osId) {
		OSSClient ossClient = aliOSSPool.ossClient;
		boolean exists = ossClient.doesObjectExist(aliOSSPool.BUCKET_NAME, osId);
		if (exists) {
			// 存在
			OSSObject object = ossClient.getObject(aliOSSPool.BUCKET_NAME, osId);
			InputStream is = object.getObjectContent();
			try {
				logger.info("文件OSID:[" + osId + "]文件Content-Type:" + object.getObjectMetadata().getContentType()+"文件大小为:"+is.available());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return is;
		} else {
			logger.error("文件OSID:[" + osId + "]文件不存在");
			return null;
		}

	}

}
