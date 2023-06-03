import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Mp3ToTxt {
    public static void main(String[] args) throws Exception {
        // set up the Google Cloud Speech-to-Text API client
        try (SpeechClient speechClient = SpeechClient.create()) {
            // specify the path to the MP3 file
            Path audioFilePath = Paths.get("src/main/java/Jealousy.wav");

            // read the audio data from the file
            byte[] audioData = Files.readAllBytes(audioFilePath);

            // build the recognition config
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.FLAC)
                    .setLanguageCode("en-US")
                    .build();

            // build the audio data object
            ByteString audioBytes = ByteString.copyFrom(audioData);
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // perform the speech recognition
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            // print the transcribed text
            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
            }
        }
    }
}
