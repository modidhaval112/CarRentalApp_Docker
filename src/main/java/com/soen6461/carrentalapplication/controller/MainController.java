package com.soen6461.carrentalapplication.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.soen6461.carrentalapplication.config.Clerk;
import com.soen6461.carrentalapplication.config.User;
import com.soen6461.carrentalapplication.config.UserRegister;
import com.soen6461.carrentalapplication.config.Administrator;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.VehicleRecord;

/**
 * This class controls the UI of the application
 */
@Controller
public class MainController {

	@Autowired
	private ClientController clientController;

	@Autowired
	private VehicleCatalog vehicleCatalog;

	@Autowired
	private TransactionCatalog transactionCatalog;

	@Autowired
	private UserRegister userRegister;

	@RequestMapping("/client-sign-up")
	public String display() {
		return "clientSignUp";
	}

	@RequestMapping("/vehicle-add")
	public String vehicleDisplay() {
		return "vehicleAdd";
	}

	@RequestMapping("/online-help")
	public ModelAndView displayOnlineHelp() {
		ModelAndView model = new ModelAndView("onlineHelp");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
			model.addObject("disableButton", 0);
		}
		else {
			model.addObject("disableButton", 1);
		}
		return model;
	}

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String RedirectToDisplayVehicleCatalog() {
		if(isAdministratorRole())
		{
			return "redirect:trans-list";
		}
		else
		{
			return "redirect:vehicle-catalog";
		}
    }

    private boolean isAdministratorRole()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority authority: authentication.getAuthorities()) {
			if(authority.toString().equalsIgnoreCase("ROLE_" + User.RoleType.Administrator))
			{
				return true;
			}
		}

		return false;
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
		List<ClientRecord> clients = clientController.getAllClientRecord();

		ModelAndView model = new ModelAndView("vehicleCatalog");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
			model.addObject("disableButton", 0);
		}
		else {
			model.addObject("disableButton", 1);
		}
		
		model.addObject("vehicles", vehicles);
		model.addObject("clients", clients);
		return model;
	}

	@RequestMapping(value = "/assign-vehicle/{lpr}", method = RequestMethod.POST)
	public String assignVehicle(@RequestParam("forClient") String driversLicense,
			@PathVariable("lpr") String licensePlateRecord, @RequestParam("fromDate2") String startDate,
			@RequestParam("toDate2") String endDate, @RequestParam("status2") String status,
			RedirectAttributes redirectAttributes) throws ParseException {

		VehicleRecord selectedVehicle = vehicleCatalog.getVehicleRecord(licensePlateRecord);
		List<Transaction> transactionList = selectedVehicle.getVehicleTransactionList();

		Date tempStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		Date tempEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

		boolean overlap = false;
		for (int i = 0; i < transactionList.size(); i++) {
			overlap = (tempStartDate.getTime() <= transactionList.get(i).getEndDate().getTime())
					&& (transactionList.get(i).getStartDate().getTime() <= tempEndDate.getTime());

			if (overlap) {
				redirectAttributes.addFlashAttribute("errorMsg",
						"  Sorry, this car is already booked for the given period of time.");
				break;
			}
		}

		if (!overlap) {
			redirectAttributes.addFlashAttribute("successMsg", "  Congratulations, You have booked the Car !!!");
			vehicleCatalog.assignVehicle(driversLicense, licensePlateRecord, startDate, endDate, status);
		}
		return "redirect:/vehicle-catalog";
	}

	@RequestMapping(value = "/cancel-transaction/{transactionId}/{lpr}", method = RequestMethod.GET)
	public String cancelTransaction(@PathVariable("transactionId") String transactionId,
			@PathVariable("lpr") String licensePlateRecord, RedirectAttributes redirectAttributes) {

		vehicleCatalog.cancelTransaction(licensePlateRecord, transactionId, redirectAttributes);

		return "redirect:/vehicle-catalog";
	}

	@RequestMapping(value = "/return-transaction/{transactionId}/{lpr}", method = RequestMethod.GET)
	public String returnTransaction(@PathVariable("transactionId") String transactionId,
			@PathVariable("lpr") String licensePlateRecord, RedirectAttributes redirectAttributes) {
		vehicleCatalog.returnTransaction(transactionId, licensePlateRecord);
		redirectAttributes.addFlashAttribute("successMsg", "  Car has been returned.");
		return "redirect:/vehicle-catalog";
	}

	@RequestMapping(value = "/edit-transaction/{clientDriversLicense}", method = RequestMethod.POST)
	public String editTransaction(@PathVariable("clientDriversLicense") String driversLicenseNumber,
			@RequestParam("licensePlateRecord") String licensePlateRecord, @RequestParam("fromDate") String startDate,
			@RequestParam("toDate") String endDate, @RequestParam("status") String status) throws ParseException {
		VehicleRecord selectedVehicle = vehicleCatalog.getVehicleRecord(licensePlateRecord);
		ClientRecord selectedClient = clientController.searchClient(driversLicenseNumber);
		List<Transaction> buffer = selectedVehicle.getVehicleTransactionList();
		if (status.equals("Rented")) {
			Transaction updatedTransaction = new Transaction(selectedClient, selectedVehicle, startDate, endDate,
					Transaction.Status.Rented);
			for (int i = 0; i < buffer.size(); i++) {
				if (buffer.get(i).getTransactionId().equals(updatedTransaction.getTransactionId())) {
					buffer.set(i, updatedTransaction);
				}
			}
		} else if (status.equals("Reserved")) {
			Transaction updatedTransaction = new Transaction(selectedClient, selectedVehicle, startDate, endDate,
					Transaction.Status.Rented);
			for (int i = 0; i < buffer.size(); i++) {
				if (buffer.get(i).getTransactionId().equals(updatedTransaction.getTransactionId())) {
					buffer.set(i, updatedTransaction);
				}
			}
		}
		return "redirect:/vehicle-catalog";
	}

	@RequestMapping("/client-register")
	public ModelAndView displayClientRegister() {
		List<ClientRecord> clients = clientController.getAllClientRecord();
		ModelAndView model = new ModelAndView("clientRegister");
		model.addObject("clients", clients);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
			model.addObject("disableButton", 0);
		}
		else {
			model.addObject("disableButton", 1);
		}	
		
		return model;
	}

	@RequestMapping("/vehicle-register")
	public ModelAndView displayVehicleRegister() {
		List<VehicleRecord> vehicles = vehicleCatalog.getAllVehicleRecord();
		ModelAndView model = new ModelAndView("vehicleDisplay");
		model.addObject("vehicles", vehicles);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
			model.addObject("disableButton", 0);
		}
		else {
			model.addObject("disableButton", 1);
		}
		
		return model;
	}

	@RequestMapping(value = "/create-client", method = RequestMethod.POST)
	public String addClientRecord(@ModelAttribute("clientRecord") ClientRecord clientRecord,
			RedirectAttributes redirectAttributes) {

		List<ClientRecord> clientRecordList = clientController.getAllClientRecord();
		boolean recordExists = false;
		for (int i = 0; i < clientRecordList.size(); i++) {
			if (clientRecordList.get(i).getDriversLicenseNumber().equalsIgnoreCase(clientRecord.getDriversLicenseNumber())) {
				recordExists = true;
				break;
			}
		}

		if (recordExists) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"  Sorry, Client Record already exists with this Driver's Licence Number.");
		} else {
			redirectAttributes.addFlashAttribute("successMsg", "  Client has been added successfully.");
			clientController.addClientRecord(clientRecord);

		}

		return "redirect:/client-register";
	}

	@RequestMapping(value = "/create-vehicle", method = RequestMethod.POST)
	public String addVehicleRecord(@ModelAttribute("vehicleRecord") VehicleRecord vehicleRecord,
			RedirectAttributes redirectAttributes) {

		List<VehicleRecord> vehicleRecordList = vehicleCatalog.getAllVehicleRecord();
		boolean recordExists = false;
		for (int i = 0; i < vehicleRecordList.size(); i++) {
			if (vehicleRecordList.get(i).getLpr().equals(vehicleRecord.getLpr())) {
				recordExists = true;
				break;
			}
		}

		if (recordExists) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"  Sorry, Vehicle Record already exists with this Vehicle Registration Number.");
		} else {
			redirectAttributes.addFlashAttribute("successMsg", "  Vehicle has been added successfully.");
			vehicleCatalog.addVehicleRecord(vehicleRecord);

		}

		return "redirect:/vehicle-register";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String editClientRecord(@PathVariable("id") String driverslicense, ModelMap model,
			RedirectAttributes redirectAttributes) {
		model.put("clientRecord", clientController.searchClient(driverslicense));

		return "clientUpdate";
	}

	@RequestMapping(value = "edit-vehicle/{id}", method = RequestMethod.GET)
	public String vehicleEdit(@PathVariable("id") String lpr, ModelMap model, RedirectAttributes redirectAttributes) {
		model.put("vehiceleRecord", vehicleCatalog.searchVehicle(lpr));

		return "vehicleEdit";
	}

	@RequestMapping(value = "/update-client/{id}", method = RequestMethod.POST)
	public String updateClientRecord(@ModelAttribute("clientRecord") ClientRecord clientRecord,
			@PathVariable("id") String driverslicense, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("successMsg", "  Client Record has been updated successfully.");

		clientController.updateClientRecord(clientRecord, driverslicense);
		return "redirect:/client-register";
	}

	@RequestMapping(value = "/delete-client-record/{id}", method = RequestMethod.GET)
	public String deleteClientRecord(@PathVariable("id") String driverslicense, RedirectAttributes redirectAttributes) {
		clientController.deleteClientRecord(driverslicense);

		redirectAttributes.addFlashAttribute("warningMsg", "  Client Record has been deleted.");

		return "redirect:/client-register";
	}

	@RequestMapping(value = "/vehicle-catalog-filter", method = RequestMethod.GET)
	public ModelAndView getFilteredVehicleList(@RequestParam("filter") String filter,
			@RequestParam("value") String value) {
		List<VehicleRecord> vehicles = vehicleCatalog.getFilteredList(filter, value);
		List<ClientRecord> clients = clientController.getAllClientRecord();
		ModelAndView model = new ModelAndView("vehicleCatalog");
		model.addObject("vehicles", vehicles);
		model.addObject("clients", clients);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
			model.addObject("disableButton", 0);
		}
		else {
			model.addObject("disableButton", 1);
		}
		
		return model;
	}

	@RequestMapping(value = "/translist-filter", method = RequestMethod.GET)
	public ModelAndView getFilteredTransactionHistory(@RequestParam("filter") String filter,
			@RequestParam("value") String value) {
		System.out.println(filter + " " + value);
		List<TransactionHistory> transactionsList = transactionCatalog.getFilteredTransactionHistory(filter, value);
		ModelAndView model = new ModelAndView("transactions");
		model.addObject("transactionsList", transactionsList);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
			model.addObject("disableButton", 0);
		}
		else {
			model.addObject("disableButton", 1);
		}
		
		return model;
	}

	@RequestMapping("/trans-list")
	public ModelAndView displayAllTransactions() {
		List<TransactionHistory> transactionsList = transactionCatalog.getAllTransactionHistory();
		ModelAndView model = new ModelAndView("transactions");
		model.addObject("transactionsList", transactionsList);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
			model.addObject("disableButton", 0);
		}
		else {
			model.addObject("disableButton", 1);
		}
		
		return model;
	}


	/**
	 * Method used to populate the views with hard coded values. TODO: Remove this
	 * method when data persistence is added.
	 *
	 * @throws Exception
	 */
	@PostConstruct
	private void AddingHardCodedValues() throws Exception {

        // Create some users.
        userRegister.addUser(new Administrator("admin", "admin"));
        userRegister.addUser(new Clerk("clerk", "clerk"));
		userRegister.addUser(new Clerk("super_clerk", "clerk"));

        // Adding some hard coded vehicles to populate the views.
        VehicleRecord v1 = new VehicleRecord("A12_636", "SUV", "Jeep", "Mercedes Rover", 2019, "Gold");
        this.vehicleCatalog.addVehicleRecord(v1);

		this.vehicleCatalog.addVehicleRecord(new VehicleRecord("U12_126", "SUV", "Jeep", "Hummer", 2019, "Yellow"));

		VehicleRecord v2 = new VehicleRecord("X12_646", "Sedan", "Audi", "A8", 2011, "Red");
		this.vehicleCatalog.addVehicleRecord(v2);

		this.vehicleCatalog.addVehicleRecord(new VehicleRecord("Z12_996", "Sedan", "Audi", "Q7", 2014, "Black"));

		// Adding some hard coded clients to populate the views.
		ClientRecord c1 = new ClientRecord("A-1234-123456-12", "Dominick", "Cobb", "(438) 566-9999", "2039-10-1");
		this.clientController.addClientRecord(c1);

		this.clientController.addClientRecord(
				new ClientRecord("A-1234-123456-13", "Robert", "Fischer", "(438) 566-9999", "2029-11-1"));
		this.clientController
				.addClientRecord(new ClientRecord("A-1234-123456-14", "Mal", "Cobb", "(438) 566-9999", "2029-12-1"));

		ClientRecord c2 = new ClientRecord("A-1234-123456-15", "Stephen", "Miles", "(438) 566-9999", "2059-11-1");
		this.clientController.addClientRecord(c2);
		this.clientController.addClientRecord(
				new ClientRecord("A-1234-123456-16", "Ariadne", "Fischer", "(438) 566-9999", "2079-11-1"));

		// Adding some hard coded transactions to populate the views.
		v1.addTransaction(new Transaction(c1, v1, "2019-08-1", "2019-09-15", Transaction.Status.Rented));
		v1.addTransaction(new Transaction(c2, v1, "2019-08-10", "2019-09-19", Transaction.Status.Rented));

		v2.addTransaction(new Transaction(c2, v2, "2020-07-1", "2019-07-15", Transaction.Status.Rented));
		v2.addTransaction(new Transaction(c1, v2, "2020-09-1", "2019-09-15", Transaction.Status.Rented));
	}
}
