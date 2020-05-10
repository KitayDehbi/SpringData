package com.master4.repositories;

import com.master4.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Page<Tag> findAll(Pageable pageable);
    @Query("select t from Tag t where t.title=:title")
    Tag findTagByName(@Param("title") String name);
}
