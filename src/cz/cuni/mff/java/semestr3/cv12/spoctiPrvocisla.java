package cz.cuni.mff.java.semestr3.cv12;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.LongStream;

public class spoctiPrvocisla {
    public static void main(String[] args){
        if(args.length<2){
            System.out.println("spatny pocet argumentu.");
            System.exit(1);
        }

        long start = Long.parseLong(args[0]);
        long stop = Long.parseLong(args[1]);

        // long result = countPrimes(start,stop);
        //  System.out.println(result);

        long result = countStreamPrimes(start,stop);
        System.out.println(result);

        /*
        try{
          long  result = countParaPrimes(start,stop);

        }
        catch( InterruptedException e){

        }
        catch (ExecutionException ex){

        }
        */
    }

    private static long countStreamPrimes(long start, long stop){
        return LongStream.rangeClosed(start,stop).
                parallel().
                filter(i->LongStream.rangeClosed(2,(long) Math.sqrt(i)).noneMatch(j->i%j==0)).
                count();
    }

    private static long countPrimes(long start, long stop){
        long vysledek=0;
        for(long number = start; number < stop; number++){
            if(isPrime(number)){
                vysledek++;
            }
        }
        return vysledek;
    }

    private static long countParaPrimes(long start, long stop)throws InterruptedException, ExecutionException {
        int nThreads = Runtime.getRuntime().availableProcessors();

        List<Callable<Long>> calls = new ArrayList<>(nThreads);
        long x = (stop-start+1)/nThreads;
        for(long i = 0; i< nThreads;i++){
            long high = start +((i+1)*x);
            if(high>stop||(i==(nThreads-1)&&high<stop-1)){
                high = stop-1;
            }
            calls.add(new CountPrimesCallable(start+(i*x),high));


        }

        ExecutorService es = Executors.newWorkStealingPool();
        List<Future<Long>> results = new ArrayList<>(nThreads);
        for(Callable<Long> c : calls){
            Future<Long> f = es.submit(c);
            results.add(f);
        }

        long result=0;
        for(Future<Long> f : results){
            result+=f.get();
        }
        return result;
    }

    private static class CountPrimesCallable implements Callable<Long>{
        private long start,stop;

        public CountPrimesCallable(long start, long stop){
            this.start=start;
            this.stop=stop;
        }

        @Override
        public Long call() throws Exception{
            long count=0;
            for(long number = start; number<stop;number++){
                if(isPrime(number))
                    count++;
            }
            return count;
        }
    }

    private static boolean isPrime(long number){
        if(number==1){
            return false;
        }
        for(long i=2L;i<=(int)Math.sqrt(number);i++){
            if(number%i==0){
                return false;
            }
        }
        return true;
    }
}
