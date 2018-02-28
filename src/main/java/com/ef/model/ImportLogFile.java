package com.ef.model;

import java.util.Date;

/**
 * Representation of a log file imported.
 * @author patriciaespada
 *
 */
public class ImportLogFile {

	public enum ImportLogFileStatus { PROGRESS, FAIL, SUCCESS }

	private Integer id;
	private Date importDate;
	private String fileName;
	private String md5sum;
	private ImportLogFileStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMd5sum() {
		return md5sum;
	}

	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}

	public ImportLogFileStatus getStatus() {
		return status;
	}

	public void setStatus(ImportLogFileStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ImportLogFile [id=" + id + ", importDate=" + importDate + ", fileName=" + fileName + ", md5sum="
				+ md5sum + ", status=" + status + "]";
	}

}
