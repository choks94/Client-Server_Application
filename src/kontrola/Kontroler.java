/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontrola;

import dbb.DatabaseBroker;
import domen.Clan;
import domen.Clanarina;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import domen.Primerak;
import domen.Radnik;
import domen.Zaduzenje;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sistemskeOperacije.EvidentirajClanarinuSO;
import sistemskeOperacije.IzmeniClanaSO;
import sistemskeOperacije.IzmeniKnjiguSO;
import sistemskeOperacije.ObrisiClanaSO;
import sistemskeOperacije.ObrisiKnjiguSO;
import sistemskeOperacije.OpstaSistemskaOperacija;
import sistemskeOperacije.RazduziZaduzenjeSO;
import sistemskeOperacije.SacuvajNovoZaduzenjeSO;
import sistemskeOperacije.SacuvajNovogClanaSO;
import sistemskeOperacije.SacuvajNovuKnjiguSO;
import sistemskeOperacije.UlogujRadnikaSO;
import sistemskeOperacije.VratiClanarinuSO;
import sistemskeOperacije.VratiClanoveSO;
import sistemskeOperacije.VratiClanoveSaVazecomClanarinomSO;
import sistemskeOperacije.VratiID;
import sistemskeOperacije.VratiKnjigeSO;
import sistemskeOperacije.VratiPretrazeneClanoveSO;
import sistemskeOperacije.VratiPretrazeneKnjigeSO;
import sistemskeOperacije.VratiPrimerkeSO;
import sistemskeOperacije.VratiZaduzenjaSO;
import transfer.ServerskiOdgovor;

/**
 *
 * @author STEFAN94
 */
public class Kontroler {

    public static Kontroler getInstanca() {
        if (instanca == null) {
            instanca = new Kontroler();
        }
        return instanca;
    }
    private Socket s;

    DatabaseBroker databaseBroker = new DatabaseBroker();

    private Kontroler() {

    }

    private static Kontroler instanca;

    public ServerskiOdgovor ulogijRadnika(OpstiDomenskiObjekat odo) {
        //        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        Radnik ulogovaniRadnik;
//        try {
//            ulogovaniRadnik = databaseBroker.ulogujRadnika(radnikZaLogovanje);
//        } catch (Exception ex) {
//            ulogovaniRadnik = null;
//        }
//        return ulogovaniRadnik;
        return new UlogujRadnikaSO().izvrsiTransakciju(odo);

    }

    public ServerskiOdgovor vratiClanove(OpstiDomenskiObjekat odo) {

        //        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Clan> listaClanova = databaseBroker.vratiClanove();
//        databaseBroker.zatvoriKonekciju();
//        return listaClanova;
        return new VratiClanoveSO().izvrsiTransakciju(odo);
    }

    public ServerskiOdgovor vratiKnjige(OpstiDomenskiObjekat odo) {

        //        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Knjiga> listaKnjiga = databaseBroker.vratiKnjige();
//        for (Knjiga knjiga : listaKnjiga) {
//            ArrayList<Primerak> listaPrimeraka = databaseBroker.vratiPrimerke(knjiga);
//            knjiga.setListaPrimeraka(listaPrimeraka);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return listaKnjiga;
        return new VratiKnjigeSO().izvrsiTransakciju(odo);

    }

//    public ArrayList<Clan> vratiClanovePoPretrazi(String pretraga) {
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Clan> listaPretrazenihClanova = databaseBroker.vratiPretrazeneClanove(pretraga);
//        databaseBroker.zatvoriKonekciju();
//        return listaPretrazenihClanova;
//    }
    //Treba izmeniti!
    public ServerskiOdgovor vratiKnjigePoPretrazi(OpstiDomenskiObjekat odo) {
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Knjiga> listaPretrazenihKnjiga = databaseBroker.vratipretrazeneKnjige(pretragaKnjiga);
//        databaseBroker.zatvoriKonekciju();
//        return listaPretrazenihKnjiga;
        return new VratiPretrazeneKnjigeSO().izvrsiTransakciju(odo);
    }
    
    public ServerskiOdgovor vratiPretrazeneClanove(OpstiDomenskiObjekat odo){
        return new VratiPretrazeneClanoveSO().izvrsiTransakciju(odo);
    }

