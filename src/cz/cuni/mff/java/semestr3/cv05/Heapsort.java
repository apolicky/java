package cz.cuni.mff.java.semestr3.cv05;

public class Heapsort {

    public static void heapSort(int[] array) {
        //int n=a.length;
        heapify(array, array.length);
        int end = array.length - 1;
        while (end > 0) {
            swap(array, end, 0);
            end--;
            siftDown(array, 0, end);
        }
    }

    public static void heapify(int[] a, int length) {
        int start=(length/2)-1;
        while(start >=0){
            siftDown(a, start, length-1);
            start--;
        }

    }

    public static void main(String[] args){
        int[] array = {5,6,3,5,7,2,7,9};

        heapSort(array);
        for(int i:array){
            System.out.println(i);
        }
    }

    public static void swap(int[] array, int i, int j){
        int x=array[i];
        array[i]=array[j];
        array[j]=x;
    }

    public static void siftDown(int[] array, int start, int end){
        int root=start;

        while((root*2 +1)<=end){
            int leftChild = root*2 +1;

            if (leftChild + 1 <= end && array[leftChild] < array[leftChild+1]){
                leftChild=leftChild+1;
            }
            if(array[root]<array[leftChild]){
                swap(array,root,leftChild);
                root=leftChild;
            }
            else{
                return;
            }
        }

    }
}
