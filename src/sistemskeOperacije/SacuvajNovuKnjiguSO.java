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
public class SacuvajNovuKnjiguSO extends OpstaSistemskaOperacija {

    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        ServerskiOdgovor so = new ServerskiOdgovor();

        try {
            DatabaseBroker.getInstanca().unesiNovuKnjigu(odo);
            Knjiga novaKnjiga = (Knjiga) odo;
            Primerak p = null;
            for (int i = 1; i <= novaKnjiga.getBrojPrimeraka(); i++) {
                p = new Primerak();
                p.setPrimerakId(i);
                p.setIsbn(novaKnjiga.getIsbn());
                p.setRashodovan(false);
                p.setZaduzen(false);
                DatabaseBroker.getInstanca().insert(p);
//                DatabaseBroker.getInstanca().unesiPrimerkeKnjige(i, novaKnjiga.getIsbn());
            }
            so.setOdgovor(true);
            so.setPoruka("Uspesno sacuvano");
        } catch (SQLException ex) {
            Logger.getLogger(UlogujRadnikaSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno izmenjeni podaci o clanu");
        }
        return so;
    }

    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {

    }

}
