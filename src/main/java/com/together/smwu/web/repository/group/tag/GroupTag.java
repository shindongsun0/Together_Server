package com.together.smwu.web.repository.group.tag;

import com.together.smwu.web.repository.group.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "GROUP_TAG")
public class GroupTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_tag_id")
    private long groupTagId;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
