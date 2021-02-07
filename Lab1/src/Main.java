import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This program converts a .txt file to a .csv file as follows: in the
 *
 * @author rubik
 * @link ../output.csv
 * file we get 3 columns:
 * 1) Word 2) Frequency 3) Frequency in '%'
 * You need to run the program with the parameter: input_file_name.txt
 */

public class Main {
    public static void main(String[] args) {
        Path file = null;
        if (args.length != 1) { /* if there are not enough or many arguments,
                                                      then we ask you to enter the file name into the console */
            System.out.println("You run program with too many arguments or without arguments, please enter input_file_name.txt");
            try (DataInputStream dis = new DataInputStream(System.in)) {
                file = Paths.get(dis.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = Paths.get(args[0]);
        }
        try (RandomAccessFile fin = new RandomAccessFile(file.toFile(), "r")) {
            TxtToCSVParser.getInstance(fin).parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
