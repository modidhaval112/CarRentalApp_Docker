package com.soen6461.carrentalapplication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	CarRentalClientTests.class,
	FrontendActionTest.class,
	VehicleTests.class
})

public class JunitTestSuite  {   
}  	