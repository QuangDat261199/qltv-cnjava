package com.congnghejava.repository;

import com.congnghejava.model.Borrow;
import com.congnghejava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;


public interface BorrowRepository extends JpaRepository<Borrow, Long>{

    @Query("select b from Borrow b where b.user = :user and b.status = false")
    Borrow getByUser(@Param("user") User user);

    @Query("select day(b.createdDate) as day, count(b.id) as number from Borrow b where b.createdDate like CONCAT('%',:my,'%') group by day")
    ArrayList<ArrayList> getBorrowByMonthGroupByDay(@Param("my") String my);
}
