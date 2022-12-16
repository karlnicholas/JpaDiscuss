package com.example.jpaplay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableRetry
@EnableTransactionManagement
public class JpaDiscussApplication implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(JpaDiscussApplication.class, args);
    }

    @Autowired
    BaseEntityRepo baseEntityRepo;
    @Autowired
    BaseChildEntityRepo baseChildEntityRepo;
    @Autowired
    DetailEntityRepo detailEntityRepo;
    @Autowired
    EntityService entityService;

    public void run(ApplicationArguments args) throws InterruptedException {
        initEntities();
        readBaseEntity();
        readDetailEntity();
        naiveReadAndUpdate();
        smartReadAndUpdate();
        System.out.println("CCCC");
        entityService.deleteEntity();
    }

    private void smartReadAndUpdate() {
        System.out.println("smartReadAndUpdate");
        entityService.smartReadAndUpdate();
        System.out.println("smartReadAndUpdateTransactional");
        entityService.smartReadAndUpdateTransactional();
    }

    private void naiveReadAndUpdate() {
        System.out.println("naiveReadAndUpdate");
        entityService.naiveReadAndUpdate();
        System.out.println("naiveReadAndUpdateTransactional");
        entityService.naiveReadAndUpdateTransactional();
    }

    private void readDetailEntity() {
        detailEntityRepo.findById(4L).ifPresent(System.out::println);
    }

    private void readBaseEntity() {
        baseEntityRepo.findById(1L).ifPresent(System.out::println);
    }

    private void initEntities() {
        BaseEntity baseEntity = BaseEntity.builder().name("B").build();
        baseEntity = baseEntityRepo.save(baseEntity);
        BaseChildEntity baseChildEntity1 = BaseChildEntity.builder().baseEntity(baseEntity).name("C1").build();
        baseChildEntity1 = baseChildEntityRepo.save(baseChildEntity1);
        BaseChildEntity baseChildEntity2 = BaseChildEntity.builder().baseEntity(baseEntity).name("C2").build();
        baseChildEntity2 = baseChildEntityRepo.save(baseChildEntity2);
        DetailEntity detailEntity = DetailEntity.builder().baseEntity(baseEntity).build();
        detailEntityRepo.save(detailEntity);
    }
}
