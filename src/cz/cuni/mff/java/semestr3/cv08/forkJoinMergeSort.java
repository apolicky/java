package cz.cuni.mff.java.semestr3.cv08;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class forkJoinMergeSort {
    public static void main(String[] args) {
        int[] arr = {5, 6, 8, 3, 6, 3, 6, 8, 4, 8, 12, 95};

        mergeSort(arr);

        for( int i : arr){
            System.out.print(i+" ");
        }
        System.out.flush();

    }

    public static void mergeSort(int[] arr){
        MergeSortTask m = new MergeSortTask(arr,0,arr.length-1);
        ForkJoinPool.commonPool().invoke(m);
    }

    private static class MergeSortTask extends RecursiveAction{
        private int[] arr;
        int l,r;

        public MergeSortTask(int[] arr, int l, int r){
            this.arr=arr;
            this.l=l;
            this.r=r;
        }

        @Override
        protected void compute(){
            if(l<r){
                MergeSortTask m1 = new MergeSortTask(arr,l,(l+r)/2);
                MergeSortTask m2 = new MergeSortTask(arr,((l+r)/2) +1,r);

                invokeAll(m1,m2);

                merge(arr,l,(l+r)/2,r);

            }
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
