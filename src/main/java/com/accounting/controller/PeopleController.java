package com.accounting.controller;

import java.util.List;

import javax.persistence.EntityManager;

import com.accounting.model.EntityManagerProvider;
import com.accounting.model.Person;

public class PeopleController {
	private static PeopleController controller;
	private EntityManagerProvider provider;

	private PeopleController() {
		provider = EntityManagerProvider.getInstance();
	}

	public static PeopleController getInstance() {
		if (controller == null) {
			controller = new PeopleController();
		}
		return controller;
	}
	public List<Person> getPeopleContainingName(String familyName) {
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		List<Person> person = mng.createNamedQuery(Person.QUERY_FIND_PERSON_BY_FAMILY_NAME)
				.setParameter("familyName",familyName).getResultList();
		mng.getTransaction().commit();
		return person;
	}
	
	public void createPerson(String firstName, String familyName, String egn) throws PersonException {
		EntityManager mng = provider.getEntityManager();
		try{
			mng.getTransaction().begin();
			List<Person> person = mng.createNamedQuery(Person.QUERY_FIND_PERSON_BY_NAMES)
					.setParameter("firstName", firstName).setParameter("familyName",familyName).getResultList();
			if(person.size() > 0 ) {
				throw new PersonException("Този човек вече съществува!");
			}
			
			Person per = new Person();
			per.setFamilyName(familyName);
			per.setFirstName(firstName);
			per.setEgn(egn);
			mng.persist(per);
			mng.getTransaction().commit();
		} finally {
			if(mng.getTransaction().isActive()) {
				mng.getTransaction().rollback();
			}
		}
	}
}