    public ServerskiOdgovor sacuvajNovogClana(OpstiDomenskiObjekat odo) {
        //        ServerskiOdgovor serverskiOdgovor = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        int id = databaseBroker.vratiSledeciID();
//        try {
//            databaseBroker.sacuvajNovogClana(noviClan, id);
//            serverskiOdgovor.setOdgovor(true);
//            serverskiOdgovor.setPoruka("Uspesno sacuvano");
//            databaseBroker.commit();
//        } catch (SQLException ex) {
//            serverskiOdgovor.setOdgovor(false);
//            serverskiOdgovor.setPoruka("Neuspesno sacuvano");
//            databaseBroker.rollback();
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return serverskiOdgovor;
        return new SacuvajNovogClanaSO().izvrsiTransakciju(odo);

    }

    public ServerskiOdgovor obrisiClana(OpstiDomenskiObjekat odo) {
//        ServerskiOdgovor so = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Knjiga> listaZaduzenihKnjiga = new ArrayList<>();
//        listaZaduzenihKnjiga = databaseBroker.vratiZaduzenjaClana(clanZaBrisanje);
//        if (listaZaduzenihKnjiga.size() > 0) {
//            String naziviKnjiga = "";
//            for (Knjiga knjiga : listaZaduzenihKnjiga) {
//                String naziv = "\n " + knjiga.getNaziv();
//                naziviKnjiga += naziv;
//            }
//            so.setOdgovor(false);
//            so.setPoruka((listaZaduzenihKnjiga.size() == 1) ? "Korisnik nije razduzio knjigu:" + naziviKnjiga : "Korisnik nije razduzio knjige:" + naziviKnjiga);
//            return so;
//        }
//        try {
//            databaseBroker.obrisiClana(clanZaBrisanje);
//            databaseBroker.obrisiClanarinuzaClana(clanZaBrisanje);
//            databaseBroker.commit();
//            so.setOdgovor(true);
//            so.setPoruka("Uspesno obrisan clan");
//        } catch (SQLException ex) {
//            databaseBroker.rollback();
//            so.setOdgovor(false);
//            so.setPoruka("Neuspesno obrisan clan");
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return so;
        return new ObrisiClanaSO().izvrsiTransakciju(odo);
    }

    public ServerskiOdgovor izmeniClana(OpstiDomenskiObjekat odo) {

        //        ServerskiOdgovor so = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        try {
//            databaseBroker.izmeniClana(clanZaIzmenu);
//            databaseBroker.commit();
//            so.setOdgovor(true);
//            so.setPoruka("Uspesno izmenjeni podaci");
//        } catch (SQLException ex) {
//            databaseBroker.rollback();
//            so.setOdgovor(false);
//            so.setPoruka("Neuspesno izmenjeni podaci");
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return so;
        return new IzmeniClanaSO().izvrsiTransakciju(odo);

    }

