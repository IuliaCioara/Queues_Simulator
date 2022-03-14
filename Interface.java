
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Interface {
    private  int  nrClienti ;
    private int minute;
    private int numarCozi;
    int randomNum;
    private JTextField nrCozi;
    private JTextField nrClientiText;
    private JLabel larrivalTime, lserviceTime;
    private JButton ok;
    private JButton stop;
    private JLabel lnrCozi, lnrClienti;
    JLabel simulating;
    final static boolean shouldfill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    GridBagConstraints c = new GridBagConstraints();
    private JTextField arrivalTimeMAX;
    private JTextField arrivalTimeMin;
    private JTextField serviceTimeMAX;
    private JTextField serviceTimeMin;
    private JTextField time;
    private JTextField casa;
    private int delay = 1000; // timpul de delay pt timer = 1 secunda
    private int sec = 0; // variabila pentru secunde
    Random random;
    boolean start = false;
    boolean serving = false;
    boolean set = false;
    private boolean ThreadStarted = false;
    ArrayList<Client> waitingClients;
    ArrayList<Boolean> first ;
    ArrayList<JLabel> labels;
    ArrayList<String> textToDisplay ;
    ArrayList<Coada> cozi;
    ArrayList<Task> threads;
    ArrayList<JTextField> textFields;
    ArrayList<Boolean> emp = new ArrayList<Boolean>();
    final String mesajFormat = "Format invalid.\nVerificati instructiunile pentru detalii";
    String mesajSfarsitSimulare = "Timpul total de servire ";
    final String mesajOrdineIncorecta = "Format invalid.\nMinumul nu poate fi mai mare decat maximul";
    private Timer clock;
    PrintWriter writer ;
    Actiuni actiune = new Actiuni();

    /**
     * Metoda care adauga componentele in panou si contine sub-metodele de action listener.
     */
    public void addComponentsToPane(Container pane) {
        try{
            writer = new PrintWriter("Output.txt", "UTF-8");
        }catch(Exception E) {System.out.println("File not found");}

        /*
         * Adaugam TextField pentru timer.
         */
        time = new JTextField(3);
        time.setMargin(new Insets(0,0,0,0));

        /*
         * ArrayList-urile pentru a memora threadurile, clientii si restul datelor
         */
        random = new Random();
        threads = new ArrayList<Task>();
        first = new ArrayList<Boolean>();
        waitingClients = new ArrayList<Client>();
        textFields = new ArrayList<JTextField>();
        labels = new ArrayList<JLabel>();

        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
        if(shouldfill){
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        pane.setLayout(new GridBagLayout());
        /*
         * Adaugam label pentru numarul de cozi.
         *
         */
        lnrCozi = new JLabel("Numarul de cozi:",JLabel.LEFT);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.LAST_LINE_END;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        pane.add(lnrCozi, c);

        /*
         * Adaugam TextField pentru numarul de cozi.
         *
         */
        nrCozi = new JTextField(3);
        c.gridx++;
        c.anchor = GridBagConstraints.NORTH;
        pane.add(nrCozi, c);

        /*
         * Label si TextField pentru numaurul de clienti.
         */
        lnrClienti = new JLabel("Numarul de clienti:", JLabel.LEFT);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.LAST_LINE_END;
        pane.add(lnrClienti, c);

        nrClientiText = new JTextField(3);
        c.gridx++;
        c.anchor = GridBagConstraints.NORTH;
        pane.add(nrClientiText, c);

        /*
         * Label si TextField pentru campurile de introducere a intervalului pentru Arrival time.
         */
        larrivalTime = new JLabel("Arrival time");
        c.gridx = 0;
        c.gridy = 2;
        pane.add(larrivalTime, c);

        arrivalTimeMin = new JTextField(3);
        c.gridx++;
        pane.add(arrivalTimeMin, c);

        arrivalTimeMAX = new JTextField(3);
        c.gridx++;
        pane.add(arrivalTimeMAX, c);

        /*
         * Label si TextField pentru campurile de introducere a intervalului pentru Arrival time.
         */
        lserviceTime = new JLabel("Service time");
        c.gridy++;
        c.gridx = 0;
        pane.add(lserviceTime, c);

        serviceTimeMin = new JTextField(3);
        c.gridx++;
        pane.add(serviceTimeMin, c);

        serviceTimeMAX = new JTextField(3);
        c.gridx++;
        pane.add(serviceTimeMAX, c);

        /*
         * Buton pentru simulare care contine un ActionListener.
         */
        ok = new JButton("Simulare");
        ok.setFont(ok.getFont().deriveFont(19.0f));
        ok.setBackground(new Color(209,132,160));
        ok.setMargin(new Insets(0,0,0,0));
        c.gridx = 5;
        c.gridy = 4;
        ActionListener action;
        ok.addActionListener(action = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered ActionListener from START button");

                /*
                 * Adaugam dinamic un label care va aparea numai dupa apasarea butonului de "START"
                 */

                simulating = new JLabel("Simulating...");
                simulating.setFont(simulating.getFont().deriveFont(15f));
                c.gridx = 0;
                c.gridy = 0;
                c.gridwidth = 3;
                c.gridwidth  = 5;
                pane.add(simulating);
                try{
                    ok.setVisible(false);
                    numarCozi = Integer.parseInt(nrCozi.getText());

                    start = true;
                    c.gridx = 5;
                    c.gridy = 5;

                    //Parsare informatii in tip integer
                    int arrivalMin = Integer.parseInt(arrivalTimeMin.getText());
                    int arrivalMAX = Integer.parseInt(arrivalTimeMAX.getText());
                    int serviceMin = Integer.parseInt(serviceTimeMin.getText());
                    int serviceMAX = Integer.parseInt(serviceTimeMAX.getText());
                    nrClienti = Integer.parseInt(nrClientiText.getText());

                    if(arrivalMAX < arrivalMin) {
                        popUp(mesajOrdineIncorecta, true);
                        resetAll();
                    }
                    if(serviceMAX < serviceMin) {
                        popUp(mesajOrdineIncorecta, true);
                        resetAll();
                    }

                    //Generare random a clientilor
                    waitingClients = actiune.generateRandom(nrClienti, arrivalMAX, arrivalMin, serviceMAX, serviceMin, waitingClients);

                    //Sortam lista
                    Collections.sort(waitingClients);

                    //reprezentare
                    writer.println("Arrival Time  Service Time");
                    for(Client a : waitingClients) {
                        a.represent();
                        writer.println("  " + a.getArrivalTime() + "              " + a.getServiceTime());
                    }
                    writer.println();
                    pane.add(Box.createRigidArea(new Dimension(30,40)));

                    //adaugare semi-dinamica a textfieldurile cu text
                    cozi = new ArrayList<Coada>();
                    textToDisplay = new ArrayList<String>();
                    //Parcurgem numarul de cozi si adaugam componente necesare in ArrayList-s
                    for(int j = 1; j <= numarCozi; j++) {
                        boolean prim = false;
                        first.add(prim);
                        emp.add(false);
                        casa = new JTextField(10);
                        cozi.add(new Coada());
                        cozi.get(j - 1).setIndex(j-1);

                        textFields.add(casa);
                        casa.setEditable(false);
                        JLabel server = new JLabel("Server "+j);
                        labels.add(server);

                        //Adaugam in panel labelurile
                        c.gridy++;
                        c.gridx = 0;

                        pane.add(server, c);
                        c.gridx++;
                        pane.add(casa, c);

                        textToDisplay.add("");
                    }
                    /*
                     * Verificam daca numarul de clienti este mai mic decat
                     * numarul de cozi pentru a putea afisa corect dinamic.
                     */
                    if(nrClienti < numarCozi) {
                        for(int j = 0; j < numarCozi - nrClienti; j++) {
                            emp.set(j, true);
                        }
                    }

                    /*
                     * Adaugam butonul de stop si ii adugam un ActionListener
                     */
                    stop = new JButton("STOP");
                    c.gridy++;
                    c.gridx = 4;
                    stop.setFont(stop.getFont().deriveFont(19.0f));
                    stop.setBackground(new Color(209,132,160));
                    stop.setMargin(new Insets(0,0,0,0));
                    pane.add(stop, c);
                    stop.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Entered reset");
                            resetAll();


                        }

                    });
                    //Cand se incepe simularea toate celelalte butone se ascund
                    nrClientiText.setVisible(false);
                    lnrClienti.setVisible(false);
                    larrivalTime.setVisible(false);
                    lserviceTime.setVisible(false);
                    arrivalTimeMAX.setVisible(false);
                    arrivalTimeMin.setVisible(false);
                    serviceTimeMAX.setVisible(false);
                    serviceTimeMin.setVisible(false);
                    lnrCozi.setVisible(false);
                    nrCozi.setVisible(false);


                }catch(Exception exception) {
                    popUp(mesajFormat,true);
                    System.exit(0);

                };

            }



        });
        //adaugam butonul de START
        pane.add(ok, c);

        c.gridx= 0;
        c.gridy++;
        time.setEditable(false);
        pane.add(time, c);
        System.out.println("Start = " + start);
        /**
         * Action Listener-ul necesar timerului.
         */
        ActionListener ceas = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Entered ActionPerformed from clock");

                if (start) {
                    refreshGUI();

                    if(sec == 60) {
                        clock.restart();
                        sec = 1;
                        minute++;

                    }else sec++;
                    /*
                     * Pornim threaduri-le o singura data in program.Apoi doar le vom folosi.
                     */
                    if(!ThreadStarted) {
                        threads = actiune.startThreads(numarCozi);
                        ThreadStarted = true;
                    }

                    String timp = "" + minute + ":" + sec;

                    time.setText(timp);
                    time.setEditable(false);
                    //Adaugam clienti in cozi.
                    if(!waitingClients.isEmpty())
                        while(sec == waitingClients.get(0).getArrivalTime()) {
                            //Determinam minimul la care va merge un nou client.
                            int iMin = actiune.findMin(cozi);
                            Actiuni.incChanges(cozi, sec);	//Metoda care detecteaza cate schimbari au avut loc in fiecare coada.
                            //Aceasta este nevoie in calculul mediei de asteptare.
                            cozi.get(iMin).addClient(waitingClients.get(0));
                            waitingClients.remove(0);
                            Actiuni.sumaTotalaCozi(cozi, sec, minute);//peak hour

                            if(waitingClients.isEmpty()) break;
                            changeData();
                        }

                    changeData();
                }
                /**
                 * Afisam intr-un fisier de iesire starea cozilor si timpul.
                 */
                try{
                    if(!cozi.isEmpty()) writer.println("Time =" + sec);
                    for(int i = 0; i < nrClienti; i++) {

                        writer.println(cozi.get(i).toString());

                    }
                }catch(Exception e) {}


            }
        };

        clock = new Timer(delay, ceas);
        clock.setInitialDelay(0);
        clock.start();

    }
    /**
     * Metoda care citeste elemente din cozi si le transmite threadurilor.
     */
    private void changeData() {
        System.out.println("Entered Change Data");
        int j = 0;
        System.out.println("clienti ramasi = " + waitingClients.size() + " cozi ramase = " + cozi.size());

        while(j < cozi.size()) {
            if(waitingClients.isEmpty() && cozi.isEmpty()) {
                System.out.println("entered mesajFinal");
            }
            System.out.println();
            try{
                if(cozi.get(j).elemLeft() <=0 &&  waitingClients.size() > 0)
                    first.set(j, false);
            }catch(Exception E) {};

            if(!first.get(j)) {
                try{
                    int curent = cozi.get(j).getCurrent();
                    threads.get(j).setCurrent(curent);
                }catch(Exception E){j++;
                    continue;
                }

                first.set(j, true);

            }
            else {
                try{
                    if(threads.get(j).getCurrent() <= 0){
                        cozi.get(j).removeClient();
                        System.out.println("A eliminat un client");

                        if(cozi.get(j).elemLeft() > 0) {

                            threads.get(j).setCurrent(cozi.get(j).getCurrent());

                        }
                        else {
                            if(waitingClients.isEmpty())
                                cozi.get(j).removeClient();

                            else continue;
                        }
                    }

                    else {
                        int curent = threads.get(j).getCurrent();
                        cozi.get(j).updateCurrent(curent);
                    }
                }catch(Exception E) {
                    emp.set(j, true);

                    if(actiune.checkIfEmpty(emp)) {
                        popUp(mesajSfarsitSimulare, false);
                        j = nrClienti + 1;
                        resetAll();


                    }
                    else j++;
                    System.out.println("Entered big catch");
                    continue;}

            }
            j++;
        }
        refreshGUI();
    }
    /**
     * Metoda pentru refacerea GUI.
     */
    private void refreshGUI() {
        try{
            int j = 0;
            while(j < numarCozi) {
                textFields.get(j).setText(cozi.get(j).toString());
                if(cozi.get(j).elemLeft() <= 0 || cozi.get(j).toString().equalsIgnoreCase("0")) {
                    textFields.get(j).setVisible(false);

                    labels.get(j).setVisible(false);
                }
                else {textFields.get(j).setVisible(true);
                    labels.get(j).setVisible(true);
                }

                j++;
            }
        }catch(Exception E) {}
        writer.println();
    }
    /**
     * Metoda ce afiseaza o fereastra de tip Pop Up cu diferite mesaje si erori.
     * Daca error e true atunci se va afisa o eroare de tip "Error"
     * Daca erro e fake atunci se va afisa diferite mesaje cu rezultatul final.
     */
    private void popUp(String mesaj, boolean error) {
        System.out.println("Entered popUp");
        if(error)
            JOptionPane.showMessageDialog(null,
                    mesaj,
                    "Eroare",
                    JOptionPane.ERROR_MESSAGE);
        else {
            mesaj = mesaj + minute + ":" + sec;
            mesaj = mesaj + "\nOra de varf " + Actiuni.getPeakMinute() + ":" + Actiuni.getPeakSecond();
            mesaj = mesaj + "\nTimpul de asteptare mediu : "  + String.format("%.2f", Actiuni.getAverage(numarCozi));

            JOptionPane.showMessageDialog(null,
                    mesaj,
                    "Sfarsit simulare",
                    JOptionPane.INFORMATION_MESSAGE);

        }
    }
    /**
     * Metoda ce genereaza GUI
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Simulare");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane());
        frame.setPreferredSize(new Dimension(600, 700));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    /**
     * Metoda care reseteaza toate campurile.

     */
    private void resetAll() {
        try {
            System.out.println("Entered resetAll");

            first.clear();
            sec= 0;
            start = false;
            waitingClients.clear();
            serving = false;
            set = false;
            minute = 0;
            numarCozi = 0;
            emp.clear();
            for(JTextField a : textFields)
                a.setVisible(false);
            for(JLabel a : labels)
                a.setVisible(false);
            textFields.clear();
            labels.clear();
            larrivalTime.setVisible(true);
            lserviceTime.setVisible(true);
            arrivalTimeMAX.setVisible(true);
            arrivalTimeMin.setVisible(true);
            serviceTimeMAX.setVisible(true);
            serviceTimeMin.setVisible(true);
            arrivalTimeMAX.setText("");
            arrivalTimeMin.setText("");
            serviceTimeMAX.setText("");
            serviceTimeMin.setText("");
            lnrCozi.setVisible(true);
            nrCozi.setVisible(true);
            stop.setVisible(false);
            ok.setVisible(true);
            simulating.setVisible(false);
            nrCozi.setText("");
            time.setText("");
            clock.stop();
            writer.close();
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Entered Exception in resetAll");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Interface obj = new Interface();
        obj.createAndShowGUI();
    }
}
