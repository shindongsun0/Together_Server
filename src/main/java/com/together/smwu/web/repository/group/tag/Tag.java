package com.together.smwu.web.repository.group.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long tagId;

    @Column(name = "name", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String name;
}
