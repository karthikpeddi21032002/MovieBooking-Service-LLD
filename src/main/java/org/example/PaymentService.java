package org.example;

import java.util.Random;

public class PaymentService {
    private final Random random = new Random();

    /**
     * Simulates payment with 89% success rate.
     * @param userName The user attempting payment
     * @param seatNumber The seat being booked
     * @return true if payment is successful, false otherwise
     */
    public boolean processPayment(String userName, int seatNumber) {
        boolean success = random.nextInt(100) < 89;
        if (success) {
            System.out.println("[PaymentService] Payment SUCCESS for user " + userName + " on seat " + seatNumber);
        } else {
            System.out.println("[PaymentService] Payment FAILED for user " + userName + " on seat " + seatNumber);
        }
        return success;
    }
}
