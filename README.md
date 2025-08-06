# Movie Ticket Booking LLD (Low-Level Design)

## Overview
This project simulates a movie ticket booking system with a focus on thread safety, OOP principles, and design patterns. It demonstrates how multiple users (threads) can attempt to book seats concurrently, with payment handled via the Strategy pattern.

---

## Main Classes & Responsibilities

### 1. `Seat`
- Represents a seat in a screen.
- Fields: `number`, `isBooked` .

### 2. `Screen`
- Represents a screen in a theatre.
- Fields: `name`, `map` (row name to list of seats).

### 3. `Theatre`
- Represents a theatre with multiple screens.
- Fields: `name`, `screens` (list of Screen).

### 4. `PaymentStrategy` (Interface)
- Strategy pattern for payment processing.
- Method: `boolean pay(String userName, int seatNumber)`

### 5. `CreditCardPayment` & `UPIPayment`
- Implementations of `PaymentStrategy`.
- Simulate payment with 89% success rate.

### 6. `PaymentStrategyFactory`
- Factory pattern to select the payment strategy (e.g., UPI, CARD).
- Method: `getPaymentStrategy(String type)`

### 7. `BookingService`
- Handles seat booking logic with thread safety.
- Uses a `PaymentStrategy` for payment.
- Method: `bookseat(Theatre, String screenname, String screenrow, int seatNumber, String userName)`
    - Synchronized on the `Seat` object to ensure only one thread can book a seat at a time.
    - Handles invalid input (non-existent screen/row/seat).
    - Attempts payment before booking; releases lock if payment fails.

### 8. `Main`
- Sets up the theatre, screens, and seats.
- Demonstrates multithreading by creating 5 threads, each trying to book seats (some valid, some invalid, some racing for the same seat).

---

## Main Methods

- `bookseat(...)` in `BookingService`: Core booking logic, thread-safe, payment integrated.
- `pay(...)` in `PaymentStrategy` implementations: Simulates payment with success/failure.
- `getPaymentStrategy(...)` in `PaymentStrategyFactory`: Returns the correct payment strategy.
- `main(...)` in `Main`: Sets up data and runs booking attempts in multiple threads.

---

## How Threads Are Runnable

In `Main.java`:
- Five `Runnable` tasks are created, each calling `bookingService.bookseat(...)` with different parameters (some valid, some invalid, some targeting the same seat).
- Each `Runnable` is wrapped in a `Thread` with a unique name (e.g., `Customer-1`).
- All threads are started nearly simultaneously, simulating real-world concurrent booking attempts.
- The main thread waits for all booking threads to finish using `join()`.

**Example:**
```java
Runnable t1 = () -> bookingService.bookseat(theatre1, "Screen1", "A", 4, Thread.currentThread().getName());
Thread customer1 = new Thread(t1, "Customer-1");
customer1.start();
```

---

## Design Patterns Used
- **Strategy Pattern:** For payment method selection and processing.
- **Factory Pattern:** For instantiating the correct payment strategy.
- **Thread Safety:** Achieved by synchronizing on the `Seat` object during booking.

---

## How to Run
1. Build the project with Maven (Lombok dependency included).
2. Run `Main.java`.
3. Observe the console output for booking attempts, payment results, and thread interactions.

---
