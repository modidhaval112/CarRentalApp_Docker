package com.soen6461.carrentalapplication.controller;

import com.soen6461.carrentalapplication.model.VehicleRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VehicleCatalog {

    private List<VehicleRecord> vehicleRecordList = new ArrayList<VehicleRecord>();
    private static VehicleCatalog instance = null;

    private VehicleCatalog() {
    }


    @PostMapping(value = "/get-all-vehicle-records")
    public List getAllVehicleRecord() {
        // To protect the main vehicle record list, only a copy is provided.
        //   this avoids someone other than this class from adding or removing vehicles.

        List<VehicleRecord> copy = new ArrayList<VehicleRecord>();
        for (VehicleRecord vehicle : vehicleRecordList) {
            copy.add(vehicle);
        }

        return copy;
    }

    @PostMapping(value = "/get-filtered-vehicle-list")
    public List getFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
        return this.getResultSet(filter, value);
    }

    @PostMapping(value = "/get-filtered-greater-vehicle-list")
    public List getGreaterThanFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
        return this.getResultSet(filter, value);
    }

    @PostMapping(value = "/get-filtered-lesser-vehicle-list")
    public List getLesserThanFilteredList(@RequestParam(name = "filter") String filter, @RequestParam(name = "value") String value) {
        return this.getResultSet(filter, value);
    }

    public List<VehicleRecord> getResultSet(String filter, String value) {

        List<VehicleRecord> temp = new ArrayList<>();
        if (filter.equals("make"))  {
            for (int i = 0; i < vehicleRecordList.size(); i++) {
                if (vehicleRecordList.get(i).getMake().equals(value)) {
                    temp.add(vehicleRecordList.get(i));
                }
            }

        } else if (filter.equals("model")) {
            for (int i = 0; i < vehicleRecordList.size(); i++) {
                if (vehicleRecordList.get(i).getModel().equals(value)) {
                    temp.add(vehicleRecordList.get(i));
                }

            }
        } else if (filter.equals("carType")) {
            for (int i = 0; i < vehicleRecordList.size(); i++) {
                if (vehicleRecordList.get(i).getCarType().equals(value)) {
                    temp.add(vehicleRecordList.get(i));
                }
            }
        } else if (filter.equals("color")) {
            for (int i = 0; i < vehicleRecordList.size(); i++) {
                if (vehicleRecordList.get(i).getColor().equals(value)) {
                    temp.add(vehicleRecordList.get(i));
                }

            }
        }
        return temp;
    }

    /**
     * Gets a vehicle that has the give license plate number.
     * @param licensePlateNumber The license plate number.
     */
    public VehicleRecord getVehicleRecord(String licensePlateNumber)
    {
        for (int i = 0; i < vehicleRecordList.size(); i++) {
            if (vehicleRecordList.get(i).getLpr() == licensePlateNumber) {
                return vehicleRecordList.get(i);
            }
        }

        return null;
    }

    /**
     * Add vehicle records to the catalog
     * //TODO: Protect this method against unauthorised access from clerk.
     * @param vehicleRecord
     */
    public void addVehicleRecord(VehicleRecord vehicleRecord)
    {
        this.vehicleRecordList.add(vehicleRecord);
    }
}
