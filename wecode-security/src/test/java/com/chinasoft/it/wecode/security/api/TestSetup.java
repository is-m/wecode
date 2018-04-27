package com.chinasoft.it.wecode.security.api;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestSetup {

	@Autowired
	private Environment env;
	
	@PersistenceUnit
	private EntityManagerFactory emFactory;
	
	@Test
	public void setup() { 
		System.out.println("setup");
		if(!env.acceptsProfiles("dev")) {
			
		}
		
	}
}
