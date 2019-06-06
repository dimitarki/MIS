package com.accounting.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {

	private static EntityManagerProvider provider;
	private EntityManagerFactory fac;
	private EntityManagerProvider() {
		fac = Persistence.createEntityManagerFactory("accounting");
	}
	
	public static EntityManagerProvider getInstance() {
		if(provider == null) {
			provider = new EntityManagerProvider();
		}
		return provider;
	}
	
	public EntityManager getEntityManager() {
		return fac.createEntityManager();
	}
}
