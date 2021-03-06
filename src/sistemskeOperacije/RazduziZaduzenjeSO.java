/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.OpstiDomenskiObjekat;
import domen.Primerak;
import domen.Zaduzenje;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class RazduziZaduzenjeSO extends OpstaSistemskaOperacija {

    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        ServerskiOdgovor so = new ServerskiOdgovor();

        try {
//            DatabaseBroker.getInstanca().razduzi(odo);
            DatabaseBroker.getInstanca().update(odo);
            Zaduzenje z = (Zaduzenje) odo;
            Primerak p = z.getPrimerak();
            DatabaseBroker.getInstanca().update(p);
//            DatabaseBroker.getInstanca().razduziPrimerak(odo);
            so.setOdgovor(true);
            so.setPoruka("Uspesno razduzeno");
        } catch (Exception ex) {
            Logger.getLogger(UlogujRadnikaSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno razduzeno");
        }
        return so;
    }

    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
    }

}
