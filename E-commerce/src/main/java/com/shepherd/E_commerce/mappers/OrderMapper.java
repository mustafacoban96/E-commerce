package com.shepherd.E_commerce.mappers;

import com.shepherd.E_commerce.dto.response.CreateOrderResponse;
import com.shepherd.E_commerce.dto.response.OrderResponse;
import com.shepherd.E_commerce.models.Orders;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class OrderMapper {


    public CreateOrderResponse toCreateOrderResponse(Orders orders){

        String formattedDate = dateConveter(orders.getCreated_at());
        return new CreateOrderResponse(
                orders.getId(),
                formattedDate,
                orders.getTotal_price(),
                true
        );
    }

    public OrderResponse toOrderResponse(Orders orders){
        String formattedDate = dateConveter(orders.getCreated_at());
        return new OrderResponse(
                orders.getId(),
                formattedDate,
                orders.getTotal_price()

        );
    }


    private String dateConveter(Timestamp date){
        Timestamp createdAt = date;
        ZonedDateTime dateTime = createdAt.toInstant().atZone(java.time.ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH); // Use "E" for short day name and "yyyy" for full year
        String formattedDate = dateTime.format(formatter);
        return formattedDate;
    }
}
