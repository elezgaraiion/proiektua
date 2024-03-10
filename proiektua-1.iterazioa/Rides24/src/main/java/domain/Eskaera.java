package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Eskaera {
	@Id
	private String offerID;
	private String driverId;
	private String riderId;
	private int zenbakia;
	private int lekuak;
	private float prezioa;
	private int rideId;
	
	public Integer getRideId() {
		return rideId;
	}
	public void setRideId(Integer rideId) {
		this.rideId = rideId;
	}
	public Eskaera(String offerID,String driverId, String riderId, int lekuak, float prezioa,int rideId) {
		super();
		this.offerID=offerID;
		this.driverId = driverId;
		this.riderId = riderId;
		this.lekuak = lekuak;
		this.prezioa = prezioa;
		this.rideId=rideId;
	}
	public String getOfferID() {
		return offerID;
	}
	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getRiderId() {
		return riderId;
	}
	public void setRiderId(String riderId) {
		this.riderId = riderId;
	}
	public int getLekuak() {
		return lekuak;
	}
	public void setLekuak(int lekuak) {
		this.lekuak = lekuak;
	}
	public float getPrezioa() {
		return prezioa;
	}
	public void setPrezioa(float prezioa) {
		this.prezioa = prezioa;
	}
	public int getZenbakia() {
		return zenbakia;
	}
	
	public String toString() {
		return "Eskaera [driverId=" + driverId + ", riderId=" + riderId +  ", lekuak="
				+ lekuak + ", prezioa=" + prezioa + "]";
	}
}
