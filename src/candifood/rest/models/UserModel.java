package com.package.rest.models;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.package.rest.beans.User;
import com.package.rest.listeners.EMF;
import com.google.appengine.api.datastore.Key;

@Component
public class UserModel {
	private static final Logger logger = Logger.getLogger(UserModel.class.getName());

	public boolean add(User user){
		logger.info("UserModel|add|UserKey:"+user.getKey());
		logger.info("UserModel|add|RoleName:"+user.getRoles());
		logger.info("UserModel|add|UserRoleKey:"+user.getKey());
		boolean returnValue = false;
		User duplicateUser = null;
		EntityManager em = EMF.getInstance().get().createEntityManager();
		if(user.getKey()!=null){
			logger.info("User key passed so its an existing item. updating it"+user.getKey());
			user.setKey(user.getKey());
		} else {
			duplicateUser =  this.checkDuplicateUser(user);
		}
		if(duplicateUser==null){
			try {
				em.getTransaction().begin();
				em.persist(user);
				//em.flush();
				returnValue = true;
				em.getTransaction().commit();
			} catch (Exception e) {
				logger.info("exception while creting items daatastore");
				e.printStackTrace();
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
			    }
			}
			em.close();			
		} else{
			logger.info("add|The item is already available with key:"+duplicateUser.getKey());
		}
		return returnValue;
	}
	public User candiLoginCheck(User user) {
		User verifiedUser =  null; 
		EntityManager em = EMF.getInstance().get().createEntityManager();
		try {
			Query query = em.createQuery("SELECT u FROM Users u WHERE u.email=?1 and u.password = ?2");
			verifiedUser =  (User)query.setParameter(1, user.getEmail()).setParameter(2, user.getPassword()).getSingleResult();
			logger.info("User "+verifiedUser.getName()+" is available and active");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return verifiedUser;
	}

	@SuppressWarnings("unchecked")
	public List<User> all(String pageno, String noOfRecord){
		List<User> users = null;
		EntityManager em = EMF.getInstance().get().createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Users u");
			users = (List<User>)q.setFirstResult(Integer.parseInt(pageno)).setMaxResults(Integer.parseInt(noOfRecord)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return users;
	}
	
	public User findUserByEmail(String email){
		User users = null;
		EntityManager em = EMF.getInstance().get().createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Users u where u.email = :email");
			q.setParameter("email", email);
			users = (User)q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		logger.info("findUserByEmail|email:"+email+"|returndata.name:"+users.getName()+"|returndata.type:"+users.getType());
		return users;
	}

	public boolean delete(Key key) {
		boolean returnValue = false;
		EntityManager em = EMF.getInstance().get().createEntityManager();
		try {
			em.getTransaction().begin();
			User user = em.find(User.class, key);
			em.remove(user);
			em.flush();
			em.getTransaction().commit();
			returnValue = true;
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			em.close();
		}
		logger.info("item deleted with key:"+key);
		return returnValue;
	}
	
	public User checkDuplicateUser(User user) {
		User verifiedUser =  null; 
		EntityManager em = EMF.getInstance().get().createEntityManager();
		try {
			Query query = em.createQuery("SELECT u FROM Users u WHERE u.email=?1");
			verifiedUser =  (User)query.setParameter(1, user.getEmail()).getSingleResult();
			logger.info("email found "+verifiedUser.getEmail()+" is available and active");
		} catch(NoResultException nre){
			logger.info("The user with name: "+user.getName()+" does not exist");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return verifiedUser;
	}
	
}
