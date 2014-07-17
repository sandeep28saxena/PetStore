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
import java.util.Date;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */


@Named
@SessionScoped
@Loggable
@CatchException
public class AccountController extends Controller implements Serializable {


	// ======================================
	// = Attributes =
	// ======================================

	// this instance of customer service can be injected into a bean
	@Inject
	private CustomerService customerService;

	// this instance of credentials can be injected into a bean
	@Inject
	private Credentials credentials;

	// this instance of conversation can be injected into a bean
	@Inject
	private Conversation conversation;

	// this instance of loggedin customer produces an object or field and that
	// stays Loggedin
	@Produces
	@LoggedIn
	private Customer loggedinCustomer;

	// this instance of logincontext can be injected into a bean and is only
	// used by the user in that session
	@Inject
	@SessionScoped
	private transient LoginContext loginContext;

	// ======================================
	// = Public Methods =
	// ======================================

	// this method defines what to do if nothing entered for password OR
	// username
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

		// Both Id and password must be filled
		if ("".equals(credentials.getLogin())
				|| "".equals(credentials.getPassword())
				|| "".equals(credentials.getPassword2())) {
			addWarningMessage("id_pwd_filled");
			return null;
		} else if (!credentials.getPassword()
				.equals(credentials.getPassword2())) {
			addWarningMessage("both_pwd_same");
			return null;
		}

		// SET THE LOGIN DETAILS TO CREDENTIALS FOR THE SESSION
		loggedinCustomer = new Customer();
		loggedinCustomer.setLogin(credentials.getLogin());
		loggedinCustomer.setPassword(credentials.getPassword());

		return "createaccount.faces";
	}
	
	
	/**
	 * this function will check whether a given string is numeric or not.
	 * @return boolean
	 * @author jahidhaque
	 */
	public boolean isNumeric(String string) {  
	    return string.matches("[-+]?\\d*\\.?\\d+");  
	} 
//	End of isNumeric.

	// uses loggedin customer to create an instance of create customer in
	// customer service
	public String doCreateCustomer() {
		Date dateString = loggedinCustomer.getDateOfBirth();
		Date today = new Date();
		String lastName = loggedinCustomer.getLastname();
		String customerFistName = loggedinCustomer.getFirstname();
		//DOB input is empty
        if (dateString == null)
        {
        	addWarningMessage ("empty_date");
        	//breaks the update action
        	return null;
        }
		//Checks if DOB is set to the future date
		if (dateString.after(today))
		{
			addWarningMessage ("future_date");
        	return null;
		}
		
		if(isNumeric(customerFistName)){
			
			addWarningMessage ("invalidFirstName");
			return null;
		}
		
		if(isNumeric(lastName)){
			
			addWarningMessage ("invalidLastName");
			return null;
		}
		
		//updates age
        loggedinCustomer.calculateAge();
        //If everything is ok, updates the account information
		loggedinCustomer = customerService.createCustomer(loggedinCustomer);
		return "main.faces";
	}

	// this is a logout procedure to end conversation
	public String doLogout() {
		loggedinCustomer = null;
		// Stop conversation
		if (!conversation.isTransient()) {
			conversation.end();
		}
		addInformationMessage("been_loggedout");
		return "main.faces";
	}

	// Inisiates update account and when it is done prints account updated
	// Accesses customerService class and updateCustomer method, passes the
	// loggedinCustomer variable
	public String doUpdateAccount() {
		Date dateString = loggedinCustomer.getDateOfBirth();
		Date today = new Date();
		String customerFistName = loggedinCustomer.getFirstname();
		String lastName = loggedinCustomer.getLastname();
		//DOB input is empty
        if (dateString == null)
        {
        	addWarningMessage ("empty_date");
        	//breaks the update action
        	return null;
        }
      //Checks if DOB is set to the future date
		if (dateString.after(today))
		{
			addWarningMessage ("future_date");
        	return null;
		}
		
		if(isNumeric(customerFistName)){
			addWarningMessage ("invalidFirstName");
			return null;
		}
		
		if(isNumeric(lastName)){
			addWarningMessage ("invalidLastName");
			return null;
		}
		
		//updates age
        loggedinCustomer.calculateAge();
        //If everything is ok, updates the account information
		loggedinCustomer = customerService.updateCustomer(loggedinCustomer);
		addInformationMessage("account_updated");
		return "showaccount.faces";
	}

	public boolean isLoggedIn() {
		return loggedinCustomer != null;
	}

	// getters and setters for LoggedinCustomer
	public Customer getLoggedinCustomer() {
		return loggedinCustomer;
	}

	public void setLoggedinCustomer(Customer loggedinCustomer) {
		this.loggedinCustomer = loggedinCustomer;
	}}
