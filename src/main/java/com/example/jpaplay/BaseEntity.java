package com.example.jpaplay;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // there is a detail entity
    @OneToMany(mappedBy = "baseEntity", cascade = CascadeType.ALL)
    @OrderBy("childrenOrder ASC")
    private List<BaseChildEntity> children;

    private String name;

    @Override
    public String toString() {
        return "BaseEntity: " + id + " " + name + " " + (Persistence.getPersistenceUtil().isLoaded(children) ? children.toString(): "[]");
    }
}
