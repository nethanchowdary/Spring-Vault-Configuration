package com.example.springs3.controller;

import com.example.springs3.serviceImpl.AwsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/object")
public class ObjectController {

    @Autowired
    private AwsClient awsClient;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file")MultipartFile file) throws IOException{
        return this.awsClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String url){
        return this.awsClient.deleteFileFromS3Bucket(url);
    }
}
