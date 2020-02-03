package com.ust.batchdemo.model;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, String> {

	@Override
	public String process(Person person) throws Exception {
		String greeting = "Hello " + person.getFirstName() + " " + person.getLastName() + "!";

		log.info("converting '{}' into '{}'", person, greeting);

		return greeting;
	}

}
