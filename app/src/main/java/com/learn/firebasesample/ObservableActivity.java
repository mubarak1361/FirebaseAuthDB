package com.learn.firebasesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.learn.firebasesample.model.Employee;

import java.util.Observable;
import java.util.Observer;

public class ObservableActivity extends AppCompatActivity implements Observer {

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);
        employee  =  new Employee();
        employee.setFirstName("Adam");
        employee.setLastName("Mohideen");
        employee.addObserver(this);
        employee.setFirstName("Mubarak");
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            Log.d("Changed Object",((String)o));
        }
    }
}
