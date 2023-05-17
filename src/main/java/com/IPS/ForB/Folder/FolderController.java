package com.IPS.ForB.Folder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.IPS.ForB.File.FileService;
import com.IPS.ForB.User.Entity.User;
import com.IPS.ForB.User.UserRepository;
import com.IPS.ForB.auth.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FolderController {
	private final FolderService folderService;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserRepository userRepository;
	private final FileService fileService;
	@PostMapping("/folder")
	public ResponseEntity<Map<String, Map<String, List<FolderResponseDto>>>> createFolder(
			@RequestHeader("token") String jwtToken, @RequestBody FolderRequestDto folderRequestDto) {
		Long userId = Long.valueOf(jwtTokenUtil.getUserIdFromToken(jwtToken));
		Map<String, List<FolderResponseDto>> folderMap = folderService.save(userId, folderRequestDto);
		Map<String, Map<String, List<FolderResponseDto>>> response = new HashMap<>();
		Map<String, List<FolderResponseDto>> dataMap = new HashMap<>();
		dataMap.put("folder", folderMap.get("folder"));
		response.put("data", dataMap);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/folder")
	public ResponseEntity<Map<String, Map<String, List<FolderResponseDto>>>> getFolder(@RequestHeader("token") String jwtToken) {
		System.out.println("jwtToken: " + jwtToken);
		Long user_id = Long.valueOf(jwtTokenUtil.getUserIdFromToken(jwtToken));
		User currentUser = userRepository.findById(user_id).get();
		Map<String, List<FolderResponseDto>> folderMap = folderService.viewFolder(currentUser);
		Map<String, Map<String, List<FolderResponseDto>>> response = new HashMap<>();
		Map<String, List<FolderResponseDto>> dataMap = new HashMap<>();
		dataMap.put("folder", folderMap.get("folder"));
		response.put("data", dataMap);
		return ResponseEntity.ok(response);
	}



	@GetMapping("/folder/{folderId}")
	public ResponseEntity<Map<String, Map<String, List<FileResponseDto>>>> getFilesInFolder(
			@RequestHeader("token") String jwtToken, @PathVariable Long folderId) {
		System.out.println("jwtToken: " + jwtToken);
		Long user_id = Long.valueOf(jwtTokenUtil.getUserIdFromToken(jwtToken));
		User currentUser = userRepository.findById(user_id).get();
		Map<String, List<FileResponseDto>> fileMap = fileService.viewFolderFile(user_id, folderId);
		Map<String, Map<String, List<FileResponseDto>>> response = new HashMap<>();
		Map<String, List<FileResponseDto>> dataMap = new HashMap<>();
		dataMap.put("files", fileMap.get("file"));
		response.put("data", dataMap);
		return ResponseEntity.ok(response);
	}

}