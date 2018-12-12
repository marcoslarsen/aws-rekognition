package com.hitachivantara.aws.rekognition.processors;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.hitachivantara.aws.rekognition.clients.RekognitionClientFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ProcessLabels {
    public void run(String[] args) {

        if (args.length < 1) {
            System.err.println("Please provide an image.");
            return;
        }

        String imgPath = args[0];
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(imgPath));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + e.getMessage());
            return;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        AmazonRekognition rekognition = RekognitionClientFactory.createClient();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image().withBytes(byteBuffer))
                .withMaxLabels(10);
        DetectLabelsResult result = rekognition.detectLabels(request);

        List<Label> labels = result.getLabels();
        for (Label label : labels) {
            System.out.println(label.getName() + ": " + label.getConfidence());
        }
    }

}
