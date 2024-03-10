package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Ride;
import domain.Rider;
import domain.User;
import domain.Driver;
import domain.Eskaera;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}
	/**
	 * {@inheritDoc}
	 */
   @WebMethod
	public boolean emailaEtaPasswordaBadaude(String email,String password,String type){
		dbManager.open();	
		
		 boolean mota=dbManager.emailaEtaPasswordaBadaude(email,password,type);		

		dbManager.close();
		
		return mota;
	}
	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail);		
		dbManager.close();
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	public void diruaAteraRider(float amount, String erabiltzaile){

		dbManager.open();

		dbManager.diruaAteraRider(amount, erabiltzaile);

		dbManager.close();

		}

		public void diruaAteraDriver(float amount, String erabiltzaile){

		dbManager.open();

		dbManager.diruaAteraDriver(amount, erabiltzaile);

		dbManager.close();

		}

		public void diruaSartuRider(float amount, String erabiltzaile){

		dbManager.open();

		dbManager.diruaSartuRider(amount, erabiltzaile);

		dbManager.close();

		}
		public void diruaSartuDriver(float amount, String erabiltzaile){

			dbManager.open();

			dbManager.diruaSartuDriver(amount, erabiltzaile);

			dbManager.close();

			}
		public void bidaiaOnartu(Eskaera eskaera) {
			dbManager.open();

			dbManager.bidaiaOnartu(eskaera);

			dbManager.close();
		}
		
		public void bidaiaEzeztatu(Eskaera eskaera) {
			dbManager.open();

			dbManager.bidaiaEzeztatu(eskaera);

			dbManager.close();
		}
		
		public List<Eskaera> eskaerakSartu (Driver driver){
			dbManager.open();
			
			List<Eskaera> lista=dbManager.eskaerakSartu(driver);
			
			dbManager.close();
			for(Eskaera esk: lista){
			System.out.println(esk.toString());
			}
			return lista;
		}
		public boolean erregistratu(User u) {
			dbManager.open();
			boolean a= dbManager.erregistratu(u);
			dbManager.close();
			return a;
		}
	
		public Driver driverBadago(String id) {
			dbManager.open();
			Driver a=dbManager.driverBadago(id);
			dbManager.close();
			return a;
		}
		public Rider riderBadago(String id) {
			dbManager.open();
			Rider a=dbManager.riderBadago(id);
			dbManager.close();
			return a;
		}
		
		public Ride findRide(String from,String to,String driver,Date date) {
			dbManager.open();
			Ride ride=dbManager.findRide(from,to,driver,date);
			dbManager.close();
			return ride;
		}
		
		public void makeOffer(Ride ride, Rider rider, int seatsSelected,String id) {
			dbManager.open();
			dbManager.makeOffer(ride,rider,seatsSelected,id);
			dbManager.close();
		}
		
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

}

