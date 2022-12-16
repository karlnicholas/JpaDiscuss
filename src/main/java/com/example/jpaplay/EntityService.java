package com.example.jpaplay;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityService {
    private final BaseEntityRepo baseEntityRepo;
    private final BaseChildEntityRepo baseChildEntityRepo;
    private final DetailEntityRepo detailEntityRepo;

    public EntityService(BaseEntityRepo baseEntityRepo, BaseChildEntityRepo baseChildEntityRepo, DetailEntityRepo detailEntityRepo) {
        this.baseEntityRepo = baseEntityRepo;
        this.baseChildEntityRepo = baseChildEntityRepo;
        this.detailEntityRepo = detailEntityRepo;
    }

    @Transactional
    public void deleteEntity() {
        detailEntityRepo.findById(2L).ifPresent(detailEntity -> {
            detailEntityRepo.delete(detailEntity);
            baseEntityRepo.delete(detailEntity.getBaseEntity());
        });
    }

    @Transactional
    public void readBaseAndChildrenEntityNoUpdate() {
        baseEntityRepo.findByIdWithChildren(1L).ifPresent(be->{
            System.out.println(be);
            baseEntityRepo.save(be);
        });
    }

    @Transactional
    public void readBaseAndChildrenEntityUpdateChild() {
        baseEntityRepo.findByIdWithChildren(1L).ifPresent(be->{
            System.out.println(be);
//            be.setName("BC");
            be.getChildren().get(0).setName("C1C");
            baseEntityRepo.save(be);
        });
    }

//    @Transactional
    public void naiveReadAndUpdate() {
        naiveReadAndUpdateMethod("upn1");
    }

    @Transactional
    public void naiveReadAndUpdateTransactional() {
        naiveReadAndUpdateMethod("upn2");
    }

    private void naiveReadAndUpdateMethod(String updValue) {

        baseChildEntityRepo.findById(2L).ifPresent( baseChildEntity-> {
            baseChildEntity.setName(updValue);
            baseChildEntityRepo.save(baseChildEntity);
        });
        BaseEntity baseEntity = baseEntityRepo.findById(1L).get();
        baseEntity.setName(updValue);

        List<BaseChildEntity> children = baseChildEntityRepo.findAllByBaseEntity_Id(baseEntity.getId());
        children = children.stream().filter(ch->ch.getId() != 2L).collect(Collectors.toList());
        children.forEach(ch->{
            ch.setName(updValue);
            baseChildEntityRepo.save(ch);
        });

        baseEntityRepo.save(baseEntity);
    }


    //    @Transactional
    public void smartReadAndUpdate() {
        smartReadAndUpdateMethod("ups1");
    }
    @Transactional
    public void smartReadAndUpdateTransactional() {
        smartReadAndUpdateMethod("ups2");
    }

    private void smartReadAndUpdateMethod(String updValue) {
        BaseEntity baseEntity = baseEntityRepo.findByIdWithChildren(1L).get();
        baseEntity.setName(updValue);
        baseEntity.getChildren().forEach(ch->ch.setName(updValue));
        baseEntityRepo.save(baseEntity);
    }

}
