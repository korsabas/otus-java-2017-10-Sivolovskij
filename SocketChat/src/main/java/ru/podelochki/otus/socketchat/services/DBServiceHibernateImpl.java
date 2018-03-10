package ru.podelochki.otus.socketchat.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import ru.podelochki.otus.socketchat.models.AppUser;
import ru.podelochki.otus.socketchat.models.ChatMessage;

@Service
public class DBServiceHibernateImpl implements DBService {
	private static final String PERSISTENCE_UNIT_NAME = "smschat";
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
	private EntityManager getEntityManager() {
		return this.factory.createEntityManager();
	}
	
	@Override
	public AppUser getAppUserById(int id) {
		EntityManager em = getEntityManager();
		return em.find(AppUser.class, id);
	}
	
	public AppUser getUserByUsername(String username) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AppUser> criteriaQuery = cb.createQuery(AppUser.class);
		Root<AppUser> from = criteriaQuery.from(AppUser.class);
		criteriaQuery.where(cb.equal(from.get("username"), username));
		Query myQuery = em.createQuery(criteriaQuery);
		AppUser user = null;
		try {	
			user = (AppUser) myQuery.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		em.close();
		return user;
	}
	
	@Override
	public List<ChatMessage> getMessagesForUser(AppUser user) {
		String sql = String.format("select * from messages where sender_id = %s or receiver_id = %s order by create_date asc", user.getId(), user.getId());
		EntityManager em = getEntityManager();
		return em.createNativeQuery(sql, ChatMessage.class).setMaxResults(1000).getResultList();
	}
	
	@Override
	public List<ChatMessage> getUnreadedMessagesForUser(AppUser user, AppUser fromUser) {
		String sql = String.format("select * from messages where (sender_id = %s and receiver_id = %s)  and status = 1 order by create_date asc", user.getId(), fromUser.getId());
		EntityManager em = getEntityManager();
		return em.createNativeQuery(sql, ChatMessage.class).setMaxResults(1000).getResultList();
	}

	@Override
	public void saveMessage(ChatMessage message) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		message.setStatus(1);
		message.setCreateDate(new Date());
		em.persist(message);
		em.getTransaction().commit();
		//em.flush();
		em.close();
	}

	@Override
	public void updateMessage(ChatMessage cMessage) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		String sql = String.format("update messages set status = 2 where message_id=%s", cMessage.getId());
		em.createNativeQuery(sql).executeUpdate();
		em.getTransaction().commit();
		//em.flush();
		em.close();
		
	}

	@Override
	public void updateNode(AppUser user) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		String sql = String.format("update users set active_node = '%s' where user_id=%s", user.getActiveNode(), user.getId());
		em.createNativeQuery(sql).executeUpdate();
		em.getTransaction().commit();
		//em.flush();
		em.close();	
	}
	
}
