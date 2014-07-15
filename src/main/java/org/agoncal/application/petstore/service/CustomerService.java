package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.domain.Customer;
import org.agoncal.application.petstore.exception.ValidationException;
import org.agoncal.application.petstore.util.Loggable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */

@Stateless
@Loggable
public class CustomerService implements Serializable {

	// ======================================
	// = Attributes =
	// ======================================

	@Inject
	private EntityManager em;

	// ======================================
	// = Public Methods =
	// ======================================

	public boolean doesLoginAlreadyExist(final String login) {

		// If login is null, throw a validation exception.
		if (login == null)
			throw new ValidationException("Login cannot be null");

		// Login has to be unique
		TypedQuery<Customer> typedQuery = em.createNamedQuery(
				Customer.FIND_BY_LOGIN, Customer.class);
		typedQuery.setParameter("login", login);
		try {
			typedQuery.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	// This will enter a new customer and persist a new instance of customer to
	// the database
	public Customer createCustomer(final Customer customer) {

		if (customer == null)
			throw new ValidationException("Customer object is null");

		em.persist(customer);

		return customer;
	}

	// this method searches for the completed login USERNAME and checks it
	// against the database, parameter is the String that has been entered into
	public Customer findCustomer(final String login) {

		// if they haven't typed anything it will be an invalid login
		if (login == null)
			throw new ValidationException("Invalid login");

		// this creates a query using the entity manager searching customer
		// using a FIND_BY_LOGIN named query
		TypedQuery<Customer> typedQuery = em.createNamedQuery(
				Customer.FIND_BY_LOGIN, Customer.class);
		typedQuery.setParameter("login", login);

		// try returning it
		try {
			return typedQuery.getSingleResult();

			// or just return nothing if the query doesn't return anything
		} catch (NoResultException e) {
			return null;
		}
	}

	// this one searches BOTH USERNAME AND PASSWORD with a view to logging
	// someone in
	public Customer findCustomer(final String login, final String password) {

		// if they don't type in anything it will return INVALID LOGIN
		if (login == null)
			throw new ValidationException("Invalid login");

		// if they don't type in a password it will return INVALID PASSWORD
		if (password == null)
			throw new ValidationException("Invalid password");

		// this uses a names query from FIND_BY_LOGIN_PASSWORD
		TypedQuery<Customer> typedQuery = em.createNamedQuery(
				Customer.FIND_BY_LOGIN_PASSWORD, Customer.class);
		typedQuery.setParameter("login", login);
		typedQuery.setParameter("password", password);

		return typedQuery.getSingleResult();
	}

	// this method will retrieve all the customers in customer class
	public List<Customer> findAllCustomers() {
		TypedQuery<Customer> typedQuery = em.createNamedQuery(
				Customer.FIND_ALL, Customer.class);
		return typedQuery.getResultList();
	}

	public Customer updateCustomer(final Customer customer) {

		// Make sure the object is valid
		if (customer == null)
			throw new ValidationException("Customer object is null");

		// Update the object in the database
		em.merge(customer);
		// returns it
		return customer;
	}

	// this method will delete a customer from the database THIS NEEDS TO BE
	// CHANGED TO AN ADMIN PRIVILAGE.
	public void removeCustomer(final Customer customer) {

		// if there isn't a customer there to start with it will be null
		if (customer == null)
			throw new ValidationException("Customer object is null");

		em.remove(em.merge(customer));
	}
}
