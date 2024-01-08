package services;

import dao.InvoiceDao;
import jakarta.persistence.EntityManager;
import objects.InvoiceMutable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import provider.EntityManagerProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private static final Log log = LogFactory.getLog(CustomerService.class);


    /**
     * @param customerId the customer id for who we are looking up the invoice ids and tender type values.
     * @return the Map<Long, String> which is a map that points the invoice ID to the tender type used.
     */
    public Map<Long, String> getCustomerInvoices(long customerId) {
        EntityManager em = null;
        Map<Long, String> result = new HashMap<>();

        //I wasn't sure what kind of database we are using. The 'invoice_data' column looked like a JSON column, maybe in a NoSql database?
        //My brain struggled to break out how to the invoiceData element. I went with a substring to get the tender type.
        //This param is fragile as it would break if the layout of the element ever changed. It relies on the specific element being spelled out with an ':' and a space at the end.
        //But it does allow me to get the size of this string, including the quotes, then add that to the first element
        //in an 'indexOf' call. So I can easily retrieve the tender type value.
        int typeLength = "\"type\": ".length();
        try {
            //Creates a local entity manager object. I wasn't sure if I needed to do this or if I could assume it was already there.
            //This is what I have done in my own little Java application that is connected to a MySql database.
            //It won't work, because it's looking for a database that doesn't exist. But if the database existed properly, I believe this would work.
            em = EntityManagerProvider.getEntityManager();
            em.getTransaction().begin();

            List<InvoiceMutable> items = getInvoiceMutables(customerId, em);
            for (InvoiceMutable mut : items) {
                String type = getType(mut, typeLength);
                //Add the invoice ID as the key and the type as the value to the map.
                result.put(mut.getInvoiceId(), type);
            }
        } catch (Exception e) {
            log.error("Caught an Exception ", e);
        } finally {
            //Close the em and emf objects.
            if (em != null) {
                EntityManagerProvider.closeEntityManager();
            }
        }
        //I am just returning an empty Map if the user passed in a customerID that matches nothing. I wasn't really sure what else I could do with this.
        return result;
    }

    /**
     * @param mut          The invoice mutable object we are looking at.
     * @param typeLength   The length of "\"type\": " element.
     * @return String The tender type used for this invoice.
     */
    private static String getType(InvoiceMutable mut, int typeLength) {
        String invoiceData = mut.getInvoiceData();

        //this sub string takes the size from above, adds it to the first element found and gets the sub string from there
        //to the next index of the '}'. This again is fragile as it could break if the order of elements ever changed.
        //For example if the time element was to be put on the end, then this check would get the type value and the whole time group.
        //I wasn't sure if I could make this assumption: If the invoice_data param is a Map, then you could just pull the "type" param from the map.
        //You could also have a table or an enum with the accepted return types and do a check for if the value returned is valid. Lots of different options.
        return invoiceData.substring(invoiceData.indexOf("\"type\"") + typeLength, invoiceData.indexOf('}'));
    }


    /**
     * @param customerId the customer id for who we are looking up the invoice ids and tender type values.
     * @param em         the entity manager object being used to run the necessary query.
     * @return the List<InvoiceMutable> The list of all objects for the passed in customerId
     */
    private static List<InvoiceMutable> getInvoiceMutables(long customerId, EntityManager em) {
        //Creates a new DAO object to run the necessary query.
        //I am not sure if it is required to pass in the EntityManager object as a best practice, but it is what I have done at home when working with Hibernate.
        //I imagine the best practice is to wire this up so that you don't need to actually pass in the em object, but am not 100% sure how to do that.
        InvoiceDao dao = new InvoiceDao(em);
        //Calls the method to get the customer data by the id passed in.
        return dao.getCustomerById(customerId);
    }
}
