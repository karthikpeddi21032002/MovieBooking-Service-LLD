package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class Screen {
    public String name;
    HashMap<String, List<Seat>> map;
}
