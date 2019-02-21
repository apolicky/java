package cz.cuni.mff.java.semestr3.cv11;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*mel bych se ujistit, jesli je to html stranka
  taky bych mel vedet, jaky je to kodovani
  mel bych nejdriv openconnection, pak kdyz je html, zjistit kodovani, potom az prevest na retezec
  nebo pres http nakonfigurovat dotaz a mohlo by to prijit rovnou.

  pro paralelni lepsi pracovat s http clientem, ktery ma podporu pro asynchronni stahovani.
 */

public class ImageDownloader {
    public static void main(String[] args){
        if(args.length<1){
            System.out.println("malo argumentu");
            System.exit(1);
        }
        try{
            URL u = new URL(args[0]);

            String page = loadURLToString(u);

            Pattern pattern = Pattern.compile("<img\\b[^>]*src=([\'\"]*)([^\'\"]*)\\1");
            Matcher m = pattern.matcher(page);

            Set<URL> downloaded = new HashSet<URL>();

            ExecutorService pool = Executors.newWorkStealingPool();

            while(m.find()){
                String imgUrl = m.group(2);

                // URL toDownload = new URL(imgUrl); tohle nebude fungovat, potrebujeme absolutni ne relativni pro stazeni


                try{
                    URI imgURI = new URI(imgUrl);
                    URI absoluteURI = u.toURI().resolve(imgURI);

                    URL toDownload = absoluteURI.toURL();

                    Path fileName = Paths.get(absoluteURI.getPath()).getFileName();

                    if(!downloaded.contains(toDownload)) {
                        /*try {
                            downloadURL(toDownload, fileName);
                            downloaded.add(toDownload);
                        } catch (IOException e) {
                            System.out.println("Cannot download" + e);
                        }*/
                        pool.submit(() -> {
                            try {
                                downloadURL(toDownload,fileName);
                            }
                            catch(IOException e){
                                System.out.println("cannot download " + e);
                            }
                        });
                        downloaded.add(toDownload);

                    }
                }
                catch(URISyntaxException e){
                    System.out.println("Spatna url na page \"" + imgUrl + "\"");
                }
            }
            //pridano------------------
            pool.shutdown();
            while(!pool.isTerminated()){
                try {
                    Thread.sleep(100);
                }catch(InterruptedException e){}
            }
            //-----------------
        }
        catch(MalformedURLException e){
            System.out.println("Spatne zadana stranka");
        }
    }

    private static void downloadURL(URL u, Path fileName) throws IOException{
        System.out.println("Downloading " + u);
        try(InputStream is = new BufferedInputStream(u.openStream()); OutputStream os = Files.newOutputStream(fileName)){
            int c;
            while((c = is.read()) != -1){
                os.write(c);
            }
        }
    }

    private static String loadURLToString(URL u){
        try(InputStream is = u.openStream(); ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            int c;
            while((c=is.read())!=-1){
                bos.write(c);
            }
            return  new String(bos.toByteArray());
        }
        catch(IOException e){
            System.out.println("Nesel precist URL vstup");
            System.exit(2);
        }
        return null;
    }
}
