package com.cognizant.model.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.model.PensionDetail;

@SpringBootTest
public class PensionDetailTest {


	@Test
	void NoArgsPensionDetailTest()
	{
		assertThat(new PensionDetail()).isNotNull();
	}
	
	@Test
	void AllArgsPensionDetailTest()
	{
		PensionDetail pensionDetail=new PensionDetail("Professor", 52131.0,550.00);
		assertThat(assertThat(pensionDetail).isNotNull());
	}
	
	@Test
	void AllSetterPensionDetailTest()
	{
		PensionDetail pensionDetail=new PensionDetail() ;
		pensionDetail.setName("Nairobi");
		pensionDetail.setPensionAmount(40000);
		assertThat(assertThat(pensionDetail).isNotNull());
	}
	
	@Test
	void AllGetterPensionDetailTest()
	{
		PensionDetail pensionDetail=new PensionDetail("Professor", 52131.0,500.0);
		String name = pensionDetail.getName() ;
		Double pa = pensionDetail.getPensionAmount() ;
		assertEquals("Professor", name) ;
		assertEquals(52131.0, pa) ;
	}
	
	
}
