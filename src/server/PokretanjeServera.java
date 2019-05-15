/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import niti.InterruptNit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author STEFAN94
 */
public class PokretanjeServera extends Thread {

    @Override
    public void run() {

        ServerSocket serverSocket;
        ObradaKlijentskihZahteva obradaKlijentskihZahteva = null;

        try {
            serverSocket = new ServerSocket(9000);
            System.out.println("Server pokrenut");
            InterruptNit interruptNit = new InterruptNit(serverSocket, this);
            interruptNit.start();
            while (!isInterrupted()) {

                Socket socket = serverSocket.accept();
                System.out.println("Klijent se nakacio");
                obradaKlijentskihZahteva = new ObradaKlijentskihZahteva(socket);
                obradaKlijentskihZahteva.start();
                kontrola.Kontroler.getInstanca().setS(socket);

            }

        } catch (IOException ex) {
            if (obradaKlijentskihZahteva != null) {
                try {
                    obradaKlijentskihZahteva.interrupt();
                    obradaKlijentskihZahteva = null;
                } catch (Exception e) {
                    System.out.println("Problem u zaustavljanju niti za obradu zahteva");
                }
            }
            System.out.println("Server zaustavljen");
            System.out.println("" + this.getState());
            this.getState();
            System.out.println("Greska " + ex.getMessage());
            return;
        }
    }

}
