package rental;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ICarRentalCompany extends Remote {
			
	public void cancelReservation(Reservation res) throws RemoteException;
	
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException;
	
	public Quote createQuote(ReservationConstraints constraints, String client) throws RemoteException, ReservationException;
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;

	public int getNumberOfReservationsForCarType(String carType) throws RemoteException;
	
	public List<Reservation> getReservationsByRenter(String clientName) throws RemoteException;
}
