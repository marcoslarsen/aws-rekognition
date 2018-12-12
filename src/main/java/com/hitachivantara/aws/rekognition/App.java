package com.hitachivantara.aws.rekognition;

import com.hitachivantara.aws.rekognition.processors.ProcessFaces;
import com.hitachivantara.aws.rekognition.processors.ProcessLabels;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new ProcessLabels().run( args );
        new ProcessFaces().run( args );
    }
}
