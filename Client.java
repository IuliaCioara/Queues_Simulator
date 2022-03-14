public class Client implements Comparable<Client>{
    private int arrivalTime;
    private int serviceTime;

    Client (int arr, int ser) {
        setArrivalTime(arr);
        setServiceTime(ser);
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    /**
     * Metoda care verifica valoarea componentelor.
     */
    @Override
    public int compareTo(Client arg) {
        if(arrivalTime > arg.arrivalTime) return 1;
        else if (arrivalTime == arg.arrivalTime) return 0;
        else return -1;

    }
    /**
     * Metoda folosita pentru reprezentarea unui client.
     */
    public void represent() {
        System.out.println("arr = " + getArrivalTime() + " ser = " + getServiceTime());
    }
}