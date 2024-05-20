package com.team2.resumeeditorproject.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SimpleJobConfiguration extends DefaultBatchConfiguration { // @EnableBatchProcessing deprecated

    @Bean //Batch Job (하나의 배치 작업 단위)
    public Job simpleJob1(JobRepository jobRepository, Step simpleStep1) { // JobBuilderFactory deprecated
        return new JobBuilder("simpleJob", jobRepository)
                .start(simpleStep1) // 하나의 Job 안에 여러 개의 step이 존재한다.
                .build();
    }
    @Bean // Batch Step
    public Step simpleStep1(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager transactionManager){
        return new StepBuilder("simpleStep1", jobRepository)
                .tasklet(testTasklet, transactionManager)
                .build(); // Step 안에 Tasklet 혹은 Reader & Processor & Writer 묶음이 존재한다.
    }
    @Bean
    public Tasklet testTasklet(){ // 개발자 커스텀 코드로 작성한다.
        return ((contribution, chunkContext) -> { // step 내에서 수행할 기능을 명시한다. Chunk 지향 프로세싱를 담당하는 객체
            log.info(">>>>> This is Step1"); // Batch가 수행되면 출력할 내용.

            return RepeatStatus.FINISHED;
        });
    }
}