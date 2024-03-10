package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Driver;
import domain.Eskaera;
import domain.Ride;
import domain.Rider;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();

				  System.out.println("File deleted");
				} else {
				  System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized())initializeDB();
		
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try {

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		   
		    //Create drivers 
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez","pass",20,"688855772");
			List<Eskaera> eskaeraBerriak=new Vector<Eskaera>();
			
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga","pass",25,"688855772");
			Driver driver3=new Driver("driver3@gmail.com","Test driver","pass",34,"688855772");
			Rider rider1=new Rider("elezgaraiion@gmail.com","Aitor Fernandez","pass",0,5,"688855772");
			
			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);

			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			
			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);

			
						
			db.persist(driver1);
			db.persist(driver2);
			db.persist(driver3);
			db.persist(rider1);
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	public List<String> getDepartCities(){
			TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			List<String> cities = query.getResultList();
			return cities;
		
	}
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from){
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList(); 
		return arrivingCities;
		
	}
	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail) throws  RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+driverEmail+" date "+date);
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();
			
			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			//next instruction can be obviated
			db.persist(driver); 
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);

		List<Ride> res = new ArrayList<>();	
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class);   
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
	 	 for (Ride ride:rides){
		   res.add(ride);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
	}
	
	public boolean emailaEtaPasswordaBadaude(String email,String password,String type) {
		TypedQuery<Long> query;

        if ("Rider".equals(type)) {
            query = db.createQuery("SELECT COUNT(r) FROM Rider r WHERE r.email = :email", Long.class);
        } else  {
            query = db.createQuery("SELECT COUNT(d) FROM Driver d WHERE d.email = :email", Long.class);
        }

        // Establecer el parámetro de la consulta
        query.setParameter("email", email);

        // Ejecutar la consulta y obtener el resultado
        Long count = query.getSingleResult();

        // Si el recuento es mayor que cero, significa que el email está registrado
        if (count > 0) { // El email está registrado en la base de datos
            TypedQuery<?> passwordQuery;
            if ("Rider".equals(type)) {
                passwordQuery = db.createQuery("SELECT r FROM Rider r WHERE r.email = :email", Rider.class);
            } else  {
                passwordQuery = db.createQuery("SELECT d FROM Driver d WHERE d.email = :email", Driver.class);
            }

            // Establecer el parámetro de la consulta de contraseña
            passwordQuery.setParameter("email", email);

            // Obtener el resultado (el registro correspondiente)
            Object result = passwordQuery.getSingleResult();

            // Verificar si la contraseña coincide
            if (result instanceof Rider) {
                return ((Rider) result).getPassword().equals(password);
            } else if (result instanceof Driver) {
                return ((Driver) result).getPassword().equals(password);
            }
        }
        return false;
	}
	
	public boolean  erregistratu(User u) {
		try {
		db.getTransaction().begin();
		db.persist(u);
		db.getTransaction().commit();
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	public void diruaSartuRider (float amount, String erabiltzaile) {

		db.getTransaction().begin();

		Rider cf=db.find(Rider.class, erabiltzaile);

		cf.setBalance(cf.getBalance()+amount);

		db.getTransaction().commit();

		}
	public void diruaSartuDriver(float amount, String erabiltzaile) {
		db.getTransaction().begin();

		Driver cf=db.find(Driver.class, erabiltzaile);

		cf.setBalance(cf.getBalance()+amount);

		db.getTransaction().commit();

	}
		public void diruaAteraRider (float amount, String erabiltzaile) {

		db.getTransaction().begin();

		Rider cf=db.find(Rider.class, erabiltzaile);

		cf.setBalance(cf.getBalance()-amount);

		db.getTransaction().commit();

		}

		public void diruaAteraDriver (float amount, String erabiltzaile) {

		db.getTransaction().begin();

		Driver cf=db.find(Driver.class, erabiltzaile);

		cf.setBalance(cf.getBalance()-amount); 

		db.getTransaction().commit();

		}

		public void bidaiaOnartu(Eskaera eskaera) {
			db.getTransaction().begin();
			Driver driver=db.find(Driver.class, eskaera.getDriverId());
			Rider rider=db.find(Rider.class, eskaera.getRiderId());
			Ride ride=db.find(Ride.class,driver.rideAurkitu(eskaera.getRideId()));
			if (ride.getnPlaces()>=eskaera.getLekuak()) {
			float dirua=eskaera.getPrezioa();
			driver.setBalance(driver.getBalance()+dirua);
			rider.setFrozen_balance(rider.getFrozen_balance()-dirua);
			eskaera.setLekuak(eskaera.getLekuak()-eskaera.getLekuak());
			}
			Eskaera eskaera1=db.find(Eskaera.class, eskaera.getOfferID());
			ride.setBetMinimum(ride.getnPlaces()-eskaera1.getLekuak());
			db.remove(eskaera1);
			List<Eskaera> lista=driver.getOffers();
			lista.remove(eskaera1);
			driver.setOffers(lista);
			List<Ride>lista2 =rider.getBookings();
			lista2.add(ride);
			rider.setBookings(lista2);
			db.getTransaction().commit();
			}
		
		public void bidaiaEzeztatu(Eskaera eskaera) {
			db.getTransaction().begin();
			Rider rider=db.find(Rider.class, eskaera.getRiderId());
			Driver driver=db.find(Driver.class, eskaera.getDriverId());
			Eskaera eskaera1=db.find(Eskaera.class, eskaera.getOfferID());
			db.remove(eskaera1);
			List<Eskaera> lista=driver.getOffers();
			lista.remove(eskaera1);
			driver.setOffers(lista);
			float dirua=eskaera1.getPrezioa();
			rider.setFrozen_balance(rider.getFrozen_balance()-dirua);
			rider.setBalance(rider.getBalance()+dirua);
			
			db.getTransaction().commit();
		}
		
		public List<Eskaera> eskaerakSartu (Driver driver) {
			db.getTransaction().begin();
			List<Eskaera> eskaerak=driver.getOffers();
			db.getTransaction().commit();
			return eskaerak;
		}
		
		
		public Driver driverBadago(String id) {
			db.getTransaction().begin();
			 Driver a=db.find(Driver.class, id);
			db.getTransaction().commit();
			return a;
		}
		public Rider riderBadago(String id) {
		db.getTransaction().begin();
			 Rider a=db.find(Rider.class, id);
			db.getTransaction().commit();
			return a;
		}

		public Ride findRide(String from,String to,String driverName,Date date) {
			db.getTransaction().begin();
		    Ride ride = null;
		    try {
		        String jpql = "SELECT r FROM Ride r WHERE r.from = :from AND r.to = :to AND r.driver.name = :driverName AND r.date = :date";
		        TypedQuery<Ride> query = db.createQuery(jpql, Ride.class);
		        query.setParameter("from", from);
		        query.setParameter("to", to);
		        query.setParameter("driverName", driverName);
		        // Utiliza el método DATE_TRUNC de ObjectDB para comparar solo la parte de fecha
		        query.setParameter("date", date);
		        
		         ride = query.getSingleResult();
		       
		        
		    } catch (NoResultException e) {
		        // Handle case where no result is found
		        ride = null;
		    } finally {
		        db.getTransaction().commit();
		    }
		    return ride;
		}


		public void makeOffer(Ride ride, Rider rider, int seatsSelected, String offerId) {
			db.getTransaction().begin();
			Eskaera eskaera=new Eskaera(offerId,ride.getDriver().getEmail(),rider.getEmail(),seatsSelected,ride.getPrice(),ride.getRideNumber());
			db.persist(eskaera);
			Driver cf=db.find(Driver.class, ride.getDriver().getEmail());
			List<Eskaera> lista =cf.getOffers();
			lista.add(eskaera);
			cf.setOffers(lista);
			Rider rider1=db.find(Rider.class, rider.getEmail());
			rider1.setBalance(rider1.getBalance()-(ride.getPrice()*seatsSelected));
			rider1.setFrozen_balance(rider1.getFrozen_balance()+(ride.getPrice()*seatsSelected));
			List<Ride>lista1=rider1.getBookings();
			lista1.add(ride);
			
			db.getTransaction().commit();
		}


		
public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
}
