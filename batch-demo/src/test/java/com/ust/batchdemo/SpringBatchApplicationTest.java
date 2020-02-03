package com.ust.batchdemo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ust.batchdemo.config.BatchConfig;
import com.ust.batchdemo.config.HelloWorldJobConfig;

@SpringBootTest(classes = SpringBatchApplicationTest.BatchTestConfig.class)
class SpringBatchApplicationTest {

	@Autowired
	private JobLauncherTestUtils jltu;

	@Test
	public void testHelloWorldJob() throws Exception {
		JobExecution jobExecution = jltu.launchJob();
		assertTrue(jobExecution.getExitStatus().getExitCode().equals("COMPLETED"));
	}

	@Configuration
	@Import({BatchConfig.class, HelloWorldJobConfig.class})
	static class BatchTestConfig {
		@Autowired
		private Job helloWorldJob;
		
		@Bean
		JobLauncherTestUtils jobLauncherTestUtils() throws NoSuchJobException {
			JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
			jobLauncherTestUtils.setJob(helloWorldJob);
			return jobLauncherTestUtils;
		}
	}
}
