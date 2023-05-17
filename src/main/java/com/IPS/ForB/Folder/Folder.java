package com.IPS.ForB.Folder;

import java.util.List;

import javax.persistence.*;

import com.IPS.ForB.File.File;
import com.IPS.ForB.User.Entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Folder")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Folder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "folder_id",nullable = false, unique = true)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User userId;

	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
	private List<File> fileList;

	@Column(nullable = false)
	private String name;


	public static Folder createFolder( User userId, String name){
		Folder folder = new Folder();
		// folder.id = id;
		folder.userId=userId;
		folder.name=name;

		return folder;

	}

}
