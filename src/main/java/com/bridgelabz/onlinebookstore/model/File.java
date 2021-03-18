package com.bridgelabz.onlinebookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "File")
public @Data class File {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_id")
	private int id;

	private String fileName;

	private String fileType;

	@Lob
	private byte[] data;

	public File() {

	}

	public File(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}
}
