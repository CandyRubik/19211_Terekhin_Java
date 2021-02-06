import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 *  Class for comparisons of two tuples
 */
class EntryComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return o2.getValue().compareTo(o1.getValue());
    }
}

/**
 * The class that parses the .txt to .csv only the input file is needed
 */
public class TxtToCSVParser {
    private final RandomAccessFile fin;
    private final Map<String, Integer> words;

    public TxtToCSVParser(RandomAccessFile fin) {
        this.fin = fin;
        words = new HashMap<>();
    }

    /**
     * The method takes a line from the file and parse it, then takes another line and so on until they run out
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

    private void writeToCSV() {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(words.entrySet());
        list.sort(new EntryComparator());
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
