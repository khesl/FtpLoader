package com.khesl.ftploader.FtpLoader.beans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UploadedFilesRepository extends JpaRepository<UploadedFiles, Long> {

}
