package cz.cuni.mff.java.semestr3.cv04;

import java.util.Iterator;

public class MyString {
    private char[] a;
    private int delkaPole=0;

    MyString(){
        a = new char[0];
    }

    MyString(String str){
        int delkaStringu= str.length();
        a=new char[delkaStringu];

        for(int i=0;i<delkaStringu;i++){
            a[i]=str.charAt(i);
        }

        delkaPole=delkaStringu;
    }

    public void append(String str){   //copyarray system.
        int delkaNovyhoStringu= str.length();
        char[] tmp = new char[delkaPole + delkaNovyhoStringu];

        for(int i=0; i<delkaPole;i++){
            tmp[i]=a[i];
        }

        for(int i=0; i<delkaNovyhoStringu; i++){
            a[delkaPole+i]=str.charAt(i);
        }

        a=tmp;
        delkaPole += delkaNovyhoStringu;
    }

    public void append(char znak){
        char[] tmp = new char[delkaPole+1];

        for(int i=0;i<delkaPole;i++){
            tmp[i]=a[i];
        }

        tmp[delkaPole]=znak;
        a=tmp;
        delkaPole=delkaPole+1;
    }

    public void insert(int pozice, String string){
        int delkaNovyhoStringu=string.length();
        delkaPole+=delkaNovyhoStringu;
        char[] tmp = new char[delkaPole/*+delkaNovyhoStringu*/];

        for(int i=0;i<pozice;i++){
            tmp[i]=a[i];
        }

        for(int i=0;i<delkaNovyhoStringu;i++){
            tmp[pozice+i]=string.charAt(i);
        }

        for(int i=0;i<delkaPole-delkaNovyhoStringu-pozice;i++){
            tmp[pozice+delkaNovyhoStringu+i]=a[pozice+i];
        }

        a=tmp;
    }

    public void insert(int pozice, char znak){
        delkaPole+=1;
        char[] tmp = new char[delkaPole];

        for(int i=0;i<pozice;i++){
            tmp[i]=a[i];
        }

        tmp[pozice]=znak;

        for(int i=0;i<delkaPole-pozice-1;i++){
            tmp[pozice+1+i]=a[pozice+i];
        }

        a=tmp;
    }

    public void delete(int pozice, int kolikZnaku){
        char[] tmp = new char[delkaPole-kolikZnaku];

        for(int i=0;i<pozice;i++){
            tmp[i]=a[i];
        }
        for(int i=0;i<delkaPole-kolikZnaku-pozice;i++){
            tmp[pozice+i]=a[pozice+kolikZnaku+i];
        }

        a=tmp;
    }

    /*public String toString(){
        return a.toString();
    }*/

    private Iterator<Character> Iterator(){
        return new Iterator<Character>() {
            public boolean hasNext() {
                return false;
            }

            public Character next() {
                return null;
            }
        };
    }
}
