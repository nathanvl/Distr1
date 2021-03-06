package rental;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Car {

    private int id;
    private CarType type;
    private List<Reservation> reservations;

    /***************
     * CONSTRUCTOR *
     ***************/
    
    public Car(int uid, CarType type) {
    	this.id = uid;
        this.type = type;
        this.reservations = new ArrayList<Reservation>();
    }

    /******
     * ID *
     ******/
    
    public int getId() {
    	return id;
    }
    
    /************
     * CAR TYPE *
     ************/
    
    public CarType getType() {
        return type;
    }

    /****************
     * RESERVATIONS *
     ****************/

    public boolean isAvailable(Date start, Date end) {
        if(!start.before(end))
            throw new IllegalArgumentException("Illegal given period");

        for(Reservation reservation : reservations) {
            if(reservation.getEndDate().before(start) || reservation.getStartDate().after(end))
                continue;
            return false;
        }
        return true;
    }
    
    public void addReservation(Reservation res) {
        reservations.add(res);
    }
    
    public List<Reservation> reservationsBy(String clientName){
    	List<Reservation> clientReservations = new ArrayList<Reservation>();
    	for (Reservation reservation : reservations){
    		if(reservation.getCarRenter() == clientName){
    			clientReservations.add(reservation);
    		}
    	}
    	return clientReservations;
    }
    
    
    public void removeReservation(Reservation reservation) {
        // equals-method for Reservation is required!
        reservations.remove(reservation);
    }
    
    public int getNumberOfReservations() {
    	return reservations.size();
    }
}