    public ServerskiOdgovor obrisiKnjigu(OpstiDomenskiObjekat odo) {
//        ServerskiOdgovor so = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Clan> listaClanovaZaKnjigu = databaseBroker.vratiClanoveZaKnjigu(knjigaZaBrisanje);
//        String clanovi = "";
//        if (listaClanovaZaKnjigu.size() > 0) {
//            for (Clan clan : listaClanovaZaKnjigu) {
//                String cl = "\n " + clan.getIme() + " " + clan.getPrezime() + " JMBG: " + clan.getJmbg() + "";
//                clanovi += cl;
//            }
//            so.setOdgovor(false);
//            String info = (listaClanovaZaKnjigu.size() == 1) ? "Knjiga je zaduzena kod clana:" + clanovi : "Knjiga je zaduzena kod clanova:" + clanovi;
//            so.setPoruka(info);
//            return so;
//        }
//        ArrayList<Primerak> listaPrimerakaZaKnjigu = databaseBroker.vratiPrimerke(knjigaZaBrisanje);
//        int brojac = 0;
//        for (Primerak primerak : listaPrimerakaZaKnjigu) {
//            if (primerak.isZaduzen()) {
//                brojac++;
//            }
//        }
//        if (brojac > 0) {
//            so.setOdgovor(false);
//            String info2 = "Nisu razduzeni svi primerci date knjige";
//            so.setPoruka(info2);
//            return so;
//        }
//        try {
//            databaseBroker.obrisiKnjigu(knjigaZaBrisanje);
//            databaseBroker.obrisiSvePrimerkeZaDatuKnjigu(knjigaZaBrisanje);
//            databaseBroker.commit();
//            so.setOdgovor(true);
//            so.setPoruka("Uspesno obrisana knjiga");
//        } catch (SQLException ex) {
//            databaseBroker.rollback();
//            so.setOdgovor(false);
//            so.setPoruka("Neuspesno obrisana knjiga");
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return so;
        return new ObrisiKnjiguSO().izvrsiTransakciju(odo);
    }

//6. Treba izmeniti!
    public ServerskiOdgovor izmeniKnjigu(OpstiDomenskiObjekat odo) {
//        ServerskiOdgovor so = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//
//        try {
//            databaseBroker.izmeniKnjigu(knjigaZaIzmenu);
//            if (knjigaZaIzmenu.getListaPrimeraka().size() > 0) {
//                for (Primerak p : knjigaZaIzmenu.getListaPrimeraka()) {
//                    databaseBroker.rashodujPrimerak(p);
//                }
//            }
//            int brNovihPrimeraka = knjigaZaIzmenu.getBrojPrimeraka();
//            if (brNovihPrimeraka > 0) {
//                int sledeciID = databaseBroker.vratiMAksIDPRimerka(knjigaZaIzmenu);
//
//                for (int i = sledeciID; i < sledeciID + brNovihPrimeraka; i++) {
//                    databaseBroker.unesiPrimerkeKnjige(i, knjigaZaIzmenu.getIsbn());
//                }
//            }
//
//            databaseBroker.commit();
//            so.setOdgovor(true);
//            so.setPoruka("Uspesno izmenjeno");
//        } catch (SQLException ex) {
//            databaseBroker.rollback();
//            so.setOdgovor(false);
//            so.setPoruka("Neuspesno izmenjeno");
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return so;
        return new IzmeniKnjiguSO().izvrsiTransakciju(odo);
    }
//5. Treba izmeniti!

    public ServerskiOdgovor unesiNovuKnjigu(OpstiDomenskiObjekat odo) {
//        ServerskiOdgovor so = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        try {
//            databaseBroker.unesiNovuKnjigu(novaKnjiga);
//            for (int i = 1; i <= novaKnjiga.getBrojPrimeraka(); i++) {
//                databaseBroker.unesiPrimerkeKnjige(i, novaKnjiga.getIsbn());
//            }
//            so.setOdgovor(true);
//            so.setPoruka("Uspesno sacuvano");
//            databaseBroker.commit();
//        } catch (SQLException ex) {
//            so.setOdgovor(false);
//            so.setPoruka("Neuspesno sacuvano");
//            databaseBroker.rollback();
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return so;
        return new SacuvajNovuKnjiguSO().izvrsiTransakciju(odo);
    }

    public ServerskiOdgovor vratiClanarinu(OpstiDomenskiObjekat odo) {

//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        Clanarina clanarina = databaseBroker.vratiClanarinu(clan);
//        databaseBroker.zatvoriKonekciju();
//        return clanarina;
        return new VratiClanarinuSO().izvrsiTransakciju(odo);

    }

    public ServerskiOdgovor evidentirajClanarinu(OpstiDomenskiObjekat odo) {
//        ServerskiOdgovor serverskiOdgovor = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//
//        Clanarina clanarina = databaseBroker.vratiClanarinu(evidentiranaClanarina.getClan());
//        if (clanarina == null) {
//            try {
//                int clanarinaID = databaseBroker.vratiSledeciIDClanarine();
//                databaseBroker.evidentirajClanarinu(evidentiranaClanarina, clanarinaID);
//                serverskiOdgovor.setOdgovor(true);
//                serverskiOdgovor.setPoruka("Uspesno sacuvano");
//                databaseBroker.commit();
//            } catch (SQLException ex) {
//                serverskiOdgovor.setOdgovor(false);
//                serverskiOdgovor.setPoruka("Neuspesno sacuvano");
//                databaseBroker.rollback();
//                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            try {
//                databaseBroker.azurirajClanarinu(evidentiranaClanarina);
//                serverskiOdgovor.setOdgovor(true);
//                serverskiOdgovor.setPoruka("Uspesno azurirano");
//                databaseBroker.commit();
//            } catch (SQLException ex) {
//                serverskiOdgovor.setOdgovor(false);
//                serverskiOdgovor.setPoruka("Neuspesno azurirano");
//                databaseBroker.rollback();
//                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        databaseBroker.zatvoriKonekciju();
//        return serverskiOdgovor;
        return new EvidentirajClanarinuSO().izvrsiTransakciju(odo);
    }

