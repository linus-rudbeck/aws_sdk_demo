package se.distansakademin;

import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting S3 Example");

        S3Client s3 = S3Example.getClient();

        String bucketName = "my-bucket-linus-" + System.currentTimeMillis();
        String fileName = "file1.txt";

        S3Example.createBucket(s3, bucketName);
        S3Example.uploadFile(s3, bucketName, fileName, fileName);
        S3Example.readFile(s3, bucketName, fileName);

        System.out.println("Program completed");
    }
}