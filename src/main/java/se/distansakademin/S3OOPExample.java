package se.distansakademin;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Path;
import java.nio.file.Paths;

public class S3OOPExample {

    private final S3Client s3;
    private String bucketName;

    public S3OOPExample() {
        s3 = S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public void createBucket(String bucketName){
        // Build request
        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        // Execute request
        s3.createBucket(request);

        this.bucketName = bucketName;
    }

    public void uploadFile(String keyName,String filePath){
                           // Build request
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        // Execute request
        Path path = Paths.get(filePath);
        s3.putObject(request, path);
    }
}
