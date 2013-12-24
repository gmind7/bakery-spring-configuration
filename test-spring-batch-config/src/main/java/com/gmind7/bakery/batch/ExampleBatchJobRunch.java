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
public class ExampleBatchJobRunch {
	
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	
	@Autowired
	public JobParametersIncrementer incrementer;
	
	@Bean
	public Job exampleJob() throws Exception {
		return jobs.get("exampleJob").
			        incrementer(new RunIdIncrementer()).
			        start(exampleStep()).
			        build();
	}

	@Bean
	protected Step exampleStep() throws Exception {
		return steps.get("exampleSetp").
			          <String, Object> chunk(1).
			          reader(reader()).
			          writer(writer()).
			          build();
	}

	@Bean
	protected ItemReader<String> reader() {
		return new ExampleItemReader();
	}

	@Bean
	protected ItemWriter<Object> writer() {
		return new ExampleItemWriter();
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
