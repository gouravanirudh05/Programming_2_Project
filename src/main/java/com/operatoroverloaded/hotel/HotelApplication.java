package com.operatoroverloaded.hotel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.operatoroverloaded.hotel.cli.ConsoleApplication;
import com.operatoroverloaded.hotel.stores.roomstore.*;
import com.operatoroverloaded.hotel.stores.roomtypestore.*;
import com.operatoroverloaded.hotel.stores.billstore.*;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.*;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.*;
import com.operatoroverloaded.hotel.stores.logonstore.*;
import com.operatoroverloaded.hotel.stores.dishstore.*;
import com.operatoroverloaded.hotel.stores.tablestore.*;
import com.operatoroverloaded.hotel.models.*;
@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		
		SpringApplicationBuilder app = new SpringApplicationBuilder(HotelApplication.class);

		ConfigurableApplicationContext context = SpringApplication.run(HotelApplication.class, args);
		// SpringApplication.run(HotelApplication.class, args);

        // RoomStore roomStore = context.getBean(RoomStore.class);
        String storeType = "in-memory";
        String interfaceType = "cli";
        // Override defaults with arguments if provided
        if (args.length > 0) {
            storeType = args[0];
        }
        if (args.length > 1) {
            interfaceType = args[1];
        }   
        if ("in-memory".equals(storeType)) {
            RoomStore.setInstance(InMemoryRoomStore.getInstance());
            RoomTypeStore.setInstance(InMemoryRoomTypeStore.getInstance());
            BillStore.setInstance(InMemoryBillStore.getInstance());
            HotelCustomerStore.setInstance(InMemoryHotelCustomerStore.getInstance());
            RestaurantCustomerStore.setInstance(InMemoryRestaurantCustomerStore.getInstance());
            LogonStore.setInstance(InMemoryLogonStore.getInstance());
            DishStore.setInstance(InMemoryDishStore.getInstance()); 
            TableStore.setInstance(InMemoryTableStore.getInstance());
            Hotel.reConfigure();
        }

        if (args.length > 1 && "gui".equalsIgnoreCase(interfaceType)) { // Switch based on condition, e.g., no arguments mean web application
            app.web(WebApplicationType.SERVLET); // Web-based with React/Spring Boot
        } else {
            app.web(WebApplicationType.NONE); // Console application
        }

        app.run(args);
        
        if (args.length > 1 && "cli".equalsIgnoreCase(interfaceType)) {
            // Console application logic
            ConsoleApplication consoleApp = context.getBean(ConsoleApplication.class);
            // consoleApp.setRoomStore(roomStore); // Set roomStore in CLI
            consoleApp.run(args);
        } else {
            // GUI application logic can be handled here
            // You can pass roomStore to a GUI controller or service
        }
	}

}
