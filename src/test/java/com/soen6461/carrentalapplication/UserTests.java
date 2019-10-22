package com.soen6461.carrentalapplication;

import com.soen6461.carrentalapplication.config.UserRegister;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserRegister userRegister;

    /**
     * Check for the class loading
     * @throws Exception Throw the junit fail error
     */
    @Test
    public void contextLoads() throws Exception {
        assertThat(this.userRegister).isNotNull();
    }

    /**
     * Check for the class loading
     * @throws Exception Throw the junit fail error
     */
    @Test
    public void AddUserTest() throws Exception {
        Assert.assertTrue(this.userRegister.getAllClerks().size() == 2);
    }
}
