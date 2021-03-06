package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the obligations database table.
 * 
 */
@Entity
@Table(name="obligations")
@NamedQuery(name="Obligation.findAll", query="SELECT o FROM Obligation o")
public class Obligation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name = "DEADLINE")
	 Timestamp deadline;

	private double debt;

	@Lob
	private String description;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="obligation")
	private List<Payment> payments;

	public Obligation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeadline() {
		 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 return dateFormat.format(this.deadline);
		 }
/*
	 public void setDeadline(String inputDeadline) {
		  String[] dateComponents=inputDeadline.split("[- :]");
		  int year=Integer.valueOf(dateComponents[0]);
		  DecimalFormat df = new DecimalFormat("00");
		  int month=Integer.valueOf(df.format(Integer.parseInt(dateComponents[1])))-1;  
		  int date=Integer.valueOf(dateComponents[2]);
		  int hours=Integer.valueOf(dateComponents[3]);
		  int minute=Integer.valueOf(dateComponents[4]);
		  Calendar calendar=Calendar.getInstance();
		  calendar.set(year, month, date, hours, minute);
		  Date dateNow=calendar.getTime();
		  this.deadline=new Timestamp(dateNow.getTime());
		 }
*/
	public void setDeadline(String inputDeadline) {
		  DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		  try {
		   Date dateNow = dateFormat.parse(inputDeadline);
		    this.deadline=new Timestamp(dateNow.getTime());
		  } catch (ParseException e) {
		   System.out.println("An error has occured..problem with parsing.");
		   e.printStackTrace();
		  }
		 }
	
	
	public double getDebt() {
		return this.debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setObligation(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setObligation(null);

		return payment;
	}

}