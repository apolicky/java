/*package cz.cuni.mff.java.semestr4.domaciUkoly.jdom;

import org.jdom2.*;
import org.jdom2.Content;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class jdom {

    private static ArrayList<String> tituly;
    private static Map<String,String> idXsekce;
    private static ArrayList<Sekce> sections;
    public static int poradi_sekce;


    public static void main(String[] argv) throws Exception {
        tituly = new ArrayList<>();
        sections = new ArrayList<>();
        idXsekce = new HashMap<>();
        poradi_sekce=0;

        //try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))){
        //   while(BR.readLine()!= null){
        //
        //    }
        //}
        //runJDOM("/home/adam/school/java/jdom_test");
        runJDOM();


        //System.out.println("tituly");
        //for( String t : tituly){
        //    System.out.println(t);
        //}

        for (Sekce s : sections){
            for (Odkaz o : s.odkazy){
                o.nahradIDzaJmenoSekce(idXsekce.get(o.kamId));
            }
        }

        sections.sort(new KomparatorSekci());
        for (Sekce s : sections){

            if(!s.odkazy.isEmpty()) {
                s.vypisOdkazy();
            }
        }


        //System.out.println("sekce a id");
        //for(Map.Entry<String,String> iXs : idXsekce.entrySet()){
        //    System.out.println(iXs.getKey() + " <- id" +", sekce -> "+ iXs.getValue());
        //}

    }


    private static void runJDOM(){
        try(BufferedReader BR = new BufferedReader(new InputStreamReader(System.in))) {

            SAXBuilder saxBuilder = new SAXBuilder();
            //Document dokument = saxBuilder.build(filename);
            org.jdom2.Document dokument = saxBuilder.build(BR);

            org.jdom2.Element koren = dokument.getRootElement();

            runJDOMList(koren,0);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void runJDOMList(org.jdom2.Element novyKoren, int hloubka){

        if (novyKoren.getQualifiedName() == "title"){
            List<Content> uvnitr = novyKoren.getContent();
            StringBuilder SB = new StringBuilder();
            for (Content c : uvnitr){
                SB.append(c.getValue());
            }
            tituly.add(SB.toString());
        }

        if (novyKoren.getQualifiedName() == "section"){
            prohledejSekci(novyKoren);
        }

        List children = novyKoren.getChildren();
        Iterator iterator = children.iterator();
        while(iterator.hasNext()){
            org.jdom2.Element dalsi = (org.jdom2.Element) iterator.next();
            runJDOMList(dalsi,hloubka+1);
        }
    }

    private static void prohledejSekci(Element novyKoren){
        ArrayList<Odkaz> odkProSekci = new ArrayList<>();
        StringBuilder SB = new StringBuilder();

        List<Element> deti = novyKoren.getChildren();
        for(Element d : deti){
            if (d.getQualifiedName() == "title"){
                List<Content> uvnitr = d.getContent();

                for (Content c : uvnitr){
                    SB.append(c.getValue());
                }
            }
        }

        Sekce s = new Sekce(SB.toString(),poradi_sekce);
        poradi_sekce++;

        for (Element d : deti){
            najdiLinkyProSekci(novyKoren,odkProSekci,SB.toString());
        }

        for( Odkaz o : odkProSekci){
            s.pridejOdkaz(o);
        }

        boolean byla = false;
        for(Sekce sek : sections){
            if (sek.mojeJmeno.equals(s.mojeJmeno) && sek.odkazy.size() == s.odkazy.size()){
                byla = true;
            }
        }
        if (!byla) {
            sections.add(s);
        }
    }

    private static void najdiLinkyProSekci(Element koren, ArrayList<Odkaz> odkazy, String jm_sekce){

        if (koren.getQualifiedName() == "link"){
            String kam = koren.getAttributeValue("linkend");

            List<Content> uvnitr = koren.getContent();
            StringBuilder SB = new StringBuilder();
            for (Content c : uvnitr){
                SB.append(c.getValue());
            }
            Odkaz o = new Odkaz(SB.toString(),kam);
            odkazy.add(o);
        }

        if(koren.getAttributeValue("id") != null){
            if (!idXsekce.containsKey(koren.getAttributeValue("id"))) {
                idXsekce.put(koren.getAttributeValue("id"),jm_sekce);
            }
        }

        List children = koren.getChildren();
        Iterator iterator = children.iterator();
        while(iterator.hasNext()) {
            org.jdom2.Element dalsi = (org.jdom2.Element) iterator.next();
            if (dalsi.getQualifiedName() != "section") {
                najdiLinkyProSekci(dalsi, odkazy,jm_sekce);
            }
            else{
                prohledejSekci(dalsi);
            }
        }
    }

    public static class KomparatorSekci implements Comparator<Sekce>{
        @Override
        public int compare(Sekce o1, Sekce o2) {
            if (o1.poradi < o2.poradi){
                return -1;
            }
            else if ( o1.poradi == o2.poradi){
                return 0;
            }
            else{
                return 1;
            }
        }
    }
}
*/