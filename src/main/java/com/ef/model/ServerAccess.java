package com.ef.model;

import java.util.Date;

/**
 * Representation of a web server access (line in the log file).
 * @author patriciaespada
 *
 */
public class ServerAccess {

	private Integer id;
	private Integer importLogFileId;
	private Date date;
	private String ip;
	private String request;
	private Integer status;
	private String userAgent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getImportLogFileId() {
		return importLogFileId;
	}

	public void setImportLogFileId(Integer importLogFileId) {
		this.importLogFileId = importLogFileId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return "ServerAccess [id=" + id + ", importLogFileId=" + importLogFileId + ", date=" + date + ", ip=" + ip
				+ ", request=" + request + ", status=" + status + ", userAgent=" + userAgent + "]";
	}

}
