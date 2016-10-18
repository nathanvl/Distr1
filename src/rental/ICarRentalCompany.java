package rental;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ICarRentalCompany extends Remote {
	
	public String getName() throws RemoteException;
	
	public boolean isAvailable(String carTypeName, Date start, Date end) throws RemoteException;
	
	public void cancelReservation(Reservation res) throws RemoteException;
	
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException;
	
	public Quote createQuote(ReservationConstraints constraints, String client) throws RemoteException, ReservationException;
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
}