package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Province;
import com.cg.service.customer.ICustomerService;
import com.cg.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping("/customers")
public class ControllerCustomer {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProvinceService provinceService;

    @GetMapping
    private ModelAndView showList(@RequestParam("search") Optional<String > search, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/customers/list-customers");
        Page<Customer> customers ;
        if(search.isPresent()){
            customers = customerService.findAllByFirstNameContaining(search.get(),pageable);
            modelAndView.addObject("customers",customers);
        } else {
            customers = customerService.findAll(pageable);
            modelAndView.addObject("customers", customers);
        }
        return modelAndView;
    }

    @GetMapping("/save")
    private ModelAndView showFormCreateCustomer() {
        ModelAndView modelAndView = new ModelAndView("/customers/create");
        Iterable<Province> provinces = provinceService.findAll();
        modelAndView.addObject("customer",new Customer());
        modelAndView.addObject("provinces",provinces);
        return modelAndView;
    }

    @PostMapping("/save")
    private ModelAndView save(@ModelAttribute("customer") Customer customer){
        ModelAndView modelAndView = new ModelAndView("/customers/create");
        customerService.save(customer);
        Iterable<Province> provinces = provinceService.findAll();
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("provinces", provinces);
        modelAndView.addObject("message","Create new customer successful");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    private ModelAndView showFormEditCustomer(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Customer> customer = customerService.findById(id);
        Iterable<Province> provinces = provinceService.findAll();
        if(customer.isPresent()){
            modelAndView.addObject("customer",customer);
            modelAndView.addObject("provinces",provinces);
            modelAndView.setViewName("/customers/edit-customer");
        } else {
            modelAndView.setViewName("/error.404");
        }
        return modelAndView;
    }

    @PostMapping("/edit")
    private ModelAndView edit(@ModelAttribute("customer") Customer customer) {
        ModelAndView modelAndView = new ModelAndView("/customers/list-customers");
        customerService.save(customer);
        Iterable<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers",customers);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    private ModelAndView showFormDeleteCustomer(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/customers/delete-customer");
        Optional<Customer> customer = customerService.findById(id);
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    @PostMapping("/delete")
    private ModelAndView delete(@ModelAttribute("customer") Customer customer){
        ModelAndView modelAndView = new ModelAndView("/customers/list-customers");
        customerService.remove(customer.getId());
        Iterable<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers",customers);
        return modelAndView;
    }
}
