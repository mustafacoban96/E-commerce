package com.shepherd.E_commerce.mappers;

import com.shepherd.E_commerce.dto.response.CreateOrderResponse;
import com.shepherd.E_commerce.models.Orders;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Component
public class OrderMapper {


    public CreateOrderResponse toCreateOrderResponse(Orders orders){
        Timestamp createdAt = orders.getCreated_at();
        ZonedDateTime dateTime = createdAt.toInstant().atZone(java.time.ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.ENGLISH);
        String formattedDate = dateTime.format(formatter);
        return new CreateOrderResponse(
                orders.getId(),
                formattedDate,
                orders.getTotal_price(),
                true
        );
    }
}
