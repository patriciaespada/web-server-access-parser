package com.ef.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate utility class.
 * @author patriciaespada
 *
 */
public class HibernateUtil {

	private static final Logger LOGGER = Logger.getGlobal();

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private HibernateUtil() { }

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Exception ex) {
			// Make sure you log the exception, as it might be swallowed
			LOGGER.log(Level.SEVERE, "Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}
