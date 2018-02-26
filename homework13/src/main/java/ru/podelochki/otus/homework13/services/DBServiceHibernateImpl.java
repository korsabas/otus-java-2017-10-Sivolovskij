package ru.podelochki.otus.homework13.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import ru.podelochki.otus.homework13.cache.Cache;
import ru.podelochki.otus.homework13.cache.CacheEntry;
import ru.podelochki.otus.homework13.cache.CacheImpl;
import ru.podelochki.otus.homework13.models.AddressDataSet;
import ru.podelochki.otus.homework13.models.DataSet;
import ru.podelochki.otus.homework13.models.PhoneDataSet;
import ru.podelochki.otus.homework13.models.UsersDataSet;

@Service
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
		session.close();
		return result;
	}
	@Override
	public <T extends DataSet> void save(T entity) {
		Session session = mysqlFactory.openSession();
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();
		if (entity.getId() != 0) cache.put(new CacheEntry<>(entity.getId(),entity));
		session.close();
	}
	@Override
	public void close() {
		mysqlFactory.close();

	}
	@Override
    public String printCacheInfo() {
        return cache.printInfo();
    }
	@Override
	public int getHitCount() {
		return cache.getHitCount();
	}
	@Override
	public int getMissCount() {
		return cache.getMissCount();
	}

}
