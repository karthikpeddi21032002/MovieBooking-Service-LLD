package org.example;

import java.util.List;

public class BookingService {
    private final PaymentStrategy paymentStrategy;

    public BookingService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /**
     * Attempts to book a seat with thread safety and payment logic.
     */
    public void bookseat(Theatre theatre, String screenname, String screenrow, int seatNumber, String userName) {
        List<Screen> screens = theatre.getScreens();
        boolean foundScreen = false;
        for (Screen s : screens) {
            if (s.getName().equals(screenname)) {
                foundScreen = true;
                if (!s.getMap().containsKey(screenrow)) {
                    System.out.println("[BookingService] Row '" + screenrow + "' does not exist in screen '" + screenname + "' for user " + userName);
                    return;
                }
                List<Seat> seats = s.getMap().get(screenrow);
                boolean foundSeat = false;
                for (Seat si : seats) {
                    if (si.getNumber() == seatNumber) {
                        foundSeat = true;
                        synchronized (si) {
                            if (!si.isBooked()) {
                                System.out.println("[BookingService] [" + userName + "] Seat " + seatNumber + " is available. Attempting payment...");
                                boolean paymentSuccess = paymentStrategy.pay(userName, seatNumber);
                                if (paymentSuccess) {
                                    si.setBooked(true);
                                    System.out.println("[BookingService] [" + userName + "] Seat " + seatNumber + " successfully BOOKED in theatre '" + theatre.getName() + "', screen '" + screenname + "', row '" + screenrow + "'.");
                                } else {
                                    System.out.println("[BookingService] [" + userName + "] Payment failed. Seat " + seatNumber + " remains available for others.");
                                }
                            } else {
                                System.out.println("[BookingService] [" + userName + "] Seat " + seatNumber + " is already BOOKED.");
                            }
                        }
                        return;
                    }
                }
                if (!foundSeat) {
                    System.out.println("[BookingService] Seat number '" + seatNumber + "' does not exist in row '" + screenrow + "' for user " + userName);
                }
                return;
            }
        }
        if (!foundScreen) {
            System.out.println("[BookingService] Screen '" + screenname + "' does not exist for user " + userName);
        }
    }
}
