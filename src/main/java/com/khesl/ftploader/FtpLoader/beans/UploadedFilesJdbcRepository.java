package com.khesl.ftploader.FtpLoader.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Collection;

@Repository
public class UploadedFilesJdbcRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public Collection<UploadedFiles> findAll(){
        return jdbcTemplate.queryForList("select * from uploaded_files",
                UploadedFiles.class);
    }

    public UploadedFiles findById(long id) {
        return jdbcTemplate.queryForObject("select * from uploaded_files where id=?", new Object[] { id },
                new BeanPropertyRowMapper<UploadedFiles>(UploadedFiles.class));
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("delete from uploaded_files where id=?", new Object[] { id });
    }

    /*@Autowired
    LobHandler lobHandler;*/

    public int insert(UploadedFiles uploadedFiles, byte[] bytes) {

        /*MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("id", uploadedFiles.getId());
        in.addValue("file_name", uploadedFiles.getFileName());
        in.addValue("upload_date", uploadedFiles.getUploadDate());
        //in.addValue("data",  new SqlLobValue(bytes, new DefaultLobHandler()), Types.CLOB);
        in.addValue("data", bytes.toString(), Types.CLOB);*/

        /*jdbcTemplate.update("insert into uploaded_files (id, file_name, upload_date, data) " + "values(?, ?, ?, ?)",
                 new Object[] { uploadedFiles.getId(), uploadedFiles.getFileName(), uploadedFiles.getUploadDate(), null});*/

        //String SQL = "update uploaded_files set data = :data where id = :id";
        /*String SQL = "insert into uploaded_files set description = :description where id = :id";
        NamedParameterJdbcTemplate jdbcTemplateObject = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplateObject.update(SQL, in);*/
        //jdbcTemplate.update(SQL, in);

        /*jdbcTemplate.update("insert into uploaded_files(id, file_name, upload_date, data) "
                + "values (?, ?, ?, ?)", new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, uploadedFiles.getId());
                ps.setString(2, uploadedFiles.getFileName());
                ps.setDate(4, null, Calendar.getInstance());//Date(4, uploadedFiles.getUploadDate());
                lobHandler.getLobCreator().setClobAsString(ps, 5, bytes.toString());
            }
        });*/

        System.out.println("Updated Record with ID = " + uploadedFiles.getId());


        System.out.println("insert new Row " + uploadedFiles.getFileName() + "->");
        return jdbcTemplate.update("insert into uploaded_files (id, file_name, upload_date, data) " + "values(?, ?, ?, ?)",
                new Object[] { uploadedFiles.getId(), uploadedFiles.getFileName(), uploadedFiles.getUploadDate(),
                        new SqlLobValue(bytes.toString(), new DefaultLobHandler()) },
                new int[] {Types.INTEGER, Types.VARCHAR, Types.DATE, Types.CLOB});
        //return 1;
    }

    /*public int update(UploadedFiles uploadedFiles) {
        return jdbcTemplate.update("update uploaded_files " + " set file_name = ?, upload_date = ?, data = ? " + " where id = ?",
                new Object[] { uploadedFiles.getFileName(), uploadedFiles.getUploadDate(), uploadedFiles.getData(), uploadedFiles.getData());
    }*/
}
