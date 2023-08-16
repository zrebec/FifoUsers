package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.function.Consumer;

public class DatabaseManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("suser-persistence");

    private void executeInTransaction(Consumer<EntityManager> action) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            action.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void addUser(int userId, String userGuid, String userName) {
        executeInTransaction(em -> {
            SUser user = new SUser();
            user.setUserId(userId);
            user.setUserGuid(userGuid);
            user.setUserName(userName);
            em.persist(user);
        });
    }

    public SUser getUser(int userId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(SUser.class, userId);
        } finally {
            em.close();
        }
    }

    public void printAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SUser> query = em.createQuery("SELECT u FROM SUser u", SUser.class);
            List<SUser> users = query.getResultList();
            for (SUser user : users) {
                System.out.println("ID: " + user.getUserId() + ", GUID: " + user.getUserGuid() + ", Name: " + user.getUserName());
            }
        } finally {
            em.close();
        }
    }

    public void deleteUser(int userId) {
        executeInTransaction(em -> {
            SUser user = em.find(SUser.class, userId);
            if (user != null) {
                em.remove(user);
            }
        });
    }

    public void deleteAll() {
        executeInTransaction(em -> em.createQuery("DELETE FROM SUser").executeUpdate());
    }
}
