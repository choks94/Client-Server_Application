/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.Clan;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import domen.Primerak;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class ObrisiKnjiguSO extends OpstaSistemskaOperacija {

    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {

        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            DatabaseBroker.getInstanca().delete(odo);
            Knjiga k = (Knjiga) odo;
            Primerak p = new Primerak();
            p.setIsbn(k.getIsbn());
//            DatabaseBroker.getInstanca().obrisiSvePrimerkeZaDatuKnjigu(odo);
            DatabaseBroker.getInstanca().delete(p);
            so.setOdgovor(true);
            so.setPoruka("Uspesno obrisani podaci o knjizi");
        } catch (Exception ex) {
            Logger.getLogger(UlogujRadnikaSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno obrisani podaci o knjizi");
        }
        return so;
    }

    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        ArrayList<Clan> listaClanovaZaKnjigu = DatabaseBroker.getInstanca().vratiClanoveZaKnjigu(odo);
        String clanovi = "";
        if (listaClanovaZaKnjigu.size() > 0) {
            for (Clan clan : listaClanovaZaKnjigu) {
                String cl = "\n " + clan.getIme() + " " + clan.getPrezime() + " JMBG: " + clan.getJmbg() + "";
                clanovi += cl;
            }
//            so.setOdgovor(false);
            String info = (listaClanovaZaKnjigu.size() == 1) ? "Knjiga je zaduzena kod clana:" + clanovi : "Knjiga je zaduzena kod clanova:" + clanovi;
//            so.setPoruka(info);
//            return so;
            throw new Exception(info);
        }
        Knjiga k = (Knjiga) odo;
        Primerak p = new Primerak();
        p.setIsbn(k.getIsbn());
        ArrayList<Primerak> listaPrimerakaZaKnjigu = DatabaseBroker.getInstanca().vratiPrimerke(odo);
      
        int brojac = 0;
        for (Primerak primerak : listaPrimerakaZaKnjigu) {
            if (primerak.isZaduzen()) {
                brojac++;
            }
        }
        if (brojac > 0) {
//            so.setOdgovor(false);
            String info2 = "Nisu razduzeni svi primerci date knjige";
//            so.setPoruka(info2);
//            return so;
            throw new Exception(info2);

        }
    }

}
