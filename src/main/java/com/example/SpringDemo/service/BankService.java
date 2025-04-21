package com.example.SpringDemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringDemo.Entity.Bank;
import com.example.SpringDemo.util.GoogleMapsAPI;

@Service
public class BankService {

    
	@Autowired
	private GoogleMapsAPI googleMapsAPI;
	
	public List<Bank> findBanksNearby(String zipcode) {
	    double[] userLocation = googleMapsAPI.getCoordinatesFromZip(zipcode);
	    return googleMapsAPI.getNearbyBanksFromCoordinates(userLocation[0], userLocation[1]);
	}


    
}
