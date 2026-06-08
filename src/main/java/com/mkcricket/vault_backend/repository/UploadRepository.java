package com.mkcricket.vault_backend.repository;

import com.mkcricket.vault_backend.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UploadRepository extends JpaRepository<Upload, Long> {
    List<Upload> findByStatus(String status);
    Optional<Upload> findByTrackingId(String trackingId);
}
