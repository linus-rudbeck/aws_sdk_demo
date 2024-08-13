package se.distansakademin;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class S3Example {

    public static S3Client getClient(){
        return S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public static void createBucket(S3Client s3, String bucketName){
        // Build request
        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        // Execute request
        CreateBucketResponse response = s3.createBucket(request);

        // Handle response
        System.out.println(response.location());
    }

    public static void uploadFile(S3Client s3, String bucketName, String keyName, String filePath) throws IOException {
        // Build request
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        // Execute request
        Path path = Paths.get(filePath);
        PutObjectResponse response = s3.putObject(request, path);

        // Handle response
        System.out.println(response.toString());
    }

    public static void readFile(S3Client s3, String bucketName, String keyName){
        // Build request
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        // Execute request
        String response = s3.getObjectAsBytes(request).asUtf8String();

        // Handle response
        System.out.println(response);
    }
}
