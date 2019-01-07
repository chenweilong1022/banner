/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.sys.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.baidu.ueditor.ActionEnter;
import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.config.FileConfig;
import io.renren.modules.oss.cloud.OSSFactory;
import io.renren.modules.oss.entity.SysOssEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/file")
public class FileController extends AbstractController {


	@Autowired
	private FileConfig fileConfig;


	@RequestMapping("/")
	private String showPage(){
		return "index";
	}

//	private Logger logger = LoggerFactory.getLogger(UeditorController.class);

	private String rootPath;

	private String projectPath = null;

	@Autowired
	private Environment environment;


	private final static String staticPath = "static/";

	public FileController() {
		String path  = FileController.class.getClassLoader().getResource("config.json").getPath();
		logger.info("path->"+path);
		File file =  new File(path);
		if(file.getParentFile().isDirectory()) {
			rootPath = new File(path).getParent()+"/";
		}else{
			rootPath = new File(path).getParentFile().getParent()+"/";
			rootPath = rootPath.replace("file:","");
		}
	}


	private String getProjectPath(){
		if(null==projectPath) {
			String val = environment.getProperty("server.context-path", "");
			if ("".equals(val)) {
				val = environment.getProperty("server.contextPath", "");
				if ("".equals(val)) {
					projectPath = "";
					return projectPath;
				}
			}
			projectPath = val.replace("/", "") + "/";
		}
		return projectPath;
	}

	@RequestMapping("/exec")
	public void exec(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		response.setHeader("Content-Type" , "text/html");
		logger.info("rootPath->"+rootPath+",staticPath->"+staticPath+",projectPath->"+getProjectPath());
		out.write( new ActionEnter( request, rootPath,staticPath,getProjectPath()).exec());
	}


	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	public String upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {

		if (file == null) {
			String realPath = request.getServletContext().getRealPath("/");
			return new ActionEnter( request, realPath ).exec();
		}
		return null;
//		if (file.isEmpty()) {
//			throw new RRException("上传文件不能为空");
//		}
//
//		String fileName = RandomUtil.simpleUUID() + "." + FileUtil.extName(file.getOriginalFilename());
//		String saveurl = fileConfig.getSaveurl();
//		File saveFile = new File(saveurl, fileName);
//
//// 		FileCopyUtils.copy(file.getInputStream(),saveFile);
//		FileUtils.copyToFile(file.getInputStream(),saveFile);
//		String url = fileConfig.getBaseurl() + fileName;
//
//
//
//		return R.data(url);
	}

}
