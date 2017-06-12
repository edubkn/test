package br.com.test.controller;

import br.com.test.dto.CustomerDto;
import br.com.test.exception.EmailAlreadyRegisteredException;
import br.com.test.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Eduardo on 11/06/2017.
 */
@RestController
public class CustomerController {

    public static final String PATH = "/customer";
    private final CustomerService customerService;

    public CustomerController(@Autowired CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(PATH)
    public ResponseEntity<?> insertCustomer(@RequestBody CustomerDto customer)  {
        try {
            Long id = customerService.insertCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }

    }


}
