/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemskeOperacije;

import dbb.DatabaseBroker;
import domen.Clanarina;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class EvidentirajClanarinuSO extends OpstaSistemskaOperacija {

    @Override
    public ServerskiOdgovor izvrsiOperaciju(OpstiDomenskiObjekat odo) throws Exception {
        ServerskiOdgovor so = new ServerskiOdgovor();

        try {
            Clanarina evidentiranaClanarina = (Clanarina) odo;
//            Clanarina clanarina = DatabaseBroker.getInstanca().vratiClanarinu(evidentiranaClanarina.getClan());
            ArrayList<OpstiDomenskiObjekat> cl = (ArrayList<OpstiDomenskiObjekat>) odo.ucitajListu(DatabaseBroker.getInstanca().select(odo));
            if (cl.size() != 1) {
                int id = DatabaseBroker.getInstanca().vratiID(odo);
                id++;
                evidentiranaClanarina.setClanarinaID(id);
                DatabaseBroker.getInstanca().insert(evidentiranaClanarina);
//                DatabaseBroker.getInstanca().evidentirajClanarinu(clanarina, id);
                so.setOdgovor(true);
                so.setPoruka("Uspesno evidentirana clanarina");
            } else {
                DatabaseBroker.getInstanca().update(odo);
                so.setOdgovor(true);
                so.setPoruka("Uspesno azurirana clanarina");
            }
        } catch (Exception ex) {
            Logger.getLogger(UlogujRadnikaSO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno izmenjeni podaci o clanu");
        }
        return so;
    }

    @Override
    public void proveriPreduslov(OpstiDomenskiObjekat odo) throws Exception {

    }

}
