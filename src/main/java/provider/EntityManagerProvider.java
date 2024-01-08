package provider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {

    //Creates an entity manager factory for the specified persistence name.
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("OREILLYPOS");
    //Creates an entity manager from the factory.
    private static final EntityManager em = emf.createEntityManager();

    private EntityManagerProvider() {

    }

    //Convenience method to get the EM object, so it can be utilized.
    public static EntityManager getEntityManager() {
        return em;
    }

    //Convenience method to close the emf and em objects.
    public static void closeEntityManager() {
        if (emf.isOpen()) {
            emf.close();
        }
        if (em.isOpen()) {
            em.close();
        }
    }
}
