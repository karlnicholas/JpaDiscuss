package com.example.jpaplay;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseChildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // there is a detail entity
    @ManyToOne
    private BaseEntity baseEntity;

    private String name;

    private int childrenOrder;

    @Override
    public String toString() {
        return "bec: " + id + " " + name;
    }

}
