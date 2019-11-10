package com.soen6461.carrentalapplication.controller;

import com.soen6461.carrentalapplication.config.Administrator;
import com.soen6461.carrentalapplication.config.Clerk;
import com.soen6461.carrentalapplication.config.User;
import com.soen6461.carrentalapplication.config.UserRegister;
import com.soen6461.carrentalapplication.model.ClientRecord;
import com.soen6461.carrentalapplication.model.Transaction;
import com.soen6461.carrentalapplication.model.TransactionHistory;
import com.soen6461.carrentalapplication.model.VehicleRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    /**
     * This method is designed for returning view that displays all vehicles
     *
     * @return vehicleAdd view
     */
    @RequestMapping("/vehicle-add")
    public ModelAndView vehicleDisplay() {
        ModelAndView model = new ModelAndView("vehicleAdd");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * This method is designed for returning view that displays online help
     *
     * @return onlineHelp view
     */
    @RequestMapping("/online-help")
    public ModelAndView displayOnlineHelp() {
        ModelAndView model = new ModelAndView("onlineHelp");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * This method is designed for redirection based on authentication
     *
     * @return redirection to particular view
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String RedirectToDisplayVehicleCatalog() {
        if (isAdministratorRole()) {
            return "redirect:/trans-list";
        } else {
            return "redirect:/vehicle-catalog";
        }
    }

    /**
     * Validates if logged in user is Admin or not
     *
     * @return true or false
     */
    private boolean isAdministratorRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.toString().equals("ROLE_" + User.RoleType.Administrator)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Method for handling logging process
     *
     * @param model
     * @param error
     * @param logout
     * @return authentication message
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");
        if (logout != null)
            clientController.persistData();
        vehicleCatalog.persistData();
        model.addAttribute("msg", "You have been logged out successfully.");
        return "/login";
    }

    /**
     * Method designed for displaying vehicle catalog
     *
     * @return vehicleCatalog view
     */
    @RequestMapping("/vehicle-catalog")
    public ModelAndView displayVehicleCatalog() {
        List<VehicleRecord> vehicles = vehicleCatalog.getAllVehicleRecord();
        List<ClientRecord> clients = clientController.getAllClientRecord();

        ModelAndView model = new ModelAndView("vehicleCatalog");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        model.addObject("vehicles", vehicles);
        model.addObject("clients", clients);
        return model;
    }

    /**
     * Method for assigning vehicle to the client
     *
     * @param driversLicense
     * @param licensePlateRecord
     * @param startDate
     * @param endDate
     * @param status
     * @param redirectAttributes
     * @return redirection to vehicle-catalog
     * @throws ParseException
     */
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

            if ((transactionList.get(i).getStatus().equals(Transaction.Status.Reserved)) || (transactionList.get(i).getStatus().equals(Transaction.Status.Rented))) {
                overlap = (tempStartDate.getTime() <= transactionList.get(i).getEndDateObject().getTime())
                        && (transactionList.get(i).getStartDateObject().getTime() <= tempEndDate.getTime());

                if (overlap) {
                    redirectAttributes.addFlashAttribute("errorMsg",
                            "  Sorry, this car is already booked for the given period of time.");
                    break;
                }
            }
        }

        if (!overlap) {
            redirectAttributes.addFlashAttribute("successMsg", "  Congratulations, You have booked the Car !!!");
            vehicleCatalog.assignVehicle(driversLicense, licensePlateRecord, startDate, endDate, status);
        }
        return "redirect:/vehicle-catalog";
    }

    /**
     * Method for cancelling the transaction
     *
     * @param transactionId
     * @param licensePlateRecord
     * @param redirectAttributes
     * @return redirection to vehicle-catalog
     */
    @RequestMapping(value = "/cancel-transaction/{transactionId}/{lpr}", method = RequestMethod.GET)
    public String cancelTransaction(@PathVariable("transactionId") String transactionId,
                                    @PathVariable("lpr") String licensePlateRecord, RedirectAttributes redirectAttributes) {

        vehicleCatalog.cancelTransaction(licensePlateRecord, transactionId, redirectAttributes);

        return "redirect:/vehicle-catalog";
    }

    /**
     * Method for return transaction
     *
     * @param transactionId
     * @param licensePlateRecord
     * @param redirectAttributes
     * @return redirection to vehicle-catalog
     */
    @RequestMapping(value = "/return-transaction/{transactionId}/{lpr}", method = RequestMethod.GET)
    public String returnTransaction(@PathVariable("transactionId") String transactionId,
                                    @PathVariable("lpr") String licensePlateRecord, RedirectAttributes redirectAttributes) {
        vehicleCatalog.returnTransaction(transactionId, licensePlateRecord);
        redirectAttributes.addFlashAttribute("successMsg", "  Car has been returned.");
        return "redirect:/vehicle-catalog";
    }

    /**
     * Method for editing transaction
     *
     * @param driversLicenseNumber
     * @param licensePlateRecord
     * @param startDate
     * @param endDate
     * @param status
     * @return redirection to vehicle-catalog
     * @throws ParseException
     */
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

    /**
     * Method for registering clients
     *
     * @return clientRegister view
     */
    @RequestMapping("/client-register")
    public ModelAndView displayClientRegister() {
        List<ClientRecord> clients = clientController.getAllClientRecord();
        ModelAndView model = new ModelAndView("clientRegister");
        model.addObject("clients", clients);

        if (this.isAdministratorRole()) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * Method for registering vehicles
     *
     * @return vehicleDisplay view
     */
    @RequestMapping("/vehicle-register")
    public ModelAndView displayVehicleRegister() {
        vehicleCatalog.persistData();
        List<VehicleRecord> vehicles = vehicleCatalog.getAllVehicleRecord();
        ModelAndView model = new ModelAndView("vehicleDisplay");
        model.addObject("vehicles", vehicles);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * Method for adding new client
     *
     * @param clientRecord
     * @param redirectAttributes
     * @return redirection to client-register
     */
    @RequestMapping(value = "/create-client", method = RequestMethod.POST)
    public String addClientRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                  @RequestParam("phoneNumber") String phoneNumber, @RequestParam("driversLicenseNumber") String driversLicenseNumber, @RequestParam("expirationDate") String expirationDate,
                                  RedirectAttributes redirectAttributes) {

        List<ClientRecord> clientRecordList = clientController.getAllClientRecord();
        boolean recordExists = false;
        for (int i = 0; i < clientRecordList.size(); i++) {
            if (clientRecordList.size() != 0 && !(clientRecordList.isEmpty()) && clientRecordList.get(i).getDriversLicenseNumber().equalsIgnoreCase(driversLicenseNumber)) {
                recordExists = true;
                break;
            }
        }

        if (recordExists) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    "  Sorry, Client Record already exists with this Driver's License Number.");
        } else {
            redirectAttributes.addFlashAttribute("successMsg", "  Client has been added successfully.");
            clientController.addClientRecord(new ClientRecord(driversLicenseNumber, firstName, lastName, phoneNumber, expirationDate));

        }

        return "redirect:/client-register";
    }

    /**
     * Method for adding new vehicle record
     *
     * @param vehicleRecord
     * @param redirectAttributes
     * @return redirection to vehicle-register
     */
    @RequestMapping(value = "/create-vehicle", method = RequestMethod.POST)
    public String addVehicleRecord(@RequestParam("carType") String carType, @RequestParam("make") String make,
                                   @RequestParam("model") String model, @RequestParam("color") String color, @RequestParam("year") String year,
                                   @RequestParam("lpr") String lpr, RedirectAttributes redirectAttributes) {

        List<VehicleRecord> vehicleRecordList = vehicleCatalog.getAllVehicleRecord();
        VehicleRecord vehicleRecord = new VehicleRecord(1, 1, lpr, carType, make, model, Integer.parseInt(year), color);
        boolean recordExists = checkIfVehicleExists(vehicleRecordList, vehicleRecord);

        if (recordExists) {
            redirectAttributes.addFlashAttribute("errorMsg",
                    "  Sorry, Vehicle Record already exists with this Vehicle Registration Number.");
        } else {
            redirectAttributes.addFlashAttribute("successMsg", "  Vehicle has been added successfully.");
            vehicleCatalog.addVehicleRecord(vehicleRecord);

        }

        return "redirect:/vehicle-register";
    }

    /**
     * Method for editing client record
     *
     * @param driverslicense
     * @param model
     * @param redirectAttributes
     * @return clientUpdate view
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editClientRecord(@PathVariable("id") String driverslicense, ModelAndView model,
                                         RedirectAttributes redirectAttributes) throws Exception {
        model.setViewName("clientUpdate");

        model.addObject("clientRecord", clientController.searchClient(driverslicense));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * This Url is used when the edit button is pressed in the vehicle register page
     *
     * @param lpr
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/edit-vehicle/{id}", method = RequestMethod.GET)
    public String vehicleEdit(@PathVariable("id") String lpr, ModelMap model, RedirectAttributes redirectAttributes) {
        //		model.setViewName("vehicleEdit");
        //
        //		model.addObject("vehcileRecord", vehicleCatalog.searchVehicle(lpr));

        vehicleCatalog.persistData();

        VehicleRecord vr = vehicleCatalog.searchVehicle(lpr);
        //		model.addAllObjects(modelMap)
        model.addAttribute("lpr", vr.getLpr());
        model.addAttribute("year", vr.getYear());
        model.addAttribute("model", vr.getModel());
        model.addAttribute("color", vr.getColor());
        model.addAttribute("make", vr.getMake());
        model.addAttribute("carType", vr.getCarType());
        model.addAttribute("version", vr.getVersion());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addAttribute("disableButton", 0);
        } else {
            model.addAttribute("disableButton", 1);
        }


        return "/vehicleEdit";
    }

    /**
     * This Url is used when the update button is pressed in the Vehicle update page
     *
     * @param vehicleRecord
     * @param lpr
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/update-vehicle/{id}", method = RequestMethod.POST)
    public String updateVehicleRecord(@RequestParam("version") String version, @RequestParam("carType") String carType, @RequestParam("make") String make,
                                      @RequestParam("model") String model, @RequestParam("color") String color, @RequestParam("year") String year,
                                      @PathVariable("id") String lpr, RedirectAttributes redirectAttributes) {


        VehicleRecord vehicleRecord = new VehicleRecord(1, Integer.parseInt(version), lpr, carType, make, model, Integer.parseInt(year), color);
        if (vehicleCatalog.updateVehicleRecord(vehicleRecord, lpr)) {
            redirectAttributes.addFlashAttribute("successMsg", "  Vehicle Record has been updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "  This is an older version of record you have. Please try again.");
        }
        return "redirect:/vehicle-register";
    }

    /**
     * Method for updating client record
     *
     * @param clientRecord
     * @param driverslicense
     * @param redirectAttributes
     * @return redirection to client-register
     */
    @RequestMapping(value = "/update-client/{id}", method = RequestMethod.POST)
    public String updateClientRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                     @RequestParam("phoneNumber") String phoneNumber, @RequestParam("driversLicenseNumber") String driversLicenseNumber, @RequestParam("expirationDate") String expirationDate,
                                     @PathVariable("id") String driverslicense, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("successMsg", "  Client Record has been updated successfully.");

        clientController.updateClientRecord(new ClientRecord(driversLicenseNumber, firstName, lastName, phoneNumber, expirationDate), driverslicense);
        return "redirect:/client-register";
    }

    /**
     * Method for deleting client record
     *
     * @param driverslicense
     * @param redirectAttributes
     * @return redirection to client-register
     */
    @RequestMapping(value = "/delete-client-record/{id}", method = RequestMethod.GET)
    public String deleteClientRecord(@PathVariable("id") String driverslicense, RedirectAttributes redirectAttributes) {
        clientController.deleteClientRecord(driverslicense);

        redirectAttributes.addFlashAttribute("warningMsg", "  Client Record has been deleted.");

        return "redirect:/client-register";
    }

    /**
     * Method for deleting vehicle record
     *
     * @param lpr
     * @param redirectAttributes
     * @return redirection to vehicle-register
     */
    @RequestMapping(value = "/delete-vehicle-record/{id}", method = RequestMethod.GET)
    public String deleteVehicleRecord(@PathVariable("id") String lpr, RedirectAttributes redirectAttributes) {

        vehicleCatalog.persistData();
        
        VehicleRecord selectedVehicle = vehicleCatalog.getVehicleRecord(lpr);
        List<Transaction> transactionList = selectedVehicle.getVehicleTransactionList();

        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getStatus().equals(Transaction.Status.Rented) || (transactionList.get(i).getStatus().equals(Transaction.Status.Reserved))) {
                redirectAttributes.addFlashAttribute("errorMsg", "  To delete vehicle record all the transactions status must be 'Returned' or 'Cancelled'.");
                return "redirect:/vehicle-register";
            }
        }

        if(vehicleCatalog.deleteVehicleRecord(lpr)) {		
			redirectAttributes.addFlashAttribute("warningMsg", "  Vehicle Record has been deleted.");
		}
		else {
			redirectAttributes.addFlashAttribute("errorMsg", "  Vehicle Record has already been deleted.");
		}

        return "redirect:/vehicle-register";
    }

    /**
     * Method for filtering vehicles
     *
     * @param filter
     * @param value
     * @param selectfromdate
     * @param selecttodate
     * @param Only_Date
     * @return vehicleCatalog view
     * @throws ParseException
     */
    @RequestMapping(value = "/vehicle-catalog-filter", method = RequestMethod.GET)
    public ModelAndView getFilteredVehicleList(@RequestParam("filter") String filter,
                                               @RequestParam("value") String value, @RequestParam("selectfromdate") String selectfromdate, @RequestParam("selecttodate") String selecttodate, @RequestParam("Only_Date") String Only_Date) throws ParseException {
        List<VehicleRecord> vehicles = vehicleCatalog.getFilteredList(filter, value);
        List<ClientRecord> clients = clientController.getAllClientRecord();
        ModelAndView model = new ModelAndView("vehicleCatalog");


        if (filter.equals("overdue")) {
            vehicles = vehicleCatalog.getOverDueParticularDay(Only_Date);
        } else if (filter.equals("due")) {
            vehicles = vehicleCatalog.getDueParticularDay(Only_Date);

        } else if (filter.equals("available")) {
            vehicles = vehicleCatalog.getAvailablabilityBetweenDates(selectfromdate, selecttodate);

        } else if (filter.equals("currentlyout")) {
            vehicles = vehicleCatalog.getCurrentlyOutVehciles();

        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }
        model.addObject("vehicles", vehicles);
        model.addObject("clients", clients);
        return model;
    }

    /**
     * Method for filtering transaction history
     *
     * @param filter
     * @param value
     * @return
     */
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
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * Method for displaying all transaction history
     *
     * @return transactions view
     */
    @RequestMapping("/trans-list")
    public ModelAndView displayAllTransactions() {

        List<VehicleRecord> vehicles = vehicleCatalog.getAllVehicleRecord();
        List<TransactionHistory> transactionsList = transactionCatalog.getAllTransactionHistory();
        ModelAndView model = new ModelAndView("transactions");
        model.addObject("transactionsList", transactionsList);
        model.addObject("vehicles", vehicles);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * This Url is used to get the overdue transactions from the history
     *
     * @return
     */
    @RequestMapping("/overdue-trans-list")
    public ModelAndView displayAllOverdueTransactions() {
        List<TransactionHistory> transactionsList = transactionCatalog.getOverDueTransactionHistory();
        ModelAndView model = new ModelAndView("transactions");
        model.addObject("transactionsList", transactionsList);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
            model.addObject("disableButton", 1);
        }

        return model;
    }

    /**
     * Url used to fetch the transactions due today
     *
     * @return
     */
    @RequestMapping("/due-today-trans-list")
    public ModelAndView displayAllDueTodayTransactions() {
        List<TransactionHistory> transactionsList = transactionCatalog.getDueTodayTransactionHistory();
        ModelAndView model = new ModelAndView("transactions");
        model.addObject("transactionsList", transactionsList);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().iterator().next().toString().equalsIgnoreCase("ROLE_ADMINISTRATOR")) {
            model.addObject("disableButton", 0);
        } else {
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
        /*
         * userRegister.addUser(new Administrator("admin", "admin"));
         * userRegister.addUser(new Clerk("clerk", "clerk")); userRegister.addUser(new
         * Clerk("super_clerk", "clerk"));
         */
        userRegister.setUserRegisterObject();
        clientController.loadClientRecords();
        vehicleCatalog.loadVehicleRecords();

        // Adding some hard coded vehicles to populate the views.
//		VehicleRecord v1 = new VehicleRecord("ABD_636", "SUV", "Jeep", "Mercedes Rover", 2019, "Gold");
//		this.vehicleCatalog.addVehicleRecord(v1);
//
//		this.vehicleCatalog.addVehicleRecord(new VehicleRecord("UDF_126", "SUV", "Jeep", "Hummer", 2019, "Yellow"));
//
//		VehicleRecord v2 = new VehicleRecord("ABE_636", "Sedan", "Audi", "A8", 2011, "Red");
//		this.vehicleCatalog.addVehicleRecord(v2);
//
//		this.vehicleCatalog.addVehicleRecord(new VehicleRecord("ABF_636", "Sedan", "Audi", "Q7", 2014, "Black"));
//
//		// Adding some hard coded clients to populate the views.
//		ClientRecord c1 = new ClientRecord("A-1234-123456-12", "Dominick", "Cobb", "(438) 566-9999", "2039-10-1");
//		this.clientController.addClientRecord(c1);
//
//		this.clientController.addClientRecord(new ClientRecord("A-1234-123456-13", "Robert", "Fischer", "(438) 566-9999", "2029-11-1"));
//		this.clientController.addClientRecord(new ClientRecord("A-1234-123456-14", "Mal", "Cobb", "(438) 566-9999", "2029-12-1"));
//
//		ClientRecord c2 = new ClientRecord("A-1234-123456-15", "Stephen", "Miles", "(438) 566-9999", "2059-11-1");
//		this.clientController.addClientRecord(c2);
//		this.clientController.addClientRecord(new ClientRecord("A-1234-123456-16", "Ariadne", "Fischer", "(438) 566-9999", "2079-11-1"));
//
//		// Adding some hard coded transactions to populate the views.
//		v1.addTransaction(new Transaction(c1, v1, "2019-11-01", "2019-11-15", Transaction.Status.Reserved));
//		v1.addTransaction(new Transaction(c2, v1, "2019-10-10", "2019-12-30", Transaction.Status.Rented));
//		v1.addTransaction(new Transaction(c2, v1, "2019-08-10", "2019-10-22", Transaction.Status.Rented));
//
//		v2.addTransaction(new Transaction(c2, v2, "2020-02-1", "2020-02-14", Transaction.Status.Reserved));
//		v2.addTransaction(new Transaction(c1, v2, "2019-09-1", "2019-10-30", Transaction.Status.Reserved));

    }

    /**
     * Checks if vehicle exists in the list
     *
     * @param vehicleRecordList
     * @param vehicleRecord
     * @return
     */
    public boolean checkIfVehicleExists(List<VehicleRecord> vehicleRecordList, VehicleRecord vehicleRecord) {
        boolean recordExists = false;
        for (int i = 0; i < vehicleRecordList.size(); i++) {
            if (vehicleRecordList.get(i).getLpr().equals(vehicleRecord.getLpr())) {
                recordExists = true;
                break;
            }
        }

        return recordExists;
    }
}
