package com.pyc.campus.batch;

import com.pyc.campus.domain.Grade;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file CsvBatchConfig
 * @pack com.pyc.campus.batch
 * @date 2021/1/17
 * @time 13:03
 * @E-mail 2923616405@qq.com
 **/

@Configuration
@EnableBatchProcessing
public class CsvBatchConfig {
    @Bean
    @StepScope
    public FlatFileItemReader<Grade> reader(@Value("#{jobParameters['input.file.name']}")String pathToFile) throws Exception{
        FlatFileItemReader<Grade> reader = new FlatFileItemReader<>();
        reader.setResource(new PathResource(pathToFile));
        reader.setLineMapper(new DefaultLineMapper<Grade>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames("courseCode","courseName","credit",
                        "gpa","grade","learHour","name","studentID","term");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Grade>(){{
                setTargetType(Grade.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<Grade,Grade> csvProcessor(){
        CsvItemProcessor processor = new CsvItemProcessor();
        processor.setValidator(csvBeanValidator());
        return processor;
    }

    @Bean
    public ItemWriter<Grade> writer(DataSource dataSource){
        JdbcBatchItemWriter<Grade> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Grade>());
        String sql = "insert into grade" + "(course_code,course_name,credit,gpa,grade,learn_hour,name,studentid,term)"
                + "values(:courseCode,:courseName,:credit,:gpa,:grade,:learnHour,:name,:studentID,:term)";
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JobRepository myJobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
            throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType("mysql");
        return jobRepositoryFactoryBean.getObject();
    }

    @Bean
    public SimpleJobLauncher myJobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager)
            throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(myJobRepository(dataSource, transactionManager));
        return jobLauncher;
    }

    @Bean
    public CsvJobListener csvJobListener() {
        return new CsvJobListener();
    }

    @Bean
    public Validator<Grade> csvBeanValidator() {
        return new CsvBeanValidator<Grade>();
    }

    @Bean
    public Job myImportJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1) //1
                .end()
                .listener(csvJobListener()) //2
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Grade> reader, ItemWriter<Grade> writer,
                      ItemProcessor<Grade,Grade>processor) {
        return stepBuilderFactory
                .get("step1")
                .<Grade, Grade>chunk(65000) //1
                .reader(reader) //2
                .processor(processor)//3
                .writer(writer) //4
                .build();
    }
}
