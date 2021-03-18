package com.bridgelabz.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bridgelabz.onlinebookstore.model.File;
import com.bridgelabz.onlinebookstore.service.FileService;
import com.bridgelabz.onlinebookstore.utility.FileResponse;

@RestController
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/uploadFile")
	public FileResponse uploadFile(@RequestParam("File") MultipartFile file) {
		File fileName = fileService.saveFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName.getFileName()).toUriString();
		return new FileResponse(fileName.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity downloadFile(@PathVariable int fileId, HttpServletRequest request) {

		File file = fileService.getFile(fileId).get();

		return new ResponseEntity(HttpStatus.OK).ok().contentType(MediaType.parseMediaType(file.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
				.body(new ByteArrayResource(file.getData()));
	}
}
