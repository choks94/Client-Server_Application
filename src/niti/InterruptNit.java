/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.PokretanjeServera;

/**
 *
 * @author STEFAN94
 */
public class InterruptNit extends Thread {

    ServerSocket serverSocket;
    PokretanjeServera pokretanjeServera;

    public InterruptNit(ServerSocket serverSocket, PokretanjeServera pokretanjeServera) {
        this.serverSocket = serverSocket;
        this.pokretanjeServera = pokretanjeServera;
    }

    @Override
    public void run() {

        boolean interuptovan = false;
        while (!interuptovan) {

            if (pokretanjeServera.isInterrupted()) {
                try {
                    serverSocket.close();
                    interuptovan = true;
                } catch (IOException ex) {
                    Logger.getLogger(InterruptNit.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
