package com.IPS.ForB.File;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByUserId(Long user_id);
    List<File> findAllByUserIdAndFolderId(Long user_id, Long folder_id);
}