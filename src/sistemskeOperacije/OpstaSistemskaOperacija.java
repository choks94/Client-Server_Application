/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.OpstiDomenskiObjekat;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public abstract class OpstaSistemskaOperacija {

    public ServerskiOdgovor izvrsiTransakciju(OpstiDomenskiObjekat odo) {
        ServerskiOdgovor so = new ServerskiOdgovor();

        try {
            DatabaseBroker.getInstanca().ucitajDrajver();
            DatabaseBroker.getInstanca().otvoriKonekciju();
            proveriPreduslov(odo);
            so = izvrsiOperaciju(odo);
            DatabaseBroker.getInstanca().commit();
        } catch (Exception ex) {
            Logger.getLogger(OpstaSistemskaOperacija.class.getName()).log(Level.SEVERE, null, ex);
            so.setOdgovor(false);
            so.setPoruka(ex.getMessage());
        } finally {
            DatabaseBroker.getInstanca().zatvoriKonekciju();
        }
        return so;
    }

    public abstract ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception;

    public abstract void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception;
}
