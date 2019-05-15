/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import domen.Clan;
import domen.Clanarina;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import domen.Primerak;
import domen.Radnik;
import domen.Zaduzenje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontrola.Kontroler;
import operacije.Operacija;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class ObradaKlijentskihZahteva extends Thread {

    Socket klijentskiSocket;

    public ObradaKlijentskihZahteva(Socket klijentskiSocket) {
        this.klijentskiSocket = klijentskiSocket;
    }

    @Override
    public void run() {

        while (true) {
            ServerskiOdgovor serverskiOdgovor = new ServerskiOdgovor();
            KlijentskiZahtev klijentskiZahtev = primiZahtev();
            OpstiDomenskiObjekat odo = (OpstiDomenskiObjekat) klijentskiZahtev.getParametar();
            switch (klijentskiZahtev.getOperacija()) {
                case Operacija.ULOGUJ_RADNIKA:
//                    Radnik radnikZaLogovanje = (Radnik) klijentskiZahtev.getParametar();
//                    Radnik ulogovaniRadnik = Kontroler.getInstanca().ulogijRadnika(radnikZaLogovanje);
//                    serverskiOdgovor.setOdgovor(ulogovaniRadnik);
                    serverskiOdgovor = Kontroler.getInstanca().ulogijRadnika(odo);
                    break;
                case Operacija.VRATI_KORISNIKE:
//                    ArrayList<Clan> listaClanova = Kontroler.getInstanca().vratiClanove();
//                    serverskiOdgovor.setOdgovor(listaClanova);
                    serverskiOdgovor = Kontroler.getInstanca().vratiClanove(odo);
                    break;
                case Operacija.VRATI_KNJIGE:
//                    ArrayList<Knjiga> listaKnjiga = Kontroler.getInstanca().vratiKnjige();
//                    serverskiOdgovor.setOdgovor(listaKnjiga);
                    serverskiOdgovor = Kontroler.getInstanca().vratiKnjige(odo);
                    break;
                case Operacija.VRATI_SLEDECI_ID:
                    serverskiOdgovor = Kontroler.getInstanca().vratiID(odo);
                    break;
                case Operacija.PRETRAZI_CLANOVE:
//                    String pretraga = (String) klijentskiZahtev.getParametar();
//                    ArrayList<Clan> listaPretrazenihClanova = Kontroler.getInstanca().vratiClanovePoPretrazi(pretraga);
//                    serverskiOdgovor.setOdgovor(listaPretrazenihClanova);
                    serverskiOdgovor = Kontroler.getInstanca().vratiPretrazeneClanove(odo);
                    break;
                case Operacija.PRETRAZI_KNJIGE:
//                    Knjiga pretragaKnjiga = (Knjiga) klijentskiZahtev.getParametar();
//                    ArrayList<Knjiga> listaPretrazenihKnjiga = Kontroler.getInstanca().vratiKnjigePoPretrazi(pretragaKnjiga);
//                    serverskiOdgovor.setOdgovor(listaPretrazenihKnjiga);
                    serverskiOdgovor = Kontroler.getInstanca().vratiKnjigePoPretrazi(odo);
                    break;
//                case Operacija.PRETRAZI_KNJIGE_PO_ZANRU:
////                    Knjiga pretragaKnjigaPoZanru = (Knjiga) klijentskiZahtev.getParametar();
////                    ArrayList<Knjiga> listaPretrazenihKnjigaPoZanru = Kontroler.getInstanca().vratiKnjigePoPretrazi(pretragaKnjigaPoZanru);
////                    serverskiOdgovor.setOdgovor(listaPretrazenihKnjigaPoZanru);
//                    serverskiOdgovor = Kontroler.getInstanca().vratiKnjigePoPretrazi(odo);
//                    break;
                case Operacija.SACUVAJ_NOVOG_CLANA:
//                    Clan noviClan = (Clan) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().sacuvajNovogClana(noviClan);
                    serverskiOdgovor = Kontroler.getInstanca().sacuvajNovogClana(odo);
                    break;
                case Operacija.OBRISI_CLANA:
//                    Clan clanZaBrisanje = (Clan) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().obrisiClana(clanZaBrisanje);
                    serverskiOdgovor = Kontroler.getInstanca().obrisiClana(odo);
                    break;
                case Operacija.IZMENI_CLANA:
//                    Clan clanZaIzmenu = (Clan) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().izmeniClana(clanZaIzmenu);
//                    break;
                    serverskiOdgovor = Kontroler.getInstanca().izmeniClana(odo);
                    break;
                case Operacija.OBRISI_KNJIGU:
//                    Knjiga knjigaZaBrisanje = (Knjiga) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().obrisiKnjigu(knjigaZaBrisanje);
                    serverskiOdgovor = Kontroler.getInstanca().obrisiKnjigu(odo);
                    break;
                case Operacija.IZMENI_KNJIGU:
//                    Knjiga knjigaZaIzmenu = (Knjiga) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().izmeniKnjigu(knjigaZaIzmenu);
                    serverskiOdgovor = Kontroler.getInstanca().izmeniKnjigu(odo);
                    break;
                case Operacija.UNESI_KNJIGU:
//                    Knjiga novaKnjiga = (Knjiga) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().unesiNovuKnjigu(novaKnjiga);
                    serverskiOdgovor = Kontroler.getInstanca().unesiNovuKnjigu(odo);
                    break;
                case Operacija.VRATI_CLANARINU:
//                    Clan clan = (Clan) klijentskiZahtev.getParametar();
//                    Clanarina clanarina = Kontroler.getInstanca().vratiClanarinu(clan);
//                    serverskiOdgovor.setOdgovor(clanarina);
//                    break;
                    serverskiOdgovor = Kontroler.getInstanca().vratiClanarinu(odo);
                    break;
                case Operacija.EVIDENTIRAJ_CLANARINU:
//                    Clanarina evidentiranaClanarina = (Clanarina) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().evidentirajClanarinu(evidentiranaClanarina);
                    serverskiOdgovor = Kontroler.getInstanca().evidentirajClanarinu(odo);
                    break;
                case Operacija.VRATI_ZADUZENJA:
//                    Knjiga knjigaZaZaduzenje = (Knjiga) klijentskiZahtev.getParametar();
//                    ArrayList<Zaduzenje> listaZaduzenje = Kontroler.getInstanca().vratiZaduzenja(knjigaZaZaduzenje);
//                    serverskiOdgovor.setOdgovor(listaZaduzenje);
                    serverskiOdgovor = Kontroler.getInstanca().vratiZaduzenja(odo);
                    break;
                case Operacija.RAZDUZI:
//                    Zaduzenje zaduzenje = (Zaduzenje) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().razduzi(zaduzenje);
                    serverskiOdgovor = Kontroler.getInstanca().razduzi(odo);
                    break;
                case Operacija.VRATI_CLANOVE_SA_VAZECOM_CLANARINOM:
//                    ArrayList<Clan> listaVazecihClanova = Kontroler.getInstanca().vratiClanoveSaClanarinom();
//                    serverskiOdgovor.setOdgovor(listaVazecihClanova);
                    serverskiOdgovor = Kontroler.getInstanca().vratiClanoveSaClanarinom(odo);
                    break;
                case Operacija.VRATI_PRIMERKE:
//                    Knjiga k = (Knjiga) klijentskiZahtev.getParametar();
//                    ArrayList<Primerak> listaPrimeraka = Kontroler.getInstanca().vratiPrimerke(k);
//                    serverskiOdgovor.setOdgovor(listaPrimeraka);
//                    break;
                    serverskiOdgovor = Kontroler.getInstanca().vratiPrimerke(odo);
                    break;
                case Operacija.SACUVAJ_NOVO_ZADUZENJE:
//                    Zaduzenje novoZaduzenje = (Zaduzenje) klijentskiZahtev.getParametar();
//                    serverskiOdgovor = Kontroler.getInstanca().sacuvajNovoZaduzenje(novoZaduzenje);
                    serverskiOdgovor = Kontroler.getInstanca().sacuvajNovoZaduzenje(odo);
                    break;
                case Operacija.VRATI_ZADUZENJA_CLANA:
//                    Clan clanZaZaduzenja = (Clan) klijentskiZahtev.getParametar();
//                    ArrayList<Zaduzenje> listaZaduzenjaClana = Kontroler.getInstanca().vratiZaduzenjaClana(clanZaZaduzenja);
//                    serverskiOdgovor.setOdgovor(listaZaduzenjaClana);
//                    break;
                    serverskiOdgovor = Kontroler.getInstanca().vratiZaduzenja(odo);
                    break;
                case Operacija.PROVERI_CLANARINU:
                    serverskiOdgovor.setOdgovor(false);
                    Clan clanZaProveru = (Clan) klijentskiZahtev.getParametar();
                    ServerskiOdgovor so = Kontroler.getInstanca().vratiClanoveSaClanarinom(odo);
                    ArrayList<Clan> listaClanovaSaClanarinom = (ArrayList<Clan>) so.getOdgovor();
                    for (Clan c : listaClanovaSaClanarinom) {
                        if (clanZaProveru.getClanID() == c.getClanID()) {
                            serverskiOdgovor.setOdgovor(true);
                            break;
                        }
                    }
                    break;
            }

            posaljiOdgovor(serverskiOdgovor);
        }
    }

    public void posaljiOdgovor(ServerskiOdgovor serverskiOdgovor) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(klijentskiSocket.getOutputStream());
            objectOutputStream.writeObject(serverskiOdgovor);
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public KlijentskiZahtev primiZahtev() {
        KlijentskiZahtev klijentskiZahtev = new KlijentskiZahtev();

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(klijentskiSocket.getInputStream());
            klijentskiZahtev = (KlijentskiZahtev) objectInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }

        return klijentskiZahtev;
    }

}
