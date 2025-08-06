package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor

public class Theatre {

    public String name;
    List<Screen> screens;


}
