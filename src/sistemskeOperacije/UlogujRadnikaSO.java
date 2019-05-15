/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.OpstiDomenskiObjekat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class UlogujRadnikaSO extends OpstaSistemskaOperacija {

    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            ArrayList<OpstiDomenskiObjekat> lista = (ArrayList<OpstiDomenskiObjekat>) odo.ucitajListu(DatabaseBroker.getInstanca().select(odo));
            if (lista.isEmpty() || lista.size() > 1) {
                so.setOdgovor(null);
                so.setPoruka("Neuspesno prijavljivanje na sistem");
            } else {
                so.setOdgovor(lista.get(0));
                so.setPoruka("Uspesno prijavljivanje na sistem");
            }
        } catch (Exception ex) {
            Logger.getLogger(UlogujRadnikaSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno prijavljivanje na sistem");
        }

        return so;
    }

    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
    }


}
