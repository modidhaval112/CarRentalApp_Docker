package com.soen6461.carrentalapplication.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.soen6461.carrentalapplication.controller.ClientController;
import com.soen6461.carrentalapplication.controller.VehicleCatalog;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

/**
 * This class controls the UI of the application
 */
@Controller
public class MainController {
//    public enum Status {
//        Available,
//        Rented,
//        Reserved
//    }

    @Autowired
    ClientController clientController;
    @Autowired
    VehicleCatalog vehicleCatalog;

    @RequestMapping("/client-sign-up")
    public String display() {
        return "clientSignUp";
    }


	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String RedirectToDisplayVehicleCatalog() {
        return "redirect:vehicle-catalog";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");
        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");
        return "login";
    }

    @RequestMapping("/vehicle-catalog")
    public ModelAndView displayVehicleCatalog() {
        List<VehicleRecord> vehicles = vehicleCatalog.getAllVehicleRecord();
        List<ClientRecord> clients= clientController.getAllClientRecord();
        ModelAndView model = new ModelAndView("vehicleCatalog");
        model.addObject("vehicles", vehicles);
        model.addObject("clients",clients);
        return model;
    }
    @RequestMapping(value="/assign-vehicle/{lpr}",method = RequestMethod.POST)
    public String assignVehicle(@RequestParam("forClient") String driversLicense, @PathVariable("lpr") String licensePlateRecord, @RequestParam("fromDate2") String startDate, @RequestParam("toDate2") String endDate, @RequestParam("status2") String status){
//        System.out.println("The transaction for client "+driversLicense+" for vehicle "+  " from"+ startDate+" "+ endDate);
        ClientRecord forClient= clientController.searchClient(driversLicense);
        VehicleRecord seletctedVehicle= vehicleCatalog.getVehicleRecord(licensePlateRecord);

        if(status.equals("Rented")){
            Transaction newTransaction= new Transaction (forClient,seletctedVehicle,startDate,endDate,Transaction.Status.Rented);
            seletctedVehicle.addTransaction(newTransaction);
        }
        else if(status.equals("Reserved")){
            Transaction newTransaction= new Transaction(forClient,seletctedVehicle,startDate,endDate,Transaction.Status.Reserved);
            seletctedVehicle.addTransaction(newTransaction);
        }
        else{
            Transaction newTransaction= new Transaction(forClient,seletctedVehicle,startDate,endDate,Transaction.Status.Available);
            seletctedVehicle.addTransaction(newTransaction);
        }
        return "redirect:/vehicle-catalog";
    }

    @RequestMapping(value = "/cancel-transaction/{transactionId}/{lpr}", method = RequestMethod.GET)
    public String cancelTransaction(@PathVariable("transactionId") String transactionId,  @PathVariable("lpr") String licensePlateRecord) {
        VehicleRecord seletctedVehicle= vehicleCatalog.getVehicleRecord(licensePlateRecord);
        seletctedVehicle.removeTransaction(transactionId);
        return "redirect:/vehicle-catalog";
    }
    
    @RequestMapping(value = "/return-transaction/{transactionId}/{lpr}", method = RequestMethod.GET)
    public String returnTransaction(@PathVariable("transactionId") String transactionId,  @PathVariable("lpr") String licensePlateRecord) {
        VehicleRecord seletctedVehicle= vehicleCatalog.getVehicleRecord(licensePlateRecord);
        seletctedVehicle.removeTransaction(transactionId);
        return "redirect:/vehicle-catalog";
    }

    @RequestMapping("/client-register")
    public ModelAndView displayClientRegister() {
        List<ClientRecord> clients = clientController.getAllClientRecord();
        ModelAndView model = new ModelAndView("clientRegister");
        model.addObject("clients", clients);
        return model;
    }

    @RequestMapping(value = "/create-client", method= RequestMethod.POST)
    public String addClientRecord(@ModelAttribute("clientRecord") ClientRecord clientRecord) {
        clientController.addClientRecord(clientRecord);
        return "redirect:/client-register";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editClientRecord(@PathVariable("id") String driverslicense, ModelMap model) {
        model.put("clientRecord", clientController.searchClient(driverslicense));
        return "clientUpdate";
    }

    @RequestMapping(value = "/update-client/{id}", method = RequestMethod.POST)
    public String updateClientRecord(@ModelAttribute("clientRecord") ClientRecord clientRecord, @PathVariable("id") String driverslicense) {
    	System.out.println("Client Name : " + clientRecord.getFirstName());
    	
        clientController.updateClientRecord(clientRecord, driverslicense);
        return "redirect:/client-register";
    }

    @RequestMapping(value = "/delete-client-record/{id}", method = RequestMethod.GET)
    public String deleteClientRecord(@PathVariable("id") String driverslicense) {
        clientController.deleteClientRecord(driverslicense);
        return "redirect:/client-register";
    }

    @RequestMapping(value = "/vehicle-catalog-filter", method = RequestMethod.GET)
    public ModelAndView getFilteredVehicleList(@RequestParam("filter") String filter, @RequestParam("value") String value) {
        List<VehicleRecord> vehicles = vehicleCatalog.getFilteredList(filter, value);
        List<ClientRecord> clients= clientController.getAllClientRecord();
        ModelAndView model = new ModelAndView("vehicleCatalog");
        model.addObject("vehicles", vehicles);
        model.addObject("clients", clients);
        return model;
    }

	/**
	 * Method used to populate the views with hard coded values.
	 * TODO: Remove this method when data persistence is added.
	 * @throws Exception
	 */
	@PostConstruct
    private void AddingHardCodedValues() throws Exception {

        // Adding some hard coded vehicles to populate the views.
       
    }
}