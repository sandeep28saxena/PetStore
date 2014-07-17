package org.agoncal.application.petstore.security;

import org.agoncal.application.petstore.web.Credentials;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.callback.*;
import java.io.IOException;

/**
 * @author blep Date: 12/02/12 Time: 12:29
 */

@Named
public class SimpleCallbackHandler implements CallbackHandler {

	// ======================================
	// = Attributes =
	// ======================================
	// injects a bean
	@Inject
	@RequestScoped
	private Credentials credentials;

	// ======================================
	// = Business methods =
	// ======================================
	
	
	// a security protocol to get the passwork and login deets from the database
	// with a view to compare them to what
	// has been entered into the field
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (Callback callback : callbacks)
		//if its a name
		{
			if (callback instanceof NameCallback) {
				NameCallback nameCallback = (NameCallback) callback;
				nameCallback.setName(credentials.getLogin());
			
			} 
			//if its a password
			else if (callback instanceof PasswordCallback) {
				PasswordCallback passwordCallback = (PasswordCallback) callback;
				passwordCallback.setPassword(credentials.getPassword()
						.toCharArray());
			} else 
			//or just break
			{
				throw new UnsupportedCallbackException(callback);
			}
		}
	}
}
