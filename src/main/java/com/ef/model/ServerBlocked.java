package com.ef.model;

import java.util.Date;

/**
 * Representation of a web server that is blocked.
 * @author patriciaespada
 *
 */
public class ServerBlocked {

	public enum ServerBlockedDurations { HOURLY, DAILY }

	private String ip;
	private Date startDate;
	private Date endDate;
	private ServerBlockedDurations duration;
	private Integer threshold;
	private Integer numberRequests;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ServerBlockedDurations getDuration() {
		return duration;
	}

	public void setDuration(ServerBlockedDurations duration) {
		this.duration = duration;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Integer getNumberRequests() {
		return numberRequests;
	}

	public void setNumberRequests(Integer numberRequests) {
		this.numberRequests = numberRequests;
	}

	@Override
	public String toString() {
		return "ServerBlocked [ip=" + ip + ", startDate=" + startDate + ", endDate=" + endDate + ", duration="
				+ duration + ", threshold=" + threshold + ", numberRequests=" + numberRequests + "]";
	}

}
