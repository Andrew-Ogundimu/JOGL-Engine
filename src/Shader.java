import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Shader {
    private int type;
    private ArrayList<String> raw;
    private ArrayList<Integer> lengths;
    public Shader(String path, int type) {
        this.type = type;
        File f = new File(path);
        raw = new ArrayList<>();
        lengths = new ArrayList<Integer>();
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String current = scan.nextLine();
                raw.add(current+"\n");
                lengths.add(current.length()+1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public int getType() {
        return type;
    }
    public String[] getRaw() {
        return raw.toArray(new String[raw.size()]);
    }
    public  int[] getRawLengths() {
        int[] primitive = new int[lengths.size()];
        for (int i=0; i<lengths.size(); i++) {
            primitive[i] = lengths.get(i);
        }
        return primitive;
    }
}
