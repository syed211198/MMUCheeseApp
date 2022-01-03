package uk.ac.mmu.advprog.mmucheeseapp;

import java.io.Serializable;

public class Cheese implements Serializable {
    private String name;
    private String details;

    public Cheese(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }




}
