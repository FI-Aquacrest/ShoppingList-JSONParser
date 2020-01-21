package fi.tuni.tamk.objectOrientedProgramming.shoppingListApp;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Creates a memory-based H2 Database and holds method to control it.
 */
public class DatabaseConnection {
    private SessionFactory sessionFactory;

    /**
     * Configures the SessionFactory.
     */
    public DatabaseConnection() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Saves the current list to the database. Overwrites if an item is already on the list.
     */
    public void saveToDatabase() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            for (ShoppingListItem item : ShoppingList.data) {
                tx = session.beginTransaction();
                session.saveOrUpdate(item);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Loads the list currently in the database.
     */
    public void loadFromDatabase() {
        try (Session session = sessionFactory.openSession()) {
            List<ShoppingListItem> items = session.createQuery("from ShoppingListItem",
                    ShoppingListItem.class).list();
            items.forEach((s) -> ShoppingList.data.add(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}