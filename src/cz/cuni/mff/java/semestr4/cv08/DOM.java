/*package cz.cuni.mff.java.semestr4.cv08;

import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.jdom2.*;

import javax.annotation.processing.SupportedSourceVersion;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;

public class DOM {
    public static void main(String[] argv) throws Exception {
        //long domStart = System.currentTimeMillis();
        //runDOM("/home/adam/Desktop/sample.xml");
        //long domEnd = System.currentTimeMillis();
        //System.out.println();

        //long jdomStart = System.currentTimeMillis();
        runJDOM("/home/adam/Downloads/INPUT(1)");
        //long jdomEnd = System.currentTimeMillis();

        /*
        long jdom,dom;
        dom = domEnd-domStart;
        jdom = jdomEnd-jdomStart;

        if(dom < jdom){
            System.out.println("DOM: "+dom+" JDOM: "+jdom+"\nDOM faster by: "+(jdom-dom));
        }
        else if(dom == jdom){
            System.out.println("Both are fast the same: "+dom);
        }
        else{
            System.out.println("JDOM: "+jdom+" DOM: "+dom+"\nJDOM faster by: "+(dom-jdom));
        }

    }

    public static void runDOM(String filename) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(filename);

        Element root = document.getDocumentElement();

        runDOMList(root,0);

    }

    private static void runDOMList(Element koren, int hloubka){
        odstav(hloubka);
        System.out.println(koren.getNodeName());
        NodeList nl = koren.getChildNodes();

        for (int i=0; i<nl.getLength(); i++) {
            Node n = nl.item(i);
            if(n instanceof Element) {
                runDOMList((Element) n, hloubka + 1);
            }

        }
    }

    private static void runJDOM(String filename){
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            org.jdom2.Document dokument = saxBuilder.build(filename);

            org.jdom2.Element koren = dokument.getRootElement();

            runJDOMList(koren,0);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void runJDOMList(org.jdom2.Element novyKoren, int hloubka){
            //odstav(hloubka);
            System.out.println(novyKoren.getAttributes());
            List children = novyKoren.getChildren();
            Iterator iterator = children.iterator();
            while(iterator.hasNext()){
                org.jdom2.Element dalsi = (org.jdom2.Element) iterator.next();
                runJDOMList(dalsi,hloubka+1);
            }


    }


    private static void odstav(int n) {

        for (int i = 0; i < n; i++) {
            System.out.print('\t');
        }

    }

}
*/