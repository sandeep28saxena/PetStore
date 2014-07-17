package org.agoncal.application.petstore.security;

import org.agoncal.application.petstore.domain.Customer;
import org.agoncal.application.petstore.service.CustomerService;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.util.Map;

/**
 * @author blep
 *         Date: 12/02/12
 *         Time: 11:59
 */

public class SimpleLoginModule implements LoginModule {

    // ======================================
    // =             Attributes             =
    // ======================================

    private CallbackHandler callbackHandler;

    private CustomerService customerService;

    private BeanManager beanManager;

    // ======================================
    // =          Business methods          =
    // ======================================

    //instantiates getCustomerService from credentials class
    private CustomerService getCustomerService() {
        if (customerService != null) {
            return customerService;
            
            //if nothing has been saved to CustomerService it will just return and not get stuck
        }
        try {
        	//Set up a try catch, creating a context. Looks up bean manager for use in the application
            Context context = new InitialContext();
            beanManager = (BeanManager) context.lookup("java:comp/BeanManager");
            //Iterates through the Customer Service class beans.
            Bean<?> bean = beanManager.getBeans(CustomerService.class).iterator().next();
            //Provides operations that are used by the Contextual implementation during instance creation and destruction.
            CreationalContext cc = beanManager.createCreationalContext(bean);
            //Gets the reference of of the bean from the customer service class.
            customerService = (CustomerService) beanManager.getReference(bean, CustomerService.class, cc);

        } catch (NamingException e) {
        	//Naming exception catch
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return customerService;

    }

    //Initilising the call back handler and calling the getCustomerService method when the class initialises.
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> stringMap, Map<String, ?> stringMap1) {
        this.callbackHandler = callbackHandler;
        getCustomerService();
    }

    @Override
    public boolean login() throws LoginException {

    	//Underlying Security services.
        NameCallback nameCallback = new NameCallback("Name : ");
        PasswordCallback passwordCallback = new PasswordCallback("Password : ", false);
        try {
        	//Get the password and name from the input and assign it to a string
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
            String username = nameCallback.getName();
            String password = new String(passwordCallback.getPassword());
            //Clear the forms
            nameCallback.setName("");
            passwordCallback.clearPassword();
            //find the customer with the username and password entered
            Customer customer = customerService.findCustomer(username, password);
            
            //If customer doesn't exist
            if (customer == null) {
            	//Throw an authentication failed exception 
                throw new LoginException("Authentication failed");
            }
            //Otherwise return the customer (Allow them to login)
            return true;
            
        } catch (Exception e) {
        	//catch any exception and print it to the stack.
            e.printStackTrace();
            throw new LoginException(e.getMessage());
        }
    }

    
    //Booleans to commit, abort and logout.
    @Override
    public boolean commit() throws LoginException {
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        return true;
    }
}
