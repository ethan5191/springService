package controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import services.CustomerService;

import java.util.Map;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Init binder to prevent fortify findings from happening. Not sure if it is needed, but we use them in my current role, so it feels natural to add it.
    //I don't have a form, so there isn't anything for spring to auto bind, so I don't think its needed or is doing anything here.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(new String[]{"*"});
    }

    //Since this controller is only getting data I am using the @GetMapping. If this was saving data I could use a @Post or @Delete.
    //You could also use the @RequestMapping and give it a method of RequestMethod.GET.
    //I am not sure what the proper URL pattern is on a spring web service call, so I just went with what made sense.
    @GetMapping(value = "/getCustomer/{customerId}")
    public Map<Long, String> getCustomerInvoices(@PathVariable("customerId") long customerId) {
        //Permission check here maybe? Some kind of cert or username password check maybe? The soap services I have created always use a certificate based authentication.
        //Call the autowired service method, it returns the Map<Long, String> that we are expecting to send back, so our controller doesn't need anything else.
        return customerService.getCustomerInvoices(customerId);
    }
}
