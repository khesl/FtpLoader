package com.khesl.ftploader.FtpLoader.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    Collection<Visit> findByUserName(String userName);
}
