package se.distansakademin;

import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting S3 OOP Example");

        var s3oop = new S3OOPExample();
        s3oop.createBucket("linus-rudbeck-2408141438");
        s3oop.uploadFile("my-file.txt", "file1.txt");

        System.out.println("Program completed");
    }

    private static void ec2Demo() {
        var ec2 = EC2Example.getClient();
        var instance = EC2Example.createEC2InstanceWithWebServer(ec2);
        System.out.println(instance.instanceId());
    }

    private static void s3Demo() throws IOException {
        S3Client s3 = S3Example.getClient();

        String bucketName = "rcit-imgs-" + System.currentTimeMillis();
        String relativePath = "file1.txt";
        String uploadedFileName = "uploaded-" + relativePath;

        S3Example.createBucket(s3, bucketName);
        S3Example.uploadFile(s3, bucketName, uploadedFileName, relativePath);
        S3Example.readFile(s3, bucketName, uploadedFileName);
        S3Example.deleteObject(s3, bucketName, uploadedFileName);
        S3Example.deleteBucket(s3, bucketName);
    }
}