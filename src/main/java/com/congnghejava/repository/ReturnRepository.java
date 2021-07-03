package com.congnghejava.repository;

import com.congnghejava.model.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface ReturnRepository extends JpaRepository<Return, Long>{

    @Query("select r from Return r where r.createdDate like CONCAT('%',:my,'%') ")
    Iterable<Return> getReturnByMonth(@Param("my") String my);

    @Query("select day(r.createdDate) as day, count(r.id) as number from Return r where r.createdDate like CONCAT('%',:my,'%') group by day")
    ArrayList<ArrayList> getReturnByMonthGroupByDay(@Param("my") String my);
}
