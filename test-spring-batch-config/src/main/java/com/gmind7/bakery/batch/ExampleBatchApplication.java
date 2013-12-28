package com.gmind7.bakery.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleBatchApplication {
	
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	
	@Autowired
	private ExampleItemReader exampleItemReader;
	
	@Autowired
	private ExampleItemWriter<Object> exampleItemWriter;
	
	@Autowired
	private JobParametersIncrementer incrementer;
	
	@Bean
	protected Job exampleJob() throws Exception {
		return jobs.get("exampleJob").
			        incrementer(new RunIdIncrementer()).
			        start(exampleStep()).
			        build();
	}

	@Bean
	protected Step exampleStep() throws Exception {
		return steps.get("step").<String, Object> chunk(1).reader(reader()).writer(writer()).faultTolerant()
                .retry(Exception.class).retryLimit(3).build();
	}

	@Bean
	protected ItemReader<String> reader() {
		return exampleItemReader;
	}

	@Bean
	protected ItemWriter<Object> writer() {
		return exampleItemWriter;
	}
	
	@Bean
    protected Tasklet tasklet() {
        return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				return RepeatStatus.FINISHED;
			}
        };
    }
	
}
