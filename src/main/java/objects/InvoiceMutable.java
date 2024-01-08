package objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Object is mapped using Hibernate mappings. I wasn't sure what mapping was used, so just went with Hibernate.
//I don't use Hibernate in my real job, just have experience working with it at home on my own time.
@Entity
@Table(name = "invoice")
public class InvoiceMutable {

    //Assumption is that the customer_id and invoice_id columns are a compound primary key on this table, so they both got the @Id annotation.
    @Id
    @Column(name = "customer_id")
    private long customerId;

    @Id
    @Column(name = "invoice_id")
    private long invoiceId;

    @Column(name = "invoice_data")
    private String invoiceData;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(String invoiceData) {
        this.invoiceData = invoiceData;
    }
}
