package id.ac.ui.cs.advprog.shipping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ShipmentController {
    @GetMapping("/")
    public String trackingPage() {
        return "trackShippingPage";
    }

    @GetMapping("/result/{id}")
    public String trackingResultPage(@PathVariable("id") String id){
        return "resultPage";
    }

}
