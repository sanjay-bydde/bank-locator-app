package com.example.SpringDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringDemo.Entity.Bank;
import com.example.SpringDemo.service.BankService;

@RestController
@RequestMapping("/banks")
public class BankController {

	@Autowired
    private BankService bankService;

    @GetMapping("/nearby")
    public List<Bank> getNearbyBanks(@RequestParam String zipcode) {
        return bankService.findBanksNearby(zipcode);
    }
}
