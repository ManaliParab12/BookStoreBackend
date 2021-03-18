package com.bridgelabz.onlinebookstore.utility;

import lombok.Data;

public @Data class FileResponse {
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;

	public FileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}

}
