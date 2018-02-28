package com.ef.service;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.ef.model.ImportLogFile;
import com.ef.model.ImportLogFile.ImportLogFileStatus;
import com.ef.persistence.HibernateUtil;

/**
 * Support to queries to the import_log_file table.
 * @author patriciaespada
 *
 */
public class ImportLogFileService {

	public ImportLogFile findByFileAndCheckSum(String file, String md5CheckSum) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<ImportLogFile> query = session.createQuery(
					"FROM ImportLogFile WHERE file_name = :fileName AND md5sum = :md5sum AND status != 'FAIL'", ImportLogFile.class);
			query.setParameter("fileName", file);
			query.setParameter("md5sum", md5CheckSum);

			return query.uniqueResult();
		}
	}

	public ImportLogFile insert(String file, String md5CheckSum) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();

			ImportLogFile importLogFile = create(new Date(), file, md5CheckSum);

			session.save(importLogFile);
			session.getTransaction().commit();

			return importLogFile;
		}
	}

	public void update(ImportLogFile importLogFile) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			session.update(importLogFile);
			session.getTransaction().commit();
		}
	}

	public ImportLogFile create(Date date, String file, String md5CheckSum) {
		ImportLogFile importLogFile = new ImportLogFile();
		importLogFile.setImportDate(date);
		importLogFile.setFileName(file);
		importLogFile.setMd5sum(md5CheckSum);
		importLogFile.setStatus(ImportLogFileStatus.PROGRESS);

		return importLogFile;
	}

}
