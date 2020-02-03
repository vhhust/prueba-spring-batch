package com.ust.batchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.ust.batchdemo.model.Person;
import com.ust.batchdemo.model.PersonItemProcessor;

@Configuration
public class HelloWorldJobConfig {

	@Bean
	public Job helloWorldJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		return jobBuilderFactory.get("helloWorldJob").start(helloWorldStep(stepBuilderFactory)).build();
	}

	@Bean
	public Step helloWorldStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("helloWorldStep").<Person, String>chunk(10).reader(reader())
				.processor(processor()).writer(writer()).build();
	}

	@Bean
	public FlatFileItemReader<Person> reader() {
		return new FlatFileItemReaderBuilder<Person>().name("personItemReader")
				.resource(new FileSystemResource("src/main/resources/people.csv")).delimited()
				.delimiter(",").names(new String[] { "firstName", "lastName" }).targetType(Person.class).build();
	}

	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public FlatFileItemWriter<String> writer() {
		return new FlatFileItemWriterBuilder<String>().name("greetingItemWriter")
				.resource(new FileSystemResource("target/test-outputs/greetings.txt"))
				.lineAggregator(new PassThroughLineAggregator<>()).build();
	}
}
