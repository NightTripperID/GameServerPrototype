package demo.shutdown;

import kuusisto.tinysound.TinySound;

public class ShutdownThread extends Thread {

    @Override
    public void run() {
        System.out.println("Shutting down TinySound...");
        TinySound.shutdown();
        System.out.println("TinySound shutdown complete!");

    }
}
