package com.ef.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.ef.model.ServerBlocked;
import com.ef.model.ServerBlocked.ServerBlockedDurations;
import com.ef.persistence.HibernateUtil;

/**
 * Support to queries to the server_blocked table.
 * @author patriciaespada
 *
 */
public class ServerBlockedService {

	public List<ServerBlocked> findBlockedServers(Integer importLogFileId, Date startDate, Date endDate, ServerBlockedDurations duration, Integer threshold) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// TODO: change this native query to na HQL (usgin criteria query)
			NativeQuery<ServerBlocked> query = session.createNativeQuery(
				"SELECT ip as ip, :startDate as start_date, :endDate as end_date, :duration as duration, :threshold as threshold, number_requests as number_requests"
				+ " FROM ("
					+ "SELECT ip, count(*) as number_requests"
					+ " FROM server_access"
					+ " WHERE import_log_file_id = :importLogFileId"
					+ " AND date >= :startDate"
					+ " AND date <= :endDate"
					+ " GROUP BY ip"
				+ ") results"
				+ " WHERE number_requests >= :threshold", ServerBlocked.class);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("duration", duration.name());
			query.setParameter("threshold", threshold);
			query.setParameter("importLogFileId", importLogFileId);

			return query.getResultList();
		}
	}

	public ServerBlocked insert(ServerBlocked serverBlocked) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			session.saveOrUpdate(serverBlocked);
			session.getTransaction().commit();

			return serverBlocked;
		}
	}

}
