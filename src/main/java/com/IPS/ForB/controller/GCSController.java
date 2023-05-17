package com.IPS.ForB.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.IPS.ForB.dto.UploadReqDto;
import com.IPS.ForB.service.GCSService;
import com.google.cloud.storage.Storage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GCSController {
    @Autowired
    private GCSService gcsService;
    @Autowired
    private Storage storage;

    //@PostMapping("/upload")
    public String uploadNewFile(UploadReqDto dto, String filename, Long folderId) throws IOException {
        return gcsService.uploadNewFile(dto, filename, folderId);
    }

}
