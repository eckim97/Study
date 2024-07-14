package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> chlid = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"), //조인 테이블에서 현재 엔티티에 대한 외래키를 지정
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID") //조인 테이블의 반대쪽 엔티티에 대한 외래키 설정
    )
    private List<Item> items = new ArrayList<>();
}
