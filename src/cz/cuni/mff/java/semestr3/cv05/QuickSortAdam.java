package cz.cuni.mff.java.semestr3.cv05;

public class QuickSortAdam {

    public static int rozdel(int[] pole, int dolniIndex, int horniIndex){
        int pivot = pole[horniIndex];
        int indexKterejVratimProDalsiDeleni = dolniIndex-1;  //udava pozici pivota po setrizeni

        for(int j=dolniIndex;j<horniIndex;j++){
            if(pole[j]<=pivot){
                indexKterejVratimProDalsiDeleni++;

                prohod(pole, indexKterejVratimProDalsiDeleni, j);
            }
        }

        prohod(pole,indexKterejVratimProDalsiDeleni+1,horniIndex);
        return indexKterejVratimProDalsiDeleni+1;
    }

    public static void prohod(int[] pole, int i, int j){
        int docasna=pole[i];
        pole[i]=pole[j];
        pole[j]=docasna;
    }

    public static void utrid(int[] pole, int dolniIndex, int horniIndex){
        if(dolniIndex<horniIndex){
            int indexDeleni=rozdel(pole, dolniIndex, horniIndex);

            utrid(pole,dolniIndex,indexDeleni-1);
            utrid(pole, indexDeleni,horniIndex);
        }
    }

    public static void main(String[] args){
        int[] pole={3,6,4,8,9,4,2,6,9,0,1,3,4,10};
        int posledniIndex=pole.length-1;

        utrid(pole, 0, posledniIndex);
    }
}
