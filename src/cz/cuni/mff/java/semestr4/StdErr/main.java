import java.io.*;

public class main {
    public static void main(String[] args){
        try( BufferedReader R = new BufferedReader(new InputStreamReader(System.in)) ){
            String radka = R.readLine();
            System.out.println(radka);
            System.err.println("Do chyboveho "+ radka);
        }
        catch (IOException e){
            System.err.println("Chyba!");
        }
    }
}
