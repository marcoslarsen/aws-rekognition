package com.hitachivantara.aws.rekognition;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.hitachivantara.aws.rekognition.processors.ListKeys;
import com.hitachivantara.aws.rekognition.processors.ProcessFaces;
import com.hitachivantara.aws.rekognition.processors.ProcessLabels;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {

        if(args.length != 1){
            System.err.println("Wrong number of arguments! Please check input arguments!!");
            return;
        }

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion("us-east-1")
                .build();

        ProcessFaces processFaces = new ProcessFaces();
        ProcessLabels processLabels = new ProcessLabels();
        String bucketName = args[0];

        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName); //.withMaxKeys(2);
        ListObjectsV2Result result;

        do {
            result = s3Client.listObjectsV2(req);
            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());

                processLabels.run( bucketName , objectSummary.getKey() );
                processFaces.run( bucketName , objectSummary.getKey() );
            }
        } while (result.isTruncated());
    }
}
