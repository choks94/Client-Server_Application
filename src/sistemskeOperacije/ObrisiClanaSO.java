/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.Clan;
import domen.Clanarina;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class ObrisiClanaSO extends OpstaSistemskaOperacija {
    
    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            DatabaseBroker.getInstanca().delete(odo);
            Clan c = (Clan) odo;
            Clanarina cl = new Clanarina();
            cl.setClan(c);
//            DatabaseBroker.getInstanca().obrisiClanarinuzaClana(odo);
            DatabaseBroker.getInstanca().delete(cl);
            so.setOdgovor(true);
            so.setPoruka("Uspesno obrisani podaci o clanu");
        } catch (Exception ex) {
            Logger.getLogger(UlogujRadnikaSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno obrisani podaci o clanu");
        }
        
        return so;
    }
    
    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        ArrayList<Knjiga> listaZaduzenihKnjiga = new ArrayList<>();
        Clan c = (Clan) odo;
        listaZaduzenihKnjiga = DatabaseBroker.getInstanca().vratiZaduzenjaClana(c);
        if (listaZaduzenihKnjiga.size() > 0) {
            
            String naziviKnjiga = "";
            for (Knjiga knjiga : listaZaduzenihKnjiga) {
                String naziv = "\n " + knjiga.getNaziv();
                naziviKnjiga += naziv;
            }
            String poruka = ((listaZaduzenihKnjiga.size() == 1) ? "Korisnik nije razduzio knjigu:" + naziviKnjiga : "Korisnik nije razduzio knjige:" + naziviKnjiga);
            throw new Exception(poruka);
        }
    }
}
