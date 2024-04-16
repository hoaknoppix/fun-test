package com.example.taxisvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaxiSvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaxiSvcApplication.class, args);
  }

}
