package se.distansakademin;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

import java.util.Base64;

public class EC2Example {

    private static final String IMAGE_ID = "ami-02af70169146bbdd3";
    private static final String KEYPAIR_NAME = "jensen-server-key";
    private static final String SECURITY_GROUP_ID = "sg-010ee42dede9eb4fa";

    public static Ec2Client getClient(){
        return Ec2Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    private static String getUserDataBase64(){
        String userDataScript = "#!/bin/bash\n"
                + "yum update -y\n"
                + "yum install -y httpd\n"
                + "systemctl start httpd\n"
                + "systemctl enable httpd\n"
                + "echo \"<html><h1>Web server</h1></html>\" > /var/www/html/index.html\n";

        return Base64.getEncoder().encodeToString(userDataScript.getBytes());
    }

    private static void addInstanceName(Ec2Client ec2, String instanceId){

        // Build request
        var tag = Tag.builder()
                .key("Name")
                .value("DEMO_INSTANCE_TERMINATE_ME")
                .build();

        var request = CreateTagsRequest.builder()
                .resources(instanceId)
                .tags(tag)
                .build();

        // Execute request
        var response = ec2.createTags(request);

        // Handle response
        System.out.println(response.toString());
    }

    public static Instance createEC2InstanceWithWebServer(Ec2Client ec2){
        // Build request
        var userDataBase64 = getUserDataBase64();

        var request = RunInstancesRequest.builder()
                .imageId(IMAGE_ID)
                .keyName(KEYPAIR_NAME)
                .securityGroupIds(SECURITY_GROUP_ID)
                .instanceType(InstanceType.T3_MICRO)
                .userData(userDataBase64)
                .minCount(1)
                .maxCount(1)
                .build();

        // Execute request
        var response = ec2.runInstances(request);

        // Handle response
        var instance = response.instances().getFirst();
        addInstanceName(ec2, instance.instanceId());

        return instance;
    }

}
