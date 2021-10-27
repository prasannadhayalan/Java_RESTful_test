/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package user;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import config.ComponentITConfig;
import domain.UserDomain;
import repository.UserRepository;
import service.UserHandler;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={UserControllerTests.class})
@AutoConfigureMockMvc
@ContextConfiguration(classes = ComponentITConfig.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHandler userHandler;

    @After
    public void after() {
        cleanUp();
    }

    @Before
    public void before() {
        cleanUp();
    }

    private void cleanUp() {
        mongoTemplate.dropCollection(
                UserDomain.class);
    }


    @Test
    public void testAddUser(){
        UserDomain userDomain = new UserDomain();
        userDomain.setFirstName("Test");
        userDomain.setLastName("John");
        userDomain.setLatitude(10.8);
        userDomain.setLongitude(11.3);
        UserDomain userDomain1 = userRepository.createUser(userDomain);
        UserDomain userDomain2 = userRepository.findUser(userDomain1.getId());
        Assert.assertNotNull(userDomain2.getId());
        Assert.assertEquals("Test",userDomain2.getFirstName());
    }

    @Test
    public void testUpdateUser(){
        UserDomain userDomain = new UserDomain();
        userDomain.setFirstName("Test");
        userDomain.setLastName("John");
        userDomain.setLatitude(10.8);
        userDomain.setLongitude(11.3);
        UserDomain userDomain1 = userRepository.createUser(userDomain);
        userDomain.setFirstName("Test1");
        UserDomain userDomain2 = userRepository.updateUser(userDomain1.getId(),userDomain);
        Assert.assertNotNull(userDomain2.getId());
        Assert.assertEquals("Test1",userDomain2.getFirstName());
    }

    @Test
    public void testGetDistanceWithZeroUser(){
        UserDomain userDomain = new UserDomain();
        userDomain.setFirstName("Test");
        userDomain.setLastName("John");
        userDomain.setLatitude(10.8);
        userDomain.setLongitude(11.3);
        String output = userHandler.getDistances();
        Assert.assertNull(output);
    }
    @Test
    public void testGetDistanceWithOneUser(){
        UserDomain userDomain = new UserDomain();
        userDomain.setFirstName("Test");
        userDomain.setLastName("John");
        userDomain.setLatitude(10.8);
        userDomain.setLongitude(11.3);
        userRepository.createUser(userDomain);
        String output = userHandler.getDistances();
        Assert.assertNull(output);
    }

    @Test
    public void testGetDistanceWithTwoUser(){
        UserDomain userDomain = new UserDomain();
        userDomain.setFirstName("Test");
        userDomain.setLastName("John");
        userDomain.setLatitude(10.8);
        userDomain.setLongitude(11.3);
        userRepository.createUser(userDomain);
        UserDomain userDomain1 = new UserDomain();
        userDomain1.setFirstName("Test1");
        userDomain1.setLastName("John1");
        userDomain1.setLatitude(20.8);
        userDomain1.setLongitude(22.3);
        userRepository.createUser(userDomain1);
        String output = userHandler.getDistances();
        Assert.assertNotNull(output);
    }

    @Test
    public void testGetDistanceWithMultipleUser() throws JSONException {
        UserDomain userDomain = new UserDomain( "Prasana", "D", 11.6113, 79.6502);
        UserDomain userDomain1 = new UserDomain( "Haja", "J", 11.3410, 77.7172);
        UserDomain userDomain2 = new UserDomain("Yuvi", "V", 13.067439, 80.237617);
        UserDomain userDomain3 = new UserDomain("sheik", "M", 11.9416, 79.8083);

        Map<String,NearByUsersDetails> stringNearByUsersDetailsMap = new HashMap<>();
        NearByUsersDetails hajaNearByUsersDetails = new NearByUsersDetails("Prasana", 212.77552048237973, "Yuvi", 334.48501702596405);
        NearByUsersDetails prasannaNearByUsersDetails = new NearByUsersDetails("sheik", 40.55985678165402, "Haja", 212.77552048237973);
        NearByUsersDetails sheikNearByUsersDetails = new NearByUsersDetails("Prasana", 40.55985678165402, "Haja", 237.3253802595011);
        NearByUsersDetails yuviNearByUsersDetails = new NearByUsersDetails("sheik", 133.581141767467, "Haja", 334.48501702596405);
        stringNearByUsersDetailsMap.put("Haja", hajaNearByUsersDetails);
        stringNearByUsersDetailsMap.put("Prasana", prasannaNearByUsersDetails);
        stringNearByUsersDetailsMap.put("sheik", sheikNearByUsersDetails);
        stringNearByUsersDetailsMap.put("Yuvi", yuviNearByUsersDetails);
        userRepository.createUser(userDomain);
        userRepository.createUser(userDomain1);
        userRepository.createUser(userDomain2);
        userRepository.createUser(userDomain3);
        String output = userHandler.getDistances();
        Assert.assertNotNull(output);
        JSONObject json = new JSONObject(output);
        JSONObject jsonObject  = (JSONObject) json.getJSONArray("userList").get(0);
        String user = jsonObject.getString("user");
        Assert.assertEquals(stringNearByUsersDetailsMap.get(user).getNearestUser(),jsonObject.getString("nearestUser"));
    }


}
