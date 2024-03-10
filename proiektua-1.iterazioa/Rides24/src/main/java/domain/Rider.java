package domain;

import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Rider extends User{
	private float frozen_balance;
	private List<Ride> bookings=new Vector<Ride>();
	public Rider() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Rider(String email, String name, String password, float balance,float frozen_balance,String telephoneNumber) {
		super(email, name, password, balance,telephoneNumber);
		this.frozen_balance=frozen_balance;
		this.bookings=bookings;
	}
	public float getFrozen_balance() {
		return frozen_balance;
	}
	public void setFrozen_balance(float frozen_balance) {
		this.frozen_balance = frozen_balance;
	}
	public List<Ride> getBookings() {
		return bookings;
	}
	public void setBookings(List<Ride> bookings) {
		this.bookings = bookings;
	}
	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
