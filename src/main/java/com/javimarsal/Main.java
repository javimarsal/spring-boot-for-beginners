package com.javimarsal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @ComponentScan(basePackages = "com.javimarsal")
// @EnableAutoConfiguration
// @Configuration
@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers") // provide the active root
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ) {}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());

        customerRepository.save(customer);
    }

    // path: api/v1/customers/1
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@RequestBody NewCustomerRequest customerToUpdate,
                               @PathVariable("customerId") Integer id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setAge(customerToUpdate.age());
        customer.setEmail(customerToUpdate.email());
        customer.setName(customerToUpdate.name());

        customerRepository.save(customer);
    }
}
