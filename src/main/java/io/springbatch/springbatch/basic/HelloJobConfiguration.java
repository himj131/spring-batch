package io.springbatch.springbatch.basic;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("HelloJob")
                .start(helloStep1()) //가장 먼저 시작될 step
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1(){
        return stepBuilderFactory.get("HelloStep1")
                .tasklet(new Tasklet() {
                    @Override //기본적으로 step에서 tasklet을 무한반복 시킨다
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("======================");
                        System.out.println(">> hello spring batch!");
                        System.out.println("======================");
                        return RepeatStatus.FINISHED; // 한번만 실행시킴
                    }
                }).build();
    }

    @Bean
    public Step helloStep2(){
        return stepBuilderFactory.get("HelloStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("======================");
                        System.out.println("step2 started!");
                        System.out.println("======================");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

}
