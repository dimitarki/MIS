package com.accounting.controller;

import java.rmi.server.ExportException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import com.accounting.model.EntityManagerProvider;
import com.accounting.model.Person;
import com.accounting.model.firm.Firm;

public class FirmController {

	private static FirmController controller;
	public Firm loggedUser;
	private EntityManagerProvider provider;

	private FirmController() {
		provider = EntityManagerProvider.getInstance();
	}

	public static FirmController getInstance() {
		if (controller == null) {
			controller = new FirmController();
		}
		return controller;
	}

	public void createProject(String firmName, String firmType, Date start, String owner, String password) throws FirmException {
		EntityManager entityManager = provider.getEntityManager();
		try{
			validateProjectParameters(firmName, firmType, start, owner);			
			entityManager.getTransaction().begin();
			checkFirmExistenceInDB(firmName, entityManager);
			Person leader = getLeaderFromDB(owner, entityManager);
			Firm firm = new Firm();
			firm.setBeginDate(start);
			firm.setFirmName(firmName);
			firm.setFirmType(firmType);
			firm.setLeader(leader);
			firm.setPassword(password);
			entityManager.persist(firm);
			entityManager.getTransaction().commit();
		} catch (RollbackException ex) {
			throw new FirmException(ex);
		} finally {
			if(entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
		}
		
	}

	private Person getLeaderFromDB(String leaderName, EntityManager entityManager) throws FirmException {
		String[] splitedLeader = leaderName.split(",");
		String[] splitedNamesOfLeader = splitedLeader[0].split("\\s+");
		int firstName; 
		int secondName;
		if(splitedNamesOfLeader.length == 3) {
			firstName = 1;
			secondName = 2;
		} else {
			firstName = 0;
			secondName = 1;
		}

		List<Person> person = entityManager.createNamedQuery(Person.QUERY_FIND_PERSON_BY_NAMES)
				.setParameter("firstName", splitedNamesOfLeader[firstName]).setParameter("familyName",splitedNamesOfLeader[secondName]).getResultList();
		if(person.isEmpty()) {
			throw new FirmException("Такъв човек нe съществува!");
		}
		return person.get(0);
	}


	private void checkFirmExistenceInDB(String firmName, EntityManager entityManager) throws FirmException {
		Firm existingFirm = entityManager.find(Firm.class, firmName);
		if(existingFirm != null) {
			throw new FirmException("Фирмата вече съществува !!!");
		}
	}
	
	public void loginToFirm(String firmName, String password) throws FirmException {
		EntityManager entityManager = provider.getEntityManager();
		Firm existingFirm = entityManager.find(Firm.class, firmName);
		if(existingFirm == null) {
			throw new FirmException("Фирмата не съществува !!!");
		} else {
			System.out.println(existingFirm.getPassword());
			System.out.println(password);
			if(existingFirm.getPassword().equals(password)) {
				loggedUser = existingFirm;
			} else {
				throw new FirmException("Грешно потребителско име или парола !!");
			}
		}
	}

	private void validateProjectParameters(String firmName, String firmType, Date start, String owner) throws FirmException {
		if(firmName == null || firmName.equals("")) {
			throw new FirmException("Полето име на фирма е задължително!");
		}
		if(firmType == null || firmType.equals("")) {
			throw new FirmException("Полето тип на фирма е задължително!");
		}
		if(start == null) {
			throw new FirmException("Полето дата е задължително!");
		}
		if(owner == null || owner.equals("")) {
			throw new FirmException("Полето собственик е задължително!");
		}
	}
	
	public List<Firm> getFirmBetweenDates(Date from, Date to) {
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		List<Firm> firms = mng.createNamedQuery(Firm.QUERY_FIND_FIRM_BETWEEN_DATES)
				.setParameter("startDate",from).setParameter("endDate",to).getResultList();
		mng.getTransaction().commit();
		return firms;
	}
	
	public void deleteFirm(String firmName) {
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		Firm firm = mng.find(Firm.class, firmName);
		mng.remove(firm);
		mng.getTransaction().commit();
	}
	
	public Firm getFirmByName(String firmName) {
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		Firm firm = mng.find(Firm.class, firmName);
		mng.getTransaction().commit();
		return firm;
	}
	
	public List<Firm> getAll() {
		EntityManager mng = provider.getEntityManager();
		mng.getTransaction().begin();
		List<Firm> firm = mng.createNamedQuery(Firm.QUERY_FIND_FIRM_ALL).getResultList();
		firm.sort((c, k) -> {
			if(c.getFirmName().charAt(0) > k.getFirmName().charAt(0)) {
				return 1;
			} else if (c.getFirmName().charAt(0) == k.getFirmName().charAt(0)) {
				return 0;
			} else {
				return -1;
			}});
		mng.getTransaction().commit();
		return firm;
	}
}
