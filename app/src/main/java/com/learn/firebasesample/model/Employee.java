package com.learn.firebasesample.model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by CIPL0224 on 8/25/2016.
 */
public class Employee extends Observable{
    private String firstName;
    private String lastName;

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setChanged();
        notifyObservers(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setChanged();
        notifyObservers(lastName);
    }
}
