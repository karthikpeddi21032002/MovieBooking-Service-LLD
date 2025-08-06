package org.example;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Seat {
  public  int number;
  public  boolean isBooked;
}
