package com.together.smwu.web.repository.group.place;

import com.together.smwu.web.repository.group.Group;
import com.together.smwu.web.repository.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupPlaceRepository extends JpaRepository<GroupPlace, Long> {

    Optional<GroupPlace> findByGroupAndPlace(Group group, Place place);

    List<GroupPlace> findAllByGroup(Group group);

    List<GroupPlace> findAllByPlace(Place place);
}
