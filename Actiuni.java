import java.util.ArrayList;
import java.util.Random;
/**
 * Clasa ce contine diferite metode folosite pentru calcul.
 */
public class Actiuni {
    static int sumaMAX = 0;
    static int secunda = 0;
    static int minut = 0;
    static int lastTime = 0;
    static int nrAdaugari = 0;
    static int sumaAverage = 0;
    /**
     * Metoda ce genereaza random clienti intre arrivalMAX si arrivalMin si serviceMAX si serviceMin
     */
    public ArrayList<Client> generateRandom(int nrClienti,int arrivalMAX,int arrivalMin,int serviceMAX, int serviceMin, ArrayList<Client> waitingClients) {
        Random random = new Random();

        while(nrClienti > 0) {
            try{
                int randomArrival = random.nextInt((arrivalMAX - arrivalMin) + 1) + arrivalMin;
                int randomService = random.nextInt((serviceMAX - serviceMin) + 1) + serviceMin;
                waitingClients.add(new Client(randomArrival,randomService));
                nrClienti--;
            }catch(Exception E) {}
        }
        return waitingClients;

    }
    /**
     * Metoda ce printeaza coada.
     */
    /*public void printCoada(Coada coada){
        for(int i = 0; i < coada.getSize(); i++)
            System.out.print(coada.getClient(i).getServiceTime() + " ");

        if(coada.getSize() == 0)
            System.out.println("0");
        else
            System.out.println("");
    }*/

    /**
     * Metoda pentru pornirea threadurilor.
     */
    public ArrayList<Task> startThreads(int numar) {
        System.out.println("Entered startThread");
        ArrayList<Task> threads = new ArrayList<Task>();
        for(int i = 0; i < numar; i++) {
            Task task = new Task(i);
            threads.add(task);
            task.start();
        }
        return threads;
    }

    /**
     * Metoda pentru determinarea minumului
     */
    public int findMin(ArrayList<Coada> cozi){
        int indexMinim = -1;
        int sumaMin =  Integer.MAX_VALUE ;//cozi.get(0).getSuma();
        for(Coada a : cozi) {
            if(a.getSuma() <= sumaMin) {
                sumaMin = a.getSuma();
                indexMinim = a.getIndex();
            }
        }
        return indexMinim;
    }
    /*public void printCozi(ArrayList<Coada> cozi) {
        //System.out.println("entered printCozi");
        for (int i = 0; i < cozi.size(); i++) {
            printCoada(cozi.get(i));
        }
    }*/
    /**
     * Metoda ce verifica daca lista este goala.
     */
    public boolean checkIfEmpty(ArrayList<Boolean> emp) {
        boolean empty = true;
        for(boolean a : emp) {
            if(!a) empty = false;
        }
        return empty;

    }

    public static void sumaTotalaCozi(ArrayList<Coada> cozi,int timeS, int timeM) {
        System.out.println("Entered suma totala cozi");

        int suma = 0;
        for(Coada a : cozi) {
            suma = suma + a.getSuma();
        }
        if(suma > sumaMAX) {
            sumaMAX = suma;
            secunda = timeS;
            minut = timeM;
        }

    }
    public static int getPeakSecond() {
        return secunda;
    }
    public static int getPeakMinute() {
        return minut;
    }
    /*public static void average(ArrayList<Coada> cozi, int sec) {

        for(Coada a : cozi) {
            //Detectam cand a avut loc o adaugare
            if(a.getSize() > lastTime ) {
                System.out.println("A avut loc o adaugare in coada" + a.getIndex() + " la secunda " + sec);
                lastTime = a.getSize();
            }
        }
    }*/

    public static void incChanges(ArrayList<Coada> cozi, int secunde) {
        nrAdaugari++;
        System.out.println("La secunda " + secunde + " a avut loc adaugare");
        for(Coada a : cozi) {
            if(a.getSize() > 1)
                sumaAverage = sumaAverage + a.getSuma() - a.getCurrent();
        }
    }

    public static double getAverage(int nrCozi) {
        return (sumaAverage * 1.00)/(nrAdaugari - nrCozi);
    }

}
