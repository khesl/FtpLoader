package com.khesl.ftploader.FtpLoader.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class VisitJdbcRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /*@Autowired
    VisitRepository visitRepository;*/

    public Collection<Visit> findAll(String personName){
        //return this.visitRepository.findAll();
        return jdbcTemplate.queryForList("select * from visit",
                Visit.class);
    }


    public Visit findById(long id) {
        return jdbcTemplate.queryForObject("select * from visit where id=?", new Object[] { id },
                new BeanPropertyRowMapper<Visit>(Visit.class));
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("delete from visit where id=?", new Object[] { id });
    }

    public int insert(Visit visit) {
        System.out.println("insert new Row " + visit.getUserName() + "->");
        return jdbcTemplate.update("insert into visit (id, user_name, date, path, description) " + "values(?, ?, ?, ?, ?)",
                new Object[] { visit.getId(), visit.getUserName(), visit.getDate(), visit.getPath(), visit.getDescription() });
    }

    public int update(Visit visit) {
        return jdbcTemplate.update("update visit " + " set user_name = ?, date = ?, path = ?, description = ? " + " where id = ?",
                new Object[] { visit.getUserName(), visit.getDate(), visit.getPath(), visit.getDescription(), visit.getId()});
    }
}
