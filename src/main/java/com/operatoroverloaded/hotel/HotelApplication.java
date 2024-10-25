package com.operatoroverloaded.hotel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.operatoroverloaded.hotel.cli.ConsoleApplication;
import com.operatoroverloaded.hotel.stores.roomstore.RoomStore;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		
		SpringApplicationBuilder app = new SpringApplicationBuilder(HotelApplication.class);

		if (args.length == 0) { // Switch based on condition, e.g., no arguments mean web application
            app.web(WebApplicationType.SERVLET); // Web-based with React/Spring Boot
        } else {
            app.web(WebApplicationType.NONE); // Console application
        }

        app.run(args);
        ConfigurableApplicationContext context = SpringApplication.run(HotelApplication.class, args);
		// SpringApplication.run(HotelApplication.class, args);

        RoomStore roomStore = context.getBean(RoomStore.class);
        if (args.length > 0) {
            // Console application logic
            ConsoleApplication consoleApp = context.getBean(ConsoleApplication.class);
            consoleApp.setRoomStore(roomStore); // Set roomStore in CLI
            consoleApp.run(args);
        } else {
            // GUI application logic can be handled here
            // You can pass roomStore to a GUI controller or service
        }
	}

}
