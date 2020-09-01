package com.together.smwu.domain.group.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "GROUP")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long id;

    @Column(name = "title", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String title;

    @Column(name = "content")
    @Size(max = 255)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "credential")
    private String credential;

    @Column(name = "created_time", nullable = false)
    private Timestamp createdTime;

}
