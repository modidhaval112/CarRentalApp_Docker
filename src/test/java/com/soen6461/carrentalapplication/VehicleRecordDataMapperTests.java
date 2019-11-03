package com.soen6461.carrentalapplication;


import com.soen6461.carrentalapplication.model.VehicleRecord;
import com.soen6461.carrentalapplication.model.VehicleRecordDataMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleRecordDataMapperTests {

    @Test
    public void CRUDTest() {
        VehicleRecordDataMapper vrdm = new VehicleRecordDataMapper();
        vrdm.setIsDataMapperTest(true);

        VehicleRecord vr = new VehicleRecord("TES_123", "SUV", "Jeep", "Hummer", 2019, "Yellow");

        vrdm.insert(1, vr);

        vrdm.clearTestingData();
    }
}
