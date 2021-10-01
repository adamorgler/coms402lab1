import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class Project1 {
    public static void main(String[] args){
        int[] goalState = { 1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[] startState = new int[9];

//        if (args.length != 2) {
//            System.out.println("Invalid number of arguments!");
//            return;
//        }
//        String fname = args[0];
//        String pname = args[1];


        String fname = "src/S2.txt";
        String pname = "h1";
        pname = pname.toUpperCase();
        try {
            File f = new File(fname);
            Scanner sc = new Scanner(f);
            String next;
            String nextLine;
            int i = 0;
            while(sc.hasNextLine()) {
                nextLine = sc.nextLine();
                Scanner ls = new Scanner(nextLine);
                while(ls.hasNext()) {
                    next = ls.next();
                    if (next.equals("_")) {
                        startState[i] = 0;
                    } else {
                        startState[i] = Integer.parseInt(next);
                    }
                    i++;
                }
            }
            System.out.println("opened " + fname + "...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("failed to open " + fname + "...");
            return;
        }
        System.out.println("checking if possible...");
        int inversions = inversionCounter(startState);
        if (inversions % 2 == 1) {
            System.out.println("There are an odd number of inversions (" + inversions + ")! No possible solution!");
            return;
        }

        AlgoThread at = new AlgoThread(pname, startState, goalState);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(at);

        try {
           future.get(15, TimeUnit.MINUTES);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        } catch (TimeoutException e) {
            System.out.println("==" + pname.toUpperCase() + "==");
            return;
        } catch (OutOfMemoryError e){
            e.printStackTrace();
            return;
        }
    }
    public static int inversionCounter(int[] state) {
        int c = 0;
        for (int i = 0; i < state.length; i++) {
            int n = state[i];
            for (int j = i; j < state.length; j++) {
                int m = state[i];
                if (n > m && m != 0) {
                    c++;
                }
            }
        }
        return c;
    }
}
