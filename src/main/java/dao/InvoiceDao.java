package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import objects.InvoiceMutable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDao {

    private final EntityManager em;

    //Constructor to pass in the EM object, not sure if this is necessary in a real world situation, but it's what I have used in my own projects at home.
    public InvoiceDao(EntityManager em) {
        this.em = em;
    }

    //DAO method to get the list of objects back based on the customer ID passed in.
    public List<InvoiceMutable> getCustomerById(long customerId) {
        String sql = "SELECT t FROM invoice t where t.customer_id = :id";
        TypedQuery<InvoiceMutable> query = em.createQuery(sql, InvoiceMutable.class);
        query.setParameter("id", customerId);
        return query.getResultList();
    }
}
