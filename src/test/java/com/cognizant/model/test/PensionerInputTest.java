package com.cognizant.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.model.PensionerInput;

@SpringBootTest
public class PensionerInputTest {
	
	PensionerInput pensionerInput = new PensionerInput() ;
	
	@Test
	void checkBeanCreation() {
		assertNotNull(pensionerInput) ;
	}
	
	@Test
	void noArgsConstructorTest() {
		PensionerInput pensionerInput1 = new PensionerInput() ;
		assertNotNull(pensionerInput1) ;
	}
	
	@Test
	void allArgsConstructorTest() {
		PensionerInput pensionerInput1 = new PensionerInput("12423543625625") ;
		assertNotNull(pensionerInput1) ;
	}	
	
	@Test
	void getterTest() {
		PensionerInput pensionerInput1 = new PensionerInput() ;
		pensionerInput1.setAadhaarNumber("1342353463565");
		
		assertNotNull(pensionerInput1) ;
	}
	
	@Test
	void allGettersTest() {
		PensionerInput pensionerInput1 = new PensionerInput( "12423543625625") ;
		
		assertEquals("12423543625625", pensionerInput1.getAadhaarNumber()) ; 
	}	

}








