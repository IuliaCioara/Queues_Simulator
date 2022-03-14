import java.util.ArrayList;
/**
 * Clasa ce modeleaza comportarea unei cozi.
 */
public class Coada {
    private int cur1;
    private ArrayList<Client> clienti = new ArrayList<Client>();
    private int index;

    public int getCurrent() {
        return clienti.get(0).getServiceTime();
    }

    public void updateCurrent(int cur2) {
        clienti.get(0).setServiceTime(cur2);


    }

    public void setCurrent(int current) {
        this.cur1 = current;
    }


    public void addClient(Client c) {
        clienti.add(c);
    }

    public Client getClient(int index) {
        return clienti.get(index);

    }

    public int getSize() {
        return clienti.size();
    }

    public int getSuma(){
        int sumaServiceTime = 0;
        if(clienti.isEmpty()) return 0;
        else {
            for(Client a : clienti) {
                sumaServiceTime = sumaServiceTime + a.getServiceTime();
            }
            return sumaServiceTime;
        }
    }

    public String toString() {
        if(clienti.isEmpty()) return "0";
        String string = "";
        for(Client a : clienti) {
            string = string + " " + a.getServiceTime();
        }
        return string;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }

    public void removeClient() {
        clienti.remove(0);
    }
    public int elemLeft() {
        return clienti.size();
    }

}
