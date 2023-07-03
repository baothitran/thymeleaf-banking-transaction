package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.withdraw.IWithDrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithDrawService withdrawService;

    @GetMapping
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage() {
        return "customer/create";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng không tồn tại");
        }
        else {
            Customer customer = customerOptional.get();
            Deposit deposit = new Deposit();
            deposit.setCustomer(customer);

            model.addAttribute("deposit", deposit);
        }

        return "customer/deposit";
    }


    @PostMapping("/create")
    public String doCreate(@ModelAttribute Customer customer,RedirectAttributes redirectAttributes) {

        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:/customers/create";
    }

    @PostMapping("/deposit/{customerId}")
    public String doDeposit(@ModelAttribute Deposit deposit, @PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng không tồn tại");
        }
        else {
            Customer customer = customerOptional.get();

            deposit.setId(null);
            depositService.save(deposit);

            BigDecimal currentBalance = customer.getBalance();
            BigDecimal newBalance = currentBalance.add(deposit.getTransactionAmount());
            customer.setBalance(newBalance);
            customerService.save(customer);

            deposit.setCustomer(customer);

            model.addAttribute("deposit", deposit);
        }

        return "customer/deposit";
    }
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable String id, Model model) {
        try {
            Long customerId = Long.parseLong(id);
            Optional<Customer> customerOptional = customerService.findById(customerId);

            if (customerOptional.isEmpty()) {
                return "redirect:/error/404";
            }

            Customer customer = customerOptional.get();

            model.addAttribute("customer", customer);

            return "customer/edit";
        }
        catch (Exception e) {
            return "error/404";
        }
    }
//    @PostMapping("/edit/{id}")
//    public String doUpdate(@PathVariable Long id, @ModelAttribute Customer customer, Model model) {
//
//        Optional<Customer> customerOptional = customerService.findById(id);
//
//
//        if (!customerOptional.isPresent()) {
//            model.addAttribute("error", true);
//        }
//        else {
//            customer.setId(id);
//            customerService.save(customer);
//            model.addAttribute("customer", customer);
//        }
//
//        return "customer/edit";
//    }
@PostMapping("/edit/{id}")
public String doUpdate(@PathVariable Long id, @ModelAttribute Customer customer, Model model,RedirectAttributes redirectAttributes) {
    customer.setId(id);
    customerService.save(customer);

    List<Customer> customers = customerService.findAll();
    redirectAttributes.addFlashAttribute("success",true);
    model.addAttribute("customers", customers);

    return "redirect:/customers";
}
    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Id khách hàng không tồn tại");
        } else {
            Customer customer = customerOptional.get();
            Withdraw withdraw = new Withdraw();
            withdraw.setCustomer(customer);
            model.addAttribute("withdraw", withdraw);
        }

        return "customer/withdraw";
    }
    @PostMapping("/withdraw/{customerId}")
    public String doWithdraw(@ModelAttribute Withdraw withdraw, @PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "Id khách hàng không tồn tại");
        } else {
            Customer customer = customerOptional.get();
            withdraw.setId(null);
            withdraw.setTransactionAmount(withdraw.getTransactionAmount());

            BigDecimal currentBalance = customer.getBalance();
            if (currentBalance.compareTo(withdraw.getTransactionAmount()) < 0) {
                model.addAttribute("error", true);
                model.addAttribute("message", "Số dư không đủ để rút tiền");
            } else {
                BigDecimal newBalance = currentBalance.subtract(withdraw.getTransactionAmount());
                customer.setBalance(newBalance);
                customerService.save(customer);

                withdraw.setCustomer(customer);
                withdrawService.save(withdraw);

                model.addAttribute("withdraw", withdraw);
            }
        }

        return "customer/withdraw";
    }
}