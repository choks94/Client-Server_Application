/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import domen.Primerak;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontrola.Kontroler;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class IzmeniKnjiguSO extends OpstaSistemskaOperacija {

    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            DatabaseBroker.getInstanca().izmeniKnjigu(odo);
            Knjiga knjigaZaIzmenu = (Knjiga) odo;
            if (knjigaZaIzmenu.getListaPrimeraka().size() > 0) {
                for (Primerak p : knjigaZaIzmenu.getListaPrimeraka()) {
                    DatabaseBroker.getInstanca().update(p);
                    
                }
            }
            int brNovihPrimeraka = knjigaZaIzmenu.getBrojPrimeraka();
            if (brNovihPrimeraka > 0) {
                int sledeciID = DatabaseBroker.getInstanca().vratiMAksIDPRimerka(knjigaZaIzmenu);
                Primerak p = null;
                for (int i = sledeciID; i < sledeciID + brNovihPrimeraka; i++) {
                    p = new Primerak();
                    p.setPrimerakId(i);
                    p.setIsbn(knjigaZaIzmenu.getIsbn());
                    p.setRashodovan(false);
                    p.setZaduzen(false);
                    DatabaseBroker.getInstanca().insert(p);
//                    DatabaseBroker.getInstanca().unesiPrimerkeKnjige(i, knjigaZaIzmenu.getIsbn());
                }
            }
            so.setOdgovor(true);
            so.setPoruka("Uspesno izmenjeni podaci o knjizi");
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno izmenjeni podaci o knjizi");
        }
        return so;
    }

    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {

    }

}
