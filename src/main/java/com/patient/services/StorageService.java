package com.patient.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Service
public class StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public void uploadDataFile(File file) {
        System.out.println(new StringBuilder().append("Saving file to s3; file size: ").append(file.length()).append("kb; date: ").append(new Date()).toString());
        s3Client.putObject(new PutObjectRequest(bucketName, "data.json", file));
    }
}