    public ServerskiOdgovor vratiZaduzenja(OpstiDomenskiObjekat odo) {

        //        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Zaduzenje> listaZaduzenja = databaseBroker.vratizaduzenja(knjigaZaZaduzenje);
//        databaseBroker.zatvoriKonekciju();
//        return listaZaduzenja;
        return new VratiZaduzenjaSO().izvrsiTransakciju(odo);

    }

    public ServerskiOdgovor razduzi(OpstiDomenskiObjekat odo) {
//        ServerskiOdgovor so = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        try {
//            databaseBroker.razduzi(zaduzenje);
//            databaseBroker.razduziPrimerak(zaduzenje);
//            so.setOdgovor(true);
//            so.setPoruka("Uspesno razduzeno");
//            databaseBroker.commit();
//        } catch (SQLException ex) {
//            so.setOdgovor(false);
//            so.setPoruka("Neuspesno razduzeno");
//            databaseBroker.rollback();
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return so;
        return new RazduziZaduzenjeSO().izvrsiTransakciju(odo);
    }

    //2. Treba obrisati!
    public ServerskiOdgovor vratiClanoveSaClanarinom(OpstiDomenskiObjekat odo) {
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Clan> listaClanova = databaseBroker.vratiVazeceClanove();
//        databaseBroker.zatvoriKonekciju();
//        return listaClanova;
        return new VratiClanoveSaVazecomClanarinomSO().izvrsiTransakciju(odo);
    }

    public ServerskiOdgovor vratiPrimerke(OpstiDomenskiObjekat odo) {

//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Primerak> listaPrimeraka = databaseBroker.vratiPrimerke(k);
//        databaseBroker.zatvoriKonekciju();
//        return listaPrimeraka;
        return new VratiPrimerkeSO().izvrsiTransakciju(odo);

    }

    //1. Treba izmeniti!
    public ServerskiOdgovor sacuvajNovoZaduzenje(OpstiDomenskiObjekat odo) {
//        ServerskiOdgovor so = new ServerskiOdgovor();
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<String> zaduzenjaDatogClana = databaseBroker.proveriZaduzenjeClana(novoZaduzenje);
//        for (String isbn : zaduzenjaDatogClana) {
//            if (isbn.equals(novoZaduzenje.getPrimerak().getIsbn())) {
//                so.setOdgovor(false);
//                so.setPoruka("Dati clan ima vec iznajmljenu datu knjigu");
//                return so;
//            }
//        }
//        try {
//            databaseBroker.sacuvajNovoZaduzenje(novoZaduzenje);
//            databaseBroker.zaduziPrimerak(novoZaduzenje);
//            databaseBroker.commit();
//            so.setOdgovor(true);
//            so.setPoruka("Uspesno zabelezeno zaduzenje");
//        } catch (SQLException ex) {
//            databaseBroker.rollback();
//            so.setOdgovor(false);
//            so.setPoruka("Neuspesno zabelezeno zaduzenje");
//            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        databaseBroker.zatvoriKonekciju();
//        return so;
        return new SacuvajNovoZaduzenjeSO().izvrsiTransakciju(odo);
    }
//Nije potrebno vise!
//    public ArrayList<Zaduzenje> vratiZaduzenjaClana(Clan clanZaZaduzenja) {
//        databaseBroker.ucitajDrajver();
//        databaseBroker.otvoriKonekciju();
//        ArrayList<Zaduzenje> listaZaduzenja = databaseBroker.vratiSvaZaduzenjaClana(clanZaZaduzenja);
//        databaseBroker.zatvoriKonekciju();
//        return listaZaduzenja;
//    }

    public ServerskiOdgovor vratiID(OpstiDomenskiObjekat odo) {
        return new VratiID().izvrsiTransakciju(odo);
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

}
