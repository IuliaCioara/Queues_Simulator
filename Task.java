/**
 * Clasa ce modeleaza un thread.
 */
public class Task extends Thread {
    private int threadIndex;
    private int current = 0;
    private boolean goThread = false;

    Task(int threadIndex) {

        this.threadIndex = threadIndex;
    }
    public void  run() {

        while(true) {
            if(goThread)
                current--;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Exceptie in thread");
            }

        }
    }

    public int getThreadIndex() {
        return threadIndex;
    }

    public void setCurrent(int curent) {
        this.current = curent;
        if(curent != 0) goThread  = true;
    }
    public int getCurrent() {
        return current;
    }
}
