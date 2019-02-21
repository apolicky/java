package cz.cuni.mff.java.semestr3.cv08;

public class MergeSort {
    public static void main(String[] args){

        int[] arr = {5,6,8,3,6,3,6,8,4,8,12,95};

        mergeSort(arr);
        paraMergeSort(arr,Runtime.getRuntime().availableProcessors());

        System.out.println("procesoru: " + Runtime.getRuntime().availableProcessors());

        for (int i : arr){
            System.out.println(i);
        }
    }

    public static void mergeSort(int[] arr){
        mergeSort(arr,0,arr.length-1);
    }

    private static void mergeSort(int[] arr, int l, int r){
        if(l<r){
            mergeSort(arr,l,(l+r)/2);
            mergeSort(arr, ((l+r)/2)+1,r);
            merge(arr,l,(l+r)/2,r);
        }
    }

    public static void paraMergeSort(int[] arr, int nThreads){
        int x = arr.length/nThreads;

        if(x>0){
            Thread[] threads = new Thread[nThreads];
            for(int i =0 ; i < nThreads; i++){
                int r = ((i+1)*x)-1;
                if(r>=arr.length||(i==nThreads -1 && r<arr.length-1)){
                    r=arr.length-1;
                }
                threads[i]=new MThread(arr,i*x,r);
                threads[i].start();
            }

            for(Thread t: threads){
                while(t.isAlive()){
                    try{
                        t.join();
                    }
                    catch (InterruptedException ie){

                    }
                }
            }
            paraMerge(arr,nThreads);
        }
        else{
            //TODO
        }
    }

    private static void paraMerge(int[] arr, int nThreads){
        int x = arr.length/nThreads;

        for( int i = 0; i< nThreads;i++){
            int r = x*(i+2)-1;
            if(r>=arr.length){
                r=arr.length-1;
            }
            
        }
    }

    private static class MThread extends Thread{
        private int[] arr;
        private int l, r;

        public MThread(int[] arr, int l, int r){
            this.arr=arr;
            this.l=l;
            this.r=r;
        }

        public void run(){
            mergeSort(arr,l,r);
        }
    }

    private static void merge(int[] arr, int l, int m, int r){
        int[] x = new int[arr.length];

        for(int i = l;i<=r;i++){
            x[i] = arr[i];
        }

        int i =l;
        int j = m+1;
        int k = l;//je tu schvalne pro zapis do pole, ktere jde pryc

        while(i<=m && j<=r){
            if(x[i]<=x[j]){
                arr[k]=x[i];
                i++;
            }
            else{
                arr[k]=x[j];
                j++;
            }
            k++;
        }

        while(i<=m){
            arr[k]=x[i];
            i++;
            k++;
        }

    }



}
