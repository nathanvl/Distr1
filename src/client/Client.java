package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;

public class Client extends AbstractTestBooking {
	
	/********
	 * MAIN *
	 ********/
	
	public static void main(String[] args) throws Exception {
		Thread.sleep(1000); //Sleep to be sure registry is online.
		System.setSecurityManager(null);
		String carRentalCompanyName = "Hertz";
		
		// An example reservation scenario on car rental company 'Hertz' would be...
		Client client = new Client("simpleTrips", carRentalCompanyName);
		client.run();
	}
	
	/***************
	 * CONSTRUCTOR *
	 ***************/
	
	protected ICarRentalCompany crc;
	
	public Client(String scriptFile, String carRentalCompanyName) {
		super(scriptFile);
		
		Registry registry;
		try {
			//Try to locate the registry
			registry = LocateRegistry.getRegistry("localhost", 1099);
			crc = (ICarRentalCompany) registry.lookup(carRentalCompanyName);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check which car types are available in the given period
	 * and print this list of car types.
	 *
	 * @param 	start
	 * 			start time of the period
	 * @param 	end
	 * 			end time of the period
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected void checkForAvailableCarTypes(Date start, Date end) throws Exception {
		Set<CarType> cars = crc.getAvailableCarTypes(start, end);
		//Print every car type on a separate line 
		for (CarType type: cars) {
			System.out.println(type);
		}
	}

	/**
	 * Retrieve a quote for a given car type (tentative reservation).
	 * 
	 * @param	clientName 
	 * 			name of the client 
	 * @param 	start 
	 * 			start time for the quote
	 * @param 	end 
	 * 			end time for the quote
	 * @param 	carType 
	 * 			type of car to be reserved
	 * @param 	region
	 * 			region in which car must be available
	 * @return	the newly created quote
	 *  
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Quote createQuote(String clientName, Date start, Date end,
			String carType, String region) throws Exception {
		//First create reservation constraints then create quote.
		ReservationConstraints con = new ReservationConstraints(start, end, carType, region);
		Quote q = crc.createQuote(con, clientName);
		return q;
	}

	/**
	 * Confirm the given quote to receive a final reservation of a car.
	 * 
	 * @param 	quote 
	 * 			the quote to be confirmed
	 * @return	the final reservation of a car
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Reservation confirmQuote(Quote quote) throws Exception {
		return crc.confirmQuote(quote);
	}
	
	/**
	 * Get all reservations made by the given client.
	 *
	 * @param 	clientName
	 * 			name of the client
	 * @return	the list of reservations of the given client
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected List<Reservation> getReservationsByRenter(String clientName) throws Exception {
		List<Reservation> reservations = crc.getReservationsByRenter(clientName);
		//Also print every reservation
		for(int i = 0; i < reservations.size(); i++) {
			System.out.println(reservations.get(i));
		}
		return reservations;
	}

	/**
	 * Get the number of reservations for a particular car type.
	 * 
	 * @param 	carType 
	 * 			name of the car type
	 * @return 	number of reservations for the given car type
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected int getNumberOfReservationsForCarType(String carType) throws Exception {
		return crc.getNumberOfReservationsForCarType(carType);
	}
}