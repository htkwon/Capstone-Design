package com.hansung.hansungcommunity.repository;


import com.hansung.hansungcommunity.entity.Adopt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdoptRepository extends JpaRepository<Adopt, Long> {


    @Query(value = "SELECT a FROM Adopt a GROUP BY a.user ORDER BY COUNT(a) DESC")
    List<Adopt> findTop5UsersByAdoptCount();

}
