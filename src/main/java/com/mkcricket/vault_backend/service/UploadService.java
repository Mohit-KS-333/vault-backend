package com.mkcricket.vault_backend.service;

import com.mkcricket.vault_backend.model.Upload;
import com.mkcricket.vault_backend.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UploadService {

    @Autowired
    private UploadRepository uploadRepository;

    public List<Upload> getPendingUploads() {
        return uploadRepository.findByStatus("pending");
    }

    public Upload saveUpload(Upload upload) {
        return uploadRepository.save(upload);
    }
}