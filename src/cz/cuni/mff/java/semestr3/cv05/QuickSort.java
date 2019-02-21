package cz.cuni.mff.java.semestr3.cv05;

public class QuickSort {

    public static int rozdel(int[] array, int dolniIndex, int horniIndex){
        int pivot = array[horniIndex];
        int i = dolniIndex-1;
        for(int j=dolniIndex;j<horniIndex;j++){
            if(array[j]<=pivot){
                i++;

                swap(array, i,j);
            }
        }
        swap(array, i+1,horniIndex);
        return i+1;
    }

    public static void swap(int[] array, int i, int j){
        int tmp=array[i];
        array[i]=array[j];
        array[j]=tmp;
    }

    public static void sort(int[] array, int dolniIndex, int horniIndex){
        if(dolniIndex<horniIndex){
            int indexDeleni = rozdel(array,dolniIndex,horniIndex);

            sort(array,dolniIndex,indexDeleni-1);
            sort(array,indexDeleni,horniIndex);

        }

    }

    public static void main(String[] args){
        int[] array = {5,8,4,8,3,2,6,9,1,10};
        int n=array.length-1;

        sort(array,0,n);

        for(int i:array){
            System.out.println(i);
        }

    }
}
