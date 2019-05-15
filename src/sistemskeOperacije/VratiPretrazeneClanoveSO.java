/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class VratiPretrazeneClanoveSO extends OpstaSistemskaOperacija {

    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            ArrayList<OpstiDomenskiObjekat> listaClanova = (ArrayList<OpstiDomenskiObjekat>) odo.ucitajListu(DatabaseBroker.getInstanca().select(odo));
            so.setOdgovor(listaClanova);
            so.setPoruka("Uspesno vraceni clanovi");
        } catch (Exception ex) {
            Logger.getLogger(VratiClanoveSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno vraceni clanovi");
        }

        return so;

    }

    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {
        
    }

}
