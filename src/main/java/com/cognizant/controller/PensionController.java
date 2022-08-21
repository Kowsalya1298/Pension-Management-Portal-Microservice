package com.cognizant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.exception.ResourceNotFoundException;
import com.cognizant.model.AuthRequest;
import com.cognizant.model.PensionDetail;
import com.cognizant.model.PensionerDetail;
import com.cognizant.model.PensionerInput;
import com.cognizant.model.Token;
import com.cognizant.model.User;
import com.cognizant.restClient.AuthorizationClient;
import com.cognizant.restClient.ProcessPensionClient;

@RestController
@CrossOrigin(origins = "*")
public class PensionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PensionController.class);
    @Autowired
    private AuthorizationClient authorizationClient;
    @Autowired
    private ProcessPensionClient processPensionClient;

    /**
     * @param authRequest username and password
     * @return token if credentials are valid
     */
    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody AuthRequest authRequest) {
	LOGGER.info("STARTED - doLogin");
	String token = null;
	try {
	    token = authorizationClient.login(authRequest);
	    LOGGER.info("GENERATED - Token - " + token.toString());
	} catch (Exception e) {
	    LOGGER.error("EXCEPTION - doLogin");
	    throw new ResourceNotFoundException("Token can't be generated");
	}

	System.out.println(token);
	LOGGER.debug(token);

	LOGGER.info("END - doLogin");
	return ResponseEntity.ok(new Token(token));
    }
    /**
     * @param user username and password
     * @return created user
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerNewUser(@RequestBody User user) {
	LOGGER.info("STARTED - Registration");
	User userDetails = null;
	try {
	    userDetails = authorizationClient.register(user);

	} catch (Exception e) {
	    LOGGER.error("EXCEPTION - Registration");
	    throw new ResourceNotFoundException("User not created");
	}
	LOGGER.info("END - Registration");
	return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }
    
    /**
     * @param token
     * @return all pensioner details if token is valid
     */
    @GetMapping("/details")
    public ResponseEntity<?> allDetail(@RequestHeader(name = "Authorization") String token) {
	LOGGER.info("STARTED - allDetail");
	List<PensionerDetail> pensionerDetail = null;
	if (token != null) {
	    if (authorizationClient.authorization(token)) {
		try {
		    pensionerDetail = processPensionClient.allDetail();

		} catch (Exception e) {
		    new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    throw new ResourceNotFoundException("Pensioner details list not found");
		}
	    } else {
		new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }

	} else {
	    new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	LOGGER.info("END - allDetail");
	return new ResponseEntity<>(pensionerDetail, HttpStatus.OK);

    }

    /**
     * @param token
     * @param pensionerInput user given aadhar number
     * @return pensionser detail of a given aadhar number
     */
    @PostMapping("/pensionerDetail")
    public ResponseEntity<?> getPensionDetail(@RequestHeader(name = "Authorization") String token,
	    @RequestBody PensionerInput pensionerInput) {
	LOGGER.info("STARTED - getPensionDetail");
	PensionerDetail pensionerDetail = null;
	ResponseEntity<?> response = null;
	if(authorizationClient.authorization(token)) {
	    if(!pensionerInput.getAadhaarNumber().isEmpty()) {
		try {
		    response = new ResponseEntity<>( processPensionClient.getPensionerDetail(pensionerInput), HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("EXCEPTION - getPensionDetail");
			new ResponseEntity<>("Aadhar Number not found", HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("Aadhar Number not found");
		    }
	    }else {
		response = new ResponseEntity<>("Enter Valid Aadhar Number", HttpStatus.BAD_REQUEST);
	    }
	}
	else {
	    response = new ResponseEntity<>("Invalid Access Token", HttpStatus.UNAUTHORIZED);
	}
	
	

	LOGGER.info("END - getPensionDetail");
	return response;

    }
    /**
     * @param token
     * @param pensionerInput
     * @return calculated pension details
     */
    @PostMapping("/calculatePension")
    public ResponseEntity<PensionDetail> getPensionerDetail(@RequestHeader(name = "Authorization") String token,
	    @RequestBody PensionerInput pensionerInput) {
	PensionDetail pensionDetail = null;
	LOGGER.info("STARTED - getPensionDetail");
	if (authorizationClient.authorization(token)) {
	    try {
		pensionDetail = processPensionClient.getPensionDetail(token, pensionerInput);
	    } catch (Exception e) {
		LOGGER.error("EXCEPTION - getPensionDetail");
		new ResponseEntity<>("Aadhar Number not found", HttpStatus.NOT_FOUND);
		throw new ResourceNotFoundException("Aadhar Number not found");
	    }
	}
	LOGGER.info("END - getPensionDetail");
	return ResponseEntity.ok(pensionDetail);

    }

}
