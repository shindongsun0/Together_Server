package com.together.smwu.domain.groupPlace.dao;

import com.together.smwu.domain.group.domain.Group;
import com.together.smwu.domain.groupPlace.domain.GroupPlace;
import com.together.smwu.domain.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupPlaceRepository extends JpaRepository<GroupPlace, Long> {

    Optional<GroupPlace> findByGroupAndPlace(Group group, Place place);

    List<GroupPlace> findAllByGroup(Group group);

    List<GroupPlace> findAllByPlace(Place place);
}
