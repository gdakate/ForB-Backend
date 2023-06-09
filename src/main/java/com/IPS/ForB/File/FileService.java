package com.IPS.ForB.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.IPS.ForB.Folder.FileResponseDto;
import com.IPS.ForB.Folder.Folder;
import com.IPS.ForB.Folder.FolderRepository;
import com.IPS.ForB.User.Entity.User;
import com.IPS.ForB.User.UserRepository;
import com.IPS.ForB.controller.GCSController;
import com.IPS.ForB.dto.UploadReqDto;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final GCSController gcsController;
    @Autowired
    private Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Transactional
    public FilePostResponseDto addFile(Long userId, Long folderId, FileRequestDto fileRequestDto) throws IOException {
        MultipartFile multipartFile = fileRequestDto.getFile();
        String filename = fileRequestDto.getFile_name();

        // 파일 gcs에 업로드
        User currentUser = userRepository.findById(userId).get();
        Folder currentFolder = folderRepository.findById(folderId).get();
        UploadReqDto uploadReqDto = new UploadReqDto(currentUser.getName(), userId, multipartFile);
        String uploadedFileUrl = gcsController.uploadNewFile(uploadReqDto, filename, folderId);


//        File target = wantedFile.get();
//        String fileName = target.getFileName();
////            String downloadFileName = "userid/"+fileName+"_json_folder/"+pageId+"/"+fileName+"_"+pageId+".json"; // api 테스트용 파일 생성 코드
//        String downloadFileName = userId+"/"+fileName+"_json_folder/"+pageId+"/"+fileName+"_"+pageId+".json"; // 실제 코드
//
//        System.out.println("다운로드 경로: " + downloadFileName);
//        BlobId blobId = BlobId.of("cloud_storage_leturn", downloadFileName);
//        Blob blob = storage.get(blobId);
//        byte[] content = blob.getContent();
//        String targetJson = new String(content, StandardCharsets.UTF_8);


        String uploadedFileImage = "https://storage.googleapis.com/"+bucketName+"/"+userId+"/"+filename+"_thumbnail.png"; // 업로드 된 파일 썸네일, 받아오는 코드는 추후에 작성

        // 후 createFile(User user, String fileName, String fileUrl)로 filerepository에 저장
        com.IPS.ForB.File.File newFile = com.IPS.ForB.File.File.createFile(currentUser, currentFolder, filename/*multipartFile.getOriginalFilename()*/,uploadedFileUrl, uploadedFileImage);
        fileRepository.save(newFile);


        return FilePostResponseDto.builder()
                .file_id(newFile.getId())
                .build();
    }

    @Transactional
    public GetFileResponseDto getFile(Long userId, Long fileId) {
        // 파일 repository에서 파일 찾기
        Optional<File> wantedFile = fileRepository.findById(fileId);
        // GCS에서 파일 폴더 찾아서 페이지 몇갠지 세어보기
        if (wantedFile.isPresent()) {
            File target = wantedFile.get();
            String fileName = target.getFileName();
            int pageId = 1;
            boolean isExist = true;
            while (isExist) {
//                String filesPath = "userid/"+fileName+"_json_folder/"+pageId+"/"+fileName+"_"+pageId+".json"; // 테스트용 코드
                String filesPath = userId+"/"+fileName+"_json_folder/"+pageId+"/"+fileName+"_"+pageId+".json"; // 실제 코드
                System.out.println("파일 경로: " + filesPath);
                BlobId blobId = BlobId.of("cloud_storage_leturn", filesPath);

                try {
                    Blob blob = storage.get(blobId);
                    isExist = blob.exists();
                } catch (NullPointerException e) {
                    System.out.println("count: "+pageId+" is not present");
                    return new GetFileResponseDto(fileId, pageId-1);
                }
                if (isExist) {
                    System.out.println("count: "+pageId+" is present");
                    pageId++;
                }
            }
        }
        return new GetFileResponseDto();
    }

    public Map<String, List<FileResponseDto>> viewFolderFile(Long user_id, Long folder_id) {
        List<File> files = fileRepository.findAllByUserIdAndFolderId(user_id, folder_id);
        List<FileResponseDto> fileResponseDtos = files.stream()
                .map(f -> new FileResponseDto(f.getId(), f.getFileName(),f.getFileImg()))
                .collect(Collectors.toList());
        Map<String, List<FileResponseDto>> response = new HashMap<>();
        response.put("file", fileResponseDtos);
        return response;
    }
}