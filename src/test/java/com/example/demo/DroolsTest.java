package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.drools.core.definitions.rule.impl.RuleImpl;
import org.drools.model.codegen.ExecutableModelProject;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drools.test.Model;


public class DroolsTest {

	@Test
	public void test() {
		try {
		
	    final Logger log = LoggerFactory.getLogger(DroolsTest.class);

		KieContainer kContainer = createKieContainer();

		kContainer.getKieBase().getKiePackages().stream().forEach(obj -> obj.getRules().stream().forEach(innerObj -> {
			log.info("Rules contain this Agenda group: {}", ((RuleImpl) innerObj).getAgendaGroup());
		}));

		log.info("Creating kieBase");
		KieBase kieBase = kContainer.getKieBase();

		log.info("There should be rules: ");
		for (KiePackage kp : kieBase.getKiePackages()) {
			for (Rule rule : kp.getRules()) {
				log.info("kp " + kp + " rule " + rule.getName());
			}
		}

		log.info("Creating kieSession");
		KieSession session = kieBase.newKieSession();

			log.info("Populating globals");
		//	Set<String> check = new HashSet<String>();
		//	session.setGlobal("controlSet", check);

			log.info("Now running data");

			Model benefitRules = Model.builder().build();
	        benefitRules.setLevel("Platinum");
	        benefitRules.setRegion("US");
	        benefitRules.setBrand("RH");
	        benefitRules.setPropertyNumber("13");
	        benefitRules.setResort("N");
	        System.out.println("SSSS");
	        
	
			session.insert(benefitRules);
			session.getAgenda().getAgendaGroup("G1").setFocus();
			session.fireAllRules();

			log.info("Final checks");

		//	assertEquals("Size of object in Working Memory is 3", 3, session.getObjects().size());
		//	assertTrue("contains red", check.contains("red"));
		//	assertTrue("contains green", check.contains("green"));
			//assertTrue("contains blue", check.contains("blue"));
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			//session.dispose();
		}
	}

	private KieContainer createKieContainer() {
		// Programmatically collect resources and build a KieContainer
		KieServices ks = KieServices.Factory.get();
		KieFileSystem kfs = ks.newKieFileSystem();
		String packagePath = "org.drools.demo".replace(".", "/");
		kfs.write("src/main/resources/benefitRules_v2.xlsx", ks.getResources().newInputStreamResource(
				this.getClass().getClassLoader().getResourceAsStream("benefitRules_v2.xlsx")));
		
		
		ReleaseId releaseId = ks.newReleaseId("org.drools.demo", "demo20230212-so75416633", "1.0-SNAPSHOT");
		kfs.generateAndWritePomXML(releaseId);
		ks.newKieBuilder(kfs).buildAll();
		return ks.newKieContainer(releaseId);
	}

}
