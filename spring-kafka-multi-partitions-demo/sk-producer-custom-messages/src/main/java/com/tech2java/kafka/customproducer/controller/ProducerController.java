
package com.tech2java.kafka.customproducer.controller;

import com.tech2java.kafka.customproducer.model.Location;
import com.tech2java.kafka.customproducer.model.Order;
import com.tech2java.kafka.customproducer.service.ProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class ProducerController {

    private final ProducerService producerService;


    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String sendLocationMessage(@RequestBody Location location) {
       // producerService.sendLocationMessage(location);
        System.out.println("Successfully Published the Location = '" + location + "' to the test topic");
        return "Successfully Published the Location = '" + location + "' to the test topic";
    }


}







