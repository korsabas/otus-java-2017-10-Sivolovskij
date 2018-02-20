package ru.podelochki.otus.homework11.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.podelochki.otus.homework11.cache.Cache;
import ru.podelochki.otus.homework11.cache.CacheEntry;
import ru.podelochki.otus.homework11.cache.CacheImpl;
import ru.podelochki.otus.homework11.models.AddressDataSet;
import ru.podelochki.otus.homework11.models.DataSet;
import ru.podelochki.otus.homework11.models.PhoneDataSet;
import ru.podelochki.otus.homework11.models.UsersDataSet;

public class DBServiceHibernateImpl implements DBService {
	private final SessionFactory mysqlFactory;
	private final Cache<Long, DataSet> cache = new CacheImpl<>();
	public DBServiceHibernateImpl() {
		mysqlFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UsersDataSet.class).addAnnotatedClass(PhoneDataSet.class)
				.addAnnotatedClass(AddressDataSet.class).buildSessionFactory();
	}
	@Override
	public <T extends DataSet> T load(long id, Class<T> clazz) {
		DataSet cachedElement = cache.get(id).getValue();
        if (cachedElement != null) return (T) cachedElement;

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
		if (entity.getId() != 0) cache.put(new CacheEntry<>(entity.getId(),entity));
	}
	@Override
	public void close() {
		mysqlFactory.close();

	}
	@Override
    public void printCacheInfo() {
        cache.printInfo();
    }

}
