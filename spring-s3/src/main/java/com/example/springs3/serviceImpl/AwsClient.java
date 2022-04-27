package com.example.springs3.serviceImpl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@Slf4j
public class AwsClient {

    private AmazonS3 amazonS3;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon(){
        AWSCredentials credentials= new BasicAWSCredentials(this.accessKey,this.secretKey);
        this.amazonS3 = new AmazonS3Client(credentials);
    }

    public String deleteFileFromS3Bucket(String fileUrl){
        String fileName= fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName+ "/", fileName));
        return "Successfully Deleted the Bucket";
    }

    private void uploadFileTos3bucket(String fileName, File file){
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName,file));
    }

    private String genrateFileName(MultipartFile multipartFile){
        return new Date().getTime()+ "-" + multipartFile.getOriginalFilename().replace(" ", "-");
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException{
        File converFile= new File(multipartFile.getOriginalFilename());
        FileOutputStream fos=new FileOutputStream(converFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return converFile;
    }

    public String uploadFile(MultipartFile multipartFile)throws IOException{

        String fileUrl = "";
        try{
            File file = convertMultipartToFile(multipartFile);
            String fileName= genrateFileName(multipartFile);
            fileUrl= endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName,file);
        }

        catch (AmazonServiceException exception){
            log.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            log.info("Error Message:    " + exception.getMessage());
            log.info("HTTP Status Code: " + exception.getStatusCode());
            log.info("AWS Error Code:   " + exception.getErrorCode());
            log.info("Error Type:       " + exception.getErrorType());
            log.info("Request ID:       " + exception.getRequestId());
        }
        catch (AmazonClientException clientException){
            log.info("Caught an AmazonClientException: ");
            log.info("Error Message: " + clientException.getMessage());
        }
        catch (IOException ioException){
            log.info("IOE Error Message: " + ioException.getMessage());
        }
        return fileUrl;
    }
}
