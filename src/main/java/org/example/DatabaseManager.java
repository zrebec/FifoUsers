package org.example;

import javax.persistence.*;
import java.util.List;

public class DatabaseManager {

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class DatabaseException extends RuntimeException {
        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InvalidInputException extends RuntimeException {
        public InvalidInputException(String message) {
            super(message);
        }
    }
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("suser-persistence");

    public void addUser(int userId, String userGuid, String userName) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // At first, we must check if user already exists
            if(em.find(SUser.class, userId) != null) {
                throw new InvalidInputException("User with ID " + userId + " already exists");
            }

            // Creating new user
            SUser user = new SUser();
            user.setUserId(userId);
            user.setUserGuid(userGuid);
            user.setUserName(userName);
            em.persist(user);
            em.getTransaction().commit();
        } catch(Exception e) {
            // Rollback transaction if we have a problem to create a user
            em.getTransaction().rollback();
            throw new DatabaseException("Problem with adding user: ", e);
        } finally {
            em.close();
        }
    }

    public SUser getUser(int userId) {
        EntityManager em = emf.createEntityManager();
        try {
            SUser user = em.find(SUser.class, userId);
            if(user == null) {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
            return user;
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
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            SUser user = em.find(SUser.class, userId);
            if(user != null) {
                em.remove(user);
            }

            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DatabaseException("Problem with deleting user: ", e);
        } finally {
            em.close();
        }
    }

    public void deleteAll() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM SUser").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("We cannot delete all users. Reason: " + e);
        } finally {
            em.close();
        }
    }
}
