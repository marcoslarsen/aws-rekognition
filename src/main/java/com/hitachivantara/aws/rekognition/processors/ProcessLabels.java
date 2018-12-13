package com.hitachivantara.aws.rekognition.processors;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.hitachivantara.aws.rekognition.clients.RekognitionClientFactory;

import java.util.List;

public class ProcessLabels {


    public void run(String bucketName, String photoName) throws Exception{

        if ( bucketName == null || bucketName.isEmpty() ) {
            System.err.println("Please provide a bucket.");
            return;
        }

        AmazonRekognition rekognition = RekognitionClientFactory.createClient();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(photoName).withBucket(bucketName)))
                .withMaxLabels(10)
                .withMinConfidence(75F);

        DetectLabelsResult result = rekognition.detectLabels(request);

        List<Label> labels = result.getLabels();
        for (Label label : labels) {
            System.out.println(label.getName() + ": " + label.getConfidence());
        }
    }
}
