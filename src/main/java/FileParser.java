import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileParser {
    /**
     * divides the contents of {@param pathInput}
     * into separate files for each sender to the {@param pathOutput} directory
     *
     * */
    public static void parserFile(Path pathInput, Path pathOutput) {
        try {
            if (Files.exists(pathInput) & Files.isRegularFile(pathInput)) {
                FileInputStream input = new FileInputStream(new File(String.valueOf(pathInput)));
                CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
                decoder.onMalformedInput(CodingErrorAction.IGNORE);
                try (InputStreamReader reader = new InputStreamReader(input, decoder);
                     BufferedReader bufferedReader = new BufferedReader(reader)) {
                    BufferedWriter bufferedWriter = null;
                    String line;
                    String lineSeparator = System.getProperty("line.separator");
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.startsWith("From ")) {
                            closeBufferedWriter(bufferedWriter);
                            bufferedWriter = new BufferedWriter(new FileWriter(new File(pathOutput.toString().
                                    concat(File.separator).concat(checkUser(line))),true));
                        }
                        bufferedWriter.write(line.concat(lineSeparator));
                    }
                    closeBufferedWriter(bufferedWriter);
                }
            }
        }catch (IOException exceptionBase64){
            System.err.println("Ik5ldmVyIHNlbmQgYSBodW1hbiB0byBkbyBhIG1hY2hpbmUncyBqb2IiIMKpIEFnZW50IFNtaXRo");
        }
    }
    private static void closeBufferedWriter(BufferedWriter bufferedWriter) throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }
    private static String checkUser(String line){
        String[] wordsInLine = line.split(" ");
        return wordsInLine[1];
    }
}
