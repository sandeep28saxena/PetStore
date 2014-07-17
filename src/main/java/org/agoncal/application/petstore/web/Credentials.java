//this class sets the attributes for login and password procedures
package org.agoncal.application.petstore.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
//This annotation means that the bean has been named
@Named

//Specifies that a bean is request scoped.
//
//The request scope is active:
//
//during the service() method of any servlet in the web application, during the doFilter() method of any servlet filter and when the container calls any ServletRequestListener or AsyncListener,
//during any Java EE web service invocation,
//during any remote method invocation of any EJB, during any asynchronous method invocation of any EJB, during any call to an EJB timeout method and during message delivery to any EJB message-driven bean, and
//during any message delivery to a MessageListener for a JMS topic or queue obtained from the Java EE component environment.
//The request context is destroyed:
//
//at the end of the servlet request, after the service() method, all doFilter() methods, and all requestDestroyed() and onComplete() notifications return,
//after the web service invocation completes,
//after the EJB remote method invocation, asynchronous method invocation, timeout or message delivery completes, or
//after the message delivery to the MessageListener completes.
@RequestScoped

//Class for Credentials
public class Credentials {

    // ======================================
    // =             Attributes             =
    // ======================================

//first attribution of  these strings
    private String login;
    private String password;
    private String password2;

    // ======================================
    // =         Getters & setters          =
    // ======================================

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
