import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * The class that parses the .txt to .csv only the input file is needed
 */
public class TxtToCSVParser {
    private static TxtToCSVParser instance = null;
    private final RandomAccessFile fin;
    private final Map<String, Integer> words;

    private TxtToCSVParser(RandomAccessFile fin) {
        this.fin = fin;
        words = new HashMap<>();
    }

    /**
     * This method creates an object if the object has not yet been created,
     * and if it has already been created, it returns a pre-created object, thus implementing the Singleton pattern
     * @param fin
     * @return TxtToCSVParser
     */
    public static TxtToCSVParser getInstance(RandomAccessFile fin) {
        if (instance == null) {
            instance = new TxtToCSVParser(fin);
        }
        return instance;
    }

    /**
     * The method takes a char from file and add it to string if it's digit or letter,
     * then if met a separator add string in frequency container, after reading of whole file
     * print words in
     * @link ../output.csv
     * by method writeToCSV
     */
    public void parse() throws IOException {
        StringBuilder string = new StringBuilder();
        int c;
        do {
            c = fin.read();
            if(c != -1) {
                if(Character.isLetterOrDigit((char)c)) {
                    string.append((char)c);
                } else if (string.length() != 0) {
                    words.merge(string.toString(), 1, Integer::sum);
                    string.delete(0, string.length());
                }
            }
        } while(c != -1);
        writeToCSV();
    }

    /**
     * The method prints words in descending order in
     * @link ../output.csv
     */
    private void writeToCSV() {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(words.entrySet());
        list.sort((o1, o2) -> o2.getValue() - o1.getValue());
        try(FileWriter fout = new FileWriter(Paths.get("output.csv").toFile(), false)){

            for (Map.Entry<String, Integer> next : list) {
                String line = next.getKey() + ";" + next.getValue().toString() + ";" + (next.getValue() / (float) list.size() * 100) + "%";
                fout.write(line + System.lineSeparator());
                fout.flush();
            }
        } catch (IOException e) {
            System.err.println("You need to put the output.csv file back in place!");
        }
    }

}
