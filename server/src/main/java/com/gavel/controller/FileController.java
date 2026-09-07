package com.gavel.controller;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gavel.annotation.IgnoreAuth;
import com.gavel.entity.EIException;
import com.gavel.utils.R;

import jakarta.annotation.PostConstruct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 文件上传/下载。
 *
 * 安全设计：
 * 1. 上传：扩展名白名单 + 随机文件名（UUID），原文件名不落盘；
 * 2. 下载：强制解析后文件必须仍位于 upload 目录内，杜绝 ../ 路径穿越；
 * 3. 上传接口需要登录（原版任何人可传）。
 */
@Tag(name = "Files", description = "文件上传下载")
@RestController
@RequestMapping("file")
public class FileController {

	/** 允许上传的扩展名白名单 */
	private static final Set<String> ALLOWED_EXTS = Set.of(
			"jpg", "jpeg", "png", "gif", "webp", "bmp", "ico",
			"pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "zip");

	private File uploadDir;

	@PostConstruct
	public void init() throws IOException {
		// 统一使用工作目录下的 ./upload，避免 classpath 在 jar 内不可写的问题
		uploadDir = new File("upload");
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		// 规范化路径，供下载时做父目录校验
		uploadDir = uploadDir.getCanonicalFile();
	}

	/**
	 * 上传文件（需要登录）
	 */
	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new EIException("上传文件不能为空");
		}
		String original = StringUtils.defaultString(file.getOriginalFilename());
		int dotIdx = original.lastIndexOf('.');
		if (dotIdx < 0) {
			throw new EIException("文件缺少扩展名");
		}
		String fileExt = original.substring(dotIdx + 1).toLowerCase(Locale.ROOT);
		if (!ALLOWED_EXTS.contains(fileExt)) {
			throw new EIException("不允许上传该类型文件");
		}
		String fileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExt;
		File dest = new File(uploadDir, fileName);
		try {
			file.transferTo(dest.getCanonicalFile());
		} catch (IOException e) {
			throw new EIException("文件保存失败");
		}
		return R.ok().put("file", fileName);
	}

	/**
	 * 下载文件（公开，仅限 upload 目录）
	 */
	@IgnoreAuth
	@RequestMapping("/download/{fileName}")
	public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) {
		try {
			// 拒绝任何路径分隔符与穿越
			if (StringUtils.isBlank(fileName) || fileName.contains("..")
					|| fileName.contains("/") || fileName.contains("\\")) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			File file = new File(uploadDir, fileName).getCanonicalFile();
			if (!file.getPath().startsWith(uploadDir.getPath() + File.separator)
					|| !file.isFile()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);
			return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
