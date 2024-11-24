package com.operatoroverloaded.hotel;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.operatoroverloaded.hotel.models.Hotel;
import com.operatoroverloaded.hotel.stores.billstore.BillStore;
import com.operatoroverloaded.hotel.stores.billstore.InMemoryBillStore;
import com.operatoroverloaded.hotel.stores.dishstore.DishStore;
import com.operatoroverloaded.hotel.stores.dishstore.InMemoryDishStore;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.HotelCustomerStore;
import com.operatoroverloaded.hotel.stores.hotelcustomerstore.InMemoryHotelCustomerStore;
import com.operatoroverloaded.hotel.stores.logonstore.InMemoryLogonStore;
import com.operatoroverloaded.hotel.stores.logonstore.LogonStore;
import com.operatoroverloaded.hotel.stores.reservationstore.InMemoryReservationStore;
import com.operatoroverloaded.hotel.stores.reservationstore.ReservationStore;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.InMemoryRestaurantCustomerStore;
import com.operatoroverloaded.hotel.stores.restaurantcustomerstore.RestaurantCustomerStore;
import com.operatoroverloaded.hotel.stores.roomstore.InMemoryRoomStore;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.InMemoryRoomTypeStore;
import com.operatoroverloaded.hotel.stores.roomtypestore.RoomTypeStore;
import com.operatoroverloaded.hotel.stores.tablestore.InMemoryTableStore;
import com.operatoroverloaded.hotel.stores.tablestore.TableStore;
import com.operatoroverloaded.hotel.stores.staffstore.InMemoryStaffStore;
import com.operatoroverloaded.hotel.stores.staffstore.StaffStore;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { 
    SecurityAutoConfiguration.class 
})
public class HotelApplication {

	public static void main(String[] args) {
		
		SpringApplicationBuilder app = new SpringApplicationBuilder(HotelApplication.class);

		// ConfigurableApplicationContext context = SpringApplication.run(HotelApplication.class, args);
		// SpringApplication.run(HotelApplication.class, args);

        // RoomStore roomStore = context.getBean(RoomStore.class);
        String storeType = "in-memory";
        String interfaceType = "gui";
        System.err.println(System.getProperty("java.library.path"));
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        // Override defaults with arguments if provided
        System.err.println("HELLO-TEST");
        // System.err.println(args[0]+args[1]);
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
            ReservationStore.setInstance(InMemoryReservationStore.getInstance());
            StaffStore.setInstance(InMemoryStaffStore.getInstance());
            Hotel.reConfigure();
        }
        // app.profiles("gui").web(WebApplicationType.SERVLET);
        if (args.length > 1 && "gui".equalsIgnoreCase(interfaceType)) { // Switch based on condition, e.g., no arguments mean web application
            app.profiles("gui").web(WebApplicationType.SERVLET); // Web-based with React/Spring Boot
        } else {
            app.profiles("cli").web(WebApplicationType.NONE); // Console application
        }

        app.run(args);
        
        // if (args.length > 1 && "cli".equalsIgnoreCase(interfaceType)) {
        //     // Console application logic
        //     // ConsoleApplication consoleApp = context.getBean(ConsoleApplication.class);
        //     // consoleApp.setRoomStore(roomStore); // Set roomStore in CLI
        //     // consoleApp.run(args);
            
        // } else {
        //     // GUI application logic can be handled here
        //     // You can pass roomStore to a GUI controller or service
        // }
	}

}
