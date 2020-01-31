package com.ust.batchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HellowWorldJobConfig {

	@Bean
	public Job helloWorldJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		return jobBuilderFactory.get("helloWorldJob")
			.start(helloWorldStep(stepBuilderFactory))
			.build();
	}

	@Bean
	public Step helloStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("helloWorldStep")
			.<Person, String>chunk(20)
			.reader()
			.processor()
			.writer()
			.build();
	}
}