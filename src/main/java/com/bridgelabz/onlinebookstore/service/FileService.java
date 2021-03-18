package com.bridgelabz.onlinebookstore.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.File;
import com.bridgelabz.onlinebookstore.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	public File saveFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		try {
			File files;
			files = new File(fileName, file.getContentType(), file.getBytes());
			return fileRepository.save(files);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Optional<File> getFile(int fileId) {
		return fileRepository.findById(fileId);
//				.orElseThrow(() - > new RuntimeException("File not found with id " + fileId));
	}

}
