package com.accounting.model.firm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.accounting.model.Person;

/**
 * Entity implementation class for Entity: Firm
 *
 */
@Entity
@Table(name="FIRMS")
@NamedQuery(name = Firm.QUERY_FIND_FIRM_BETWEEN_DATES, query = "SELECT firm FROM Firm firm where firm.beginDate BETWEEN :startDate AND :endDate")
@NamedQuery(name = Firm.QUERY_FIND_FIRM_FOR_MONTH, query = "SELECT FUNCTION('month', firm.beginDate), count(firm) FROM Firm firm where FUNCTION('year', firm.beginDate) = :year GROUP by FUNCTION('month', firm.beginDate)")
@NamedQuery(name = Firm.QUERY_FIND_FIRM_FOR_YEARS, query = "SELECT FUNCTION('year', firm.beginDate), count(firm) FROM Firm firm where firm.beginDate BETWEEN :startDate AND :endDate GROUP by FUNCTION('year', firm.beginDate)")
@NamedQuery(name = Firm.QUERY_FIND_FIRM_ALL, query="SELECT e FROM Firm e")
@Cacheable(false)
public class Firm implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String QUERY_FIND_FIRM_FOR_MONTH = "Firm.findFirmForMonths";
	public static final String QUERY_FIND_FIRM_BETWEEN_DATES = "Firm.findFirmBetweenDates";
	public static final String QUERY_FIND_FIRM_FOR_YEARS = "Firm.findFirmForYears";
	public static final String QUERY_FIND_FIRM_ALL = "Firm.findAll";
	@Id
	@Column(name="FIRM_NAME", unique=true)
	@NotNull
	private String firmName;
	
	@Column(name="FIRM_TYPE")
	@NotNull
	private String firmType;
	
	@Column(name="FIRM_PASSWORD")
	@NotNull
	private String password;
	
	@Column(name="BEGIN_DATE")
	@NotNull
	private Date beginDate;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "OWNER_ID")
	@NotNull
	private Person owner;
	
	@OneToMany(mappedBy = "firm",cascade = CascadeType.PERSIST)
	@NotNull
	private List<Expense> expenses = new ArrayList<>();
	
	
	public Firm() {
		super();
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getFirmType() {
		return firmType;
	}

	public void setFirmType(String firmType) {
		this.firmType = firmType;
	}
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Person getLeader() {
		return owner;
	}

	public void setLeader(Person leader) {
		this.owner = leader;
	}

	@Override
	public String toString() {
		return firmName;
	}
}
