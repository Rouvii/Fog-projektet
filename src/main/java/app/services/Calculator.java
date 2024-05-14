package app.services;

import app.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private List<OrderItem> orderItems = new ArrayList<>();
    private int width;
    private int length;

    public Calculator(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public void calcCarport(){
        calcStolper();
        calcRemmer();
        calcSpær();
    }
    private void calcStolper(){

    }

    private void calcRemmer(){

    }

    private void calcSpær(){

    }

    public List<OrderItem> getOrderItems(){
        return orderItems;
    }
}
