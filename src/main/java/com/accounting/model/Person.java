package com.accounting.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Person
 *
 */
@Entity
@Table (name = "PEOPLE", uniqueConstraints = {@UniqueConstraint(columnNames = {"FIRST_NAME", "FAMILY_NAME"})})
@Cacheable(false)
@NamedQuery(name = Person.QUERY_FIND_PERSON_BY_NAMES, query = "SELECT per FROM Person per where per.firstName = :firstName and per.familyName = :familyName")
@NamedQuery(name = Person.QUERY_FIND_PERSON_BY_FAMILY_NAME, query = "SELECT per FROM Person per where per.familyName LIKE CONCAT('%',:familyName,'%')")
public class Person implements Serializable {
	
	public static final String QUERY_FIND_PERSON_BY_NAMES = "Person.findPersonByName";
	public static final String QUERY_FIND_PERSON_BY_FAMILY_NAME = "Person.findPersonByFamilyName";	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="FIRST_NAME")
	@NotNull
	private String firstName;
	
	@Column(name="FAMILY_NAME")
	@NotNull
	private String familyName;
	
	@Column(name="EGN", unique = true)
	@NotNull
	private String egn;

	public Person() {
		super();
	}

	public Person(String firstName, String familyName, String egn) {
		super();
		this.firstName = firstName;
		this.familyName = familyName;
		this.egn = egn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getEgn() {
		return egn;
	}

	public void setEgn(String egn) {
		this.egn = egn;
	}
	
}
