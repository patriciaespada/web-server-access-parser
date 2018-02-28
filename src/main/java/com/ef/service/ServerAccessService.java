package com.ef.service;

import java.util.Date;

import org.hibernate.Session;

import com.ef.model.ServerAccess;
import com.ef.persistence.HibernateUtil;

/**
 * Support to queries to the server_access table.
 * @author patriciaespada
 *
 */
public class ServerAccessService {

	public ServerAccess insert(Integer importLogFileId, Date date, String ip, String request, Integer status, String userAgent) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();

			ServerAccess serverAccess = create(importLogFileId, date, ip, request, status, userAgent);

			session.save(serverAccess);
			session.getTransaction().commit();

			return serverAccess;
		}
	}

	public ServerAccess create(Integer importLogFileId, Date date, String ip, String request, Integer status, String userAgent) {
		ServerAccess serverAccess = new ServerAccess();
		serverAccess.setImportLogFileId(importLogFileId);
		serverAccess.setDate(date);
		serverAccess.setIp(ip);
		serverAccess.setRequest(request);
		serverAccess.setStatus(status);
		serverAccess.setUserAgent(userAgent);

		return serverAccess;
	}

}
