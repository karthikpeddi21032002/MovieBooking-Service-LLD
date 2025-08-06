package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Seat> seats1 = new ArrayList<>();
        seats1.add(new Seat(1, false));
        seats1.add(new Seat(2, false));
        seats1.add(new Seat(3, true));
        seats1.add(new Seat(4, false));
        seats1.add(new Seat(5, true));

        ArrayList<Seat> seats2 = new ArrayList<>();
        seats2.add(new Seat(1, true));
        seats2.add(new Seat(2, false));
        seats2.add(new Seat(3, false));
        seats2.add(new Seat(4, false));
        seats2.add(new Seat(5, true));

        HashMap<String, List<Seat>> map1 = new HashMap<>();
        map1.put("A", seats1);
        map1.put("B", seats2);
        map1.put("C", seats1);
        map1.put("D", seats2);

        HashMap<String, List<Seat>> map2 = new HashMap<>();
        map2.put("A", seats1);
        map2.put("B", seats2);
        map2.put("C", seats1);
        map2.put("D", seats2);

        Screen s1 = new Screen("Screen1", map1);
        Screen s2 = new Screen("Screen2", map2);

        ArrayList<Screen> screens = new ArrayList<>();
        screens.add(s1);
        screens.add(s2);

        Theatre theatre1 = new Theatre("Karthik", screens);

        // Use CARD payment for demonstration
        BookingService bookingService = new BookingService(PaymentStrategyFactory.getPaymentStrategy("CARD"));

        // 5 threads with different scenarios
        Runnable t1 = () -> bookingService.bookseat(theatre1, "Screen1", "A", 4, Thread.currentThread().getName()); // valid, available
        Runnable t2 = () -> bookingService.bookseat(theatre1, "Screen1", "A", 4, Thread.currentThread().getName()); // same seat, race
        Runnable t3 = () -> bookingService.bookseat(theatre1, "Screen1", "B", 2, Thread.currentThread().getName()); // different row, available
        Runnable t4 = () -> bookingService.bookseat(theatre1, "Screen1", "Z", 1, Thread.currentThread().getName()); // invalid row
        Runnable t5 = () -> bookingService.bookseat(theatre1, "Screen2", "A", 99, Thread.currentThread().getName()); // invalid seat

        Thread[] threads = {
            new Thread(t1, "Customer-1"),
            new Thread(t2, "Customer-2"),
            new Thread(t3, "Customer-3"),
            new Thread(t4, "Customer-4"),
            new Thread(t5, "Customer-5")
        };

        for (Thread t : threads) t.start();
        for (Thread t : threads)
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
