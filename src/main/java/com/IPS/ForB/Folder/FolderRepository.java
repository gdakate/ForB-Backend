package com.IPS.ForB.Folder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.IPS.ForB.User.Entity.User;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
	// List<Folder> findAll();
    List<Folder> findAllByUserId(User user);
    Optional<Folder> findByIdAndUserId(Long folder_id, User user);
}
