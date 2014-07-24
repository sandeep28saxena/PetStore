package org.agoncal.application.petstore.web;

import org.agoncal.application.petstore.domain.Customer;
import org.agoncal.application.petstore.service.CustomerService;
import org.agoncal.application.petstore.util.Loggable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Named
@SessionScoped
@Loggable
@CatchException
public class AccountController extends Controller implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private CustomerService customerService;

    @Inject
    private Credentials credentials;

    @Inject
    private Conversation conversation;

    @Produces
    @LoggedIn
    private Customer loggedinCustomer;

    @Inject
    @SessionScoped
    private transient LoginContext loginContext;

    // ======================================
    // =              Public Methods        =
    // ======================================
    public boolean isNumeric(String string) {  
    	 	    return string.matches("[-+]?\\d*\\.?\\d+");  
    	 	} 
    	 //	End of isNumeric
    	 
    public String doLogin() throws LoginException {
        if ("".equals(credentials.getLogin())) {
            addWarningMessage("id_filled");
            return null;
        }
        if ("".equals(credentials.getPassword())) {
            addWarningMessage("pwd_filled");
            return null;
        }

        loginContext.login();
        loggedinCustomer = customerService.findCustomer(credentials.getLogin());

        
        return "main.faces";
    }

    public String doCreateNewAccount() {

        // Login has to be unique
        if (customerService.doesLoginAlreadyExist(credentials.getLogin())) {
            addWarningMessage("login_exists");
            return null;
        }

        // Id and password must be filled
        if ("".equals(credentials.getLogin()) || "".equals(credentials.getPassword()) || "".equals(credentials.getPassword2())) {
            addWarningMessage("id_pwd_filled");
            return null;
        } else if (!credentials.getPassword().equals(credentials.getPassword2())) {
            addWarningMessage("both_pwd_same");
            return null;
        }

        // Login and password are ok
        loggedinCustomer = new Customer();
        loggedinCustomer.setLogin(credentials.getLogin());
        loggedinCustomer.setPassword(credentials.getPassword());
        return "createaccount.faces";
    }
    
    /**
     * This function will create customer account
     * @return string.
     */
    public String doCreateCustomer() {
    	
    	Date dateString = loggedinCustomer.getDateOfBirth();
  		Date today = new Date();
  		
  		String customerFirstName = loggedinCustomer.getFirstname();
  		String customerLastName = loggedinCustomer.getLastname();
  		
	//		Error catching ArrayList
			ArrayList<String> errors = new ArrayList<String>();

		if (dateString == null)
	    {
	       	errors.add("empty_date");
	    }
		else
		{
			//Checks if DOB is set to the future date
			if (dateString.after(today))
			{
				errors.add ("future_date");
			}

		}
	//	When dob field is not empty.  	


		if(isNumeric(customerFirstName)){

				errors.add("invalidFirstName");

			}

		if(isNumeric(customerLastName)){

				errors.add("invalidLastName");

			}

		String customerCountry = loggedinCustomer.getHomeAddress().getCountry();

		if(isNumeric(customerCountry)){

			errors.add("invalidCountry");

		}

		String customerZipCode = loggedinCustomer.getHomeAddress().getZipcode();

		if(isNumeric(customerZipCode)){

			errors.add("invalidZipCode");

		}
		else
		{
			int zipLength = customerZipCode.length();

			if(zipLength <6 || zipLength >8){

				errors.add("invalidPostCodeLength");

			}
		}

		String customerCity = loggedinCustomer.getHomeAddress().getCity();

		if(isNumeric(customerCity)){

			errors.add("invalidCity");

		}
		else
		{
			int cityChar = customerCity.length();
			if(cityChar <4 || cityChar > 25){
				errors.add("invalidCityLength");

			}

		}

	//	Checking Errors list.
		if(errors.isEmpty()){

			//updates age
	        loggedinCustomer.calculateAge();
	        //If everything is ok, updates the account information
	        loggedinCustomer = customerService.updateCustomer(loggedinCustomer);			
		}
		else
		{
	//		Now printing errors.
			for(int i =0; i < errors.size();){

				addWarningMessage(errors.get(i));
				i++;
			}
			return null;

		}
	//	When we do have errors.
		
		return "main.faces";
		
    }
//    End of doCreateCustomer.
 


    public String doLogout() {
        loggedinCustomer = null;
        // Stop conversation
        if (!conversation.isTransient()) {
            conversation.end();
        }
        addInformationMessage("been_loggedout");
        return "main.faces";
    }
//    End of doLogout.
    
    
    /**
     * this function will update account.
     * @return string.
     */
    public String doUpdateAccount() {
    	
    	Date dateString = loggedinCustomer.getDateOfBirth();
  		Date today = new Date();
  		
  		String customerFirstName = loggedinCustomer.getFirstname();
  		String customerLastName = loggedinCustomer.getLastname();
  		
	//		Error catching ArrayList
			ArrayList<String> errors = new ArrayList<String>();

		if (dateString == null)
	    {
	       	errors.add("empty_date");
	    }
		else
		{
			//Checks if DOB is set to the future date
			if (dateString.after(today))
			{
				errors.add ("future_date");
			}

		}
	//	When dob field is not empty.  	


		if(isNumeric(customerFirstName)){

				errors.add("invalidFirstName");

			}

		if(isNumeric(customerLastName)){

				errors.add("invalidLastName");

			}

		String customerCountry = loggedinCustomer.getHomeAddress().getCountry();

		if(isNumeric(customerCountry)){

			errors.add("invalidCountry");

		}

		String customerZipCode = loggedinCustomer.getHomeAddress().getZipcode();

		if(isNumeric(customerZipCode)){

			errors.add("invalidZipCode");

		}
		else
		{
			int zipLength = customerZipCode.length();

			if(zipLength <6 || zipLength >8){

				errors.add("invalidPostCodeLength");

			}
		}

		String customerCity = loggedinCustomer.getHomeAddress().getCity();

		if(isNumeric(customerCity)){

			errors.add("invalidCity");

		}
		else
		{
			int cityChar = customerCity.length();
			if(cityChar <4 || cityChar > 25){
				errors.add("invalidCityLength");

			}

		}

	//	Checking Errors list.
		if(errors.isEmpty()){

			//updates age
	        loggedinCustomer.calculateAge();
	        //If everything is ok, updates the account information
	        loggedinCustomer = customerService.updateCustomer(loggedinCustomer);			
		}
		else
		{
	//		Now printing errors.
			for(int i =0; i < errors.size();){

				addWarningMessage(errors.get(i));
				i++;
			}
			return null;

		}
	//	When we do have errors.


    return "main.faces";
    
}
//    End of doUpdateAccount.
    
    /**
     * This function will reset customer when customer enter invalid data.
     */
    public void resetCustomer(){
        	
    	Customer customer = customerService.findCustomerByID(loggedinCustomer.getId());
    	loggedinCustomer.setFirstname(customer.getFirstname());
    }
//        End of resetCustomer.

    public boolean isLoggedIn() {
        return loggedinCustomer != null;
    }

    public Customer getLoggedinCustomer() {
        return loggedinCustomer;
    }

    public void setLoggedinCustomer(Customer loggedinCustomer) {
        this.loggedinCustomer = loggedinCustomer;
    }
}
