package com.soen6461.carrentalapplication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	ClientTests.class,
	FrontendActionTest.class,
	 VehicleTests.class,
		UserTests.class
})

public class JunitTestSuite  {   
}  	