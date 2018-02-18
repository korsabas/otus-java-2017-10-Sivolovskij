package ru.podelochki.otus.homework10.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.podelochki.otus.homework10.models.AddressDataSet;
import ru.podelochki.otus.homework10.models.DataSet;
import ru.podelochki.otus.homework10.models.PhoneDataSet;
import ru.podelochki.otus.homework10.models.UsersDataSet;

public class DBServiceHibernateImpl implements DBService {
	private final SessionFactory mysqlFactory;
	
	public DBServiceHibernateImpl() {
		mysqlFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UsersDataSet.class).addAnnotatedClass(PhoneDataSet.class)
				.addAnnotatedClass(AddressDataSet.class).buildSessionFactory();
	}
	@Override
	public <T extends DataSet> T load(long id, Class<T> clazz) {
		Session session = mysqlFactory.openSession();
		session.beginTransaction();
		T result = session.load(clazz, id);
		session.getTransaction().commit();
		return result;
	}
	@Override
	public <T extends DataSet> void save(T entity) {
		Session session = mysqlFactory.openSession();
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();
	}
	@Override
	public void close() {
		mysqlFactory.close();

	}

}
