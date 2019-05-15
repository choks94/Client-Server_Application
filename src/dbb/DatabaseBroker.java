/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbb;

import domen.Clan;
import domen.Clanarina;
import domen.Knjiga;
import domen.OpstiDomenskiObjekat;
import domen.Primerak;
import domen.Radnik;
import domen.Zaduzenje;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author STEFAN94
 */
public class DatabaseBroker {

    private static DatabaseBroker instanca;
    Connection konekcija;
    Scanner skener;

    public static DatabaseBroker getInstanca() {
        if (instanca == null) {
            instanca = new DatabaseBroker();
        }
        return instanca;
    }

    public void ucitajDrajver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void otvoriKonekciju() {
        String host = "";
        String port = "";
        String database = "";
        String user = "";
        String pass = "";
        try {
            skener = new Scanner(new File("parametri.txt"));

            String red = "";
            String[] podaci = new String[2];
            while (skener.hasNext()) {
                red = skener.nextLine();
                podaci = red.split(":");
                switch (podaci[0].toLowerCase()) {
                    case "host":
                        host = podaci[1].trim();
                        break;
                    case "port":
                        port = podaci[1].trim();
                        break;
                    case "username":
                        user = podaci[1].trim();
                        break;
                    case "database":
                        database = podaci[1].trim();
                        break;
                    case "password":
                        pass = podaci[1].trim();
                        break;

                }
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Neispravni parametri za konekciju");
            return;

        }
        System.out.println(host + " " + port + " " + database + " " + user + " " + pass);
        String sql = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            konekcija = DriverManager.getConnection(sql, user, pass);
            konekcija.setAutoCommit(false);
            System.out.println("Konektovan");
        } catch (SQLException ex) {
            System.out.println("Neuspesna konekcija sa bazom");
        }
    }

    public void zatvoriKonekciju() {
        try {
            konekcija.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void commit() {
        try {
            konekcija.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            konekcija.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public Radnik ulogujRadnika(Radnik radnikZaLogovanje) throws Exception {
//        Radnik ulogovaniRadnik = null;
//        String lozinkaEncriptovana = sha1(radnikZaLogovanje.getLozinka());
//        System.out.println("" + lozinkaEncriptovana);
//        String sql = "SELECT * FROM `radnik` WHERE `KorisnickoIme` = '" + radnikZaLogovanje.getKorisnickoIme() + "' AND `Lozinka` = '" + lozinkaEncriptovana + "'";
//
//        try {
//            Statement statement = konekcija.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            if (resultSet.next()) {
//                int radnikID = resultSet.getInt("RadnikID");
//                String ime = resultSet.getString("Ime");
//                String prezime = resultSet.getString("Prezime");
//                String korisnickoIme = resultSet.getString("KorisnickoIme");
//                String lozinka = resultSet.getString("Lozinka");
//
//                ulogovaniRadnik = new Radnik(radnikID, ime, prezime, korisnickoIme, lozinka);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return ulogovaniRadnik;
//    }
//
//    static String sha1(String input) throws NoSuchAlgorithmException {
//        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
//        byte[] result = mDigest.digest(input.getBytes());
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < result.length; i++) {
//            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
//        }
//
//        return sb.toString();
//    }
//    public ArrayList<Clan> vratiClanove() {
//        ArrayList<Clan> listaclanova = new ArrayList<>();
//        String sql = "SELECT * FROM `clan`";
//
//        try {
//            Statement statement = konekcija.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                int clanID = resultSet.getInt("ClanID");
//                String ime = resultSet.getString("Ime");
//                String prezime = resultSet.getString("Prezime");
//                java.util.Date datumRodjenja = new Date(resultSet.getDate("DatumRodjenja").getTime());
//                long jmbg = resultSet.getLong("JMBG");
//                String kontaktTelefon = resultSet.getString("KontaktTelefon");
//                String email = resultSet.getString("Email");
//
//                Clan clan = new Clan(clanID, jmbg, ime, prezime, datumRodjenja, kontaktTelefon, email);
//                listaclanova.add(clan);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return listaclanova;
//    }
//    public ArrayList<Knjiga> vratiKnjige() {
//        ArrayList<Knjiga> listaKnjiga = new ArrayList<>();
//
//        String sql = "SELECT * FROM `knjiga`";
//        try {
//            Statement statement = konekcija.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                String isbn = resultSet.getString("ISBN");
//                String naziv = resultSet.getString("Naziv");
//                String pisac = resultSet.getString("Pisac");
//                String godinaIzdanja = resultSet.getString("GodinaIzdanja");
//                String pismo = resultSet.getString("Pismo");
//                int brojStrana = resultSet.getInt("BrojStrana");
//                String zanr = resultSet.getString("Zanr");
//                String povez = resultSet.getString("Povez");
//                byte[] slika = resultSet.getBytes("Slika");
//
//                Knjiga knjiga = new Knjiga(isbn, naziv, pisac, godinaIzdanja, pismo, brojStrana, zanr, povez, slika, null);
//                listaKnjiga.add(knjiga);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return listaKnjiga;
//    }
//
//    public ArrayList<Clan> vratiPretrazeneClanove(String pretraga) {
//        ArrayList<Clan> listaclanova = new ArrayList<>();
//        String sql = "SELECT * FROM `clan` WHERE ime LIKE '" + pretraga + "%' OR CONCAT(`Ime`,' ',`Prezime`) LIKE '" + pretraga + "%' OR CONCAT(`Prezime`,' ',`Ime`) LIKE '" + pretraga + "%' OR prezime LIKE '" + pretraga + "%' OR clanID LIKE '" + pretraga + "%' OR datumrodjenja LIKE '%" + pretraga + "%'";
//
//        try {
//            Statement statement = konekcija.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                int clanID = resultSet.getInt("ClanID");
//                String ime = resultSet.getString("Ime");
//                String prezime = resultSet.getString("Prezime");
//                java.util.Date datumRodjenja = new Date(resultSet.getDate("DatumRodjenja").getTime());
//                long jmbg = resultSet.getLong("JMBG");
//                String kontaktTelefon = resultSet.getString("KontaktTelefon");
//                String email = resultSet.getString("Email");
//
//                Clan clan = new Clan(clanID, jmbg, ime, prezime, datumRodjenja, kontaktTelefon, email);
//                listaclanova.add(clan);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return listaclanova;
//
//    }
    
    //!
//    public ArrayList<Knjiga> vratipretrazeneKnjige(Knjiga pretragaKnjiga) {
//        ArrayList<Knjiga> listaKnjiga = new ArrayList<>();
//        String sql = "";
//        if (pretragaKnjiga.getZanr().equals("Svi zanrovi")) {
//            sql = "SELECT * FROM `knjiga` WHERE isbn LIKE '" + pretragaKnjiga.getNaziv() + "%' OR naziv LIKE '" + pretragaKnjiga.getNaziv() + "%' OR pisac LIKE '" + pretragaKnjiga.getNaziv() + "%'";
//        } else {
//            sql = "SELECT * FROM `knjiga` WHERE (isbn LIKE '" + pretragaKnjiga.getNaziv() + "%' OR naziv LIKE '" + pretragaKnjiga.getNaziv() + "%' OR pisac LIKE '" + pretragaKnjiga.getNaziv() + "%') AND Zanr = '" + pretragaKnjiga.getZanr() + "'";
//        }
//        try {
//            Statement statement = konekcija.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                String isbn = resultSet.getString("ISBN");
//                String naziv = resultSet.getString("Naziv");
//                String pisac = resultSet.getString("Pisac");
//                String godinaIzdanja = resultSet.getString("GodinaIzdanja");
//                String pismo = resultSet.getString("Pismo");
//                int brojStrana = resultSet.getInt("BrojStrana");
//                String zanr = resultSet.getString("Zanr");
//                String povez = resultSet.getString("Povez");
//                byte[] slika = resultSet.getBytes("Slika");
//
//                Knjiga knjiga = new Knjiga(isbn, naziv, pisac, godinaIzdanja, pismo, brojStrana, zanr, povez, slika, null);
//                listaKnjiga.add(knjiga);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return listaKnjiga;
//    }

//    public int vratiSledeciID() {
//        int id = 0;
//        String sql = "SELECT MAX(ClanID) as id FROM `clan`";
//
//        try {
//            Statement statement = konekcija.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            if (resultSet.next()) {
//                id = resultSet.getInt("id");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return ++id;
//    }
//
//    public void sacuvajNovogClana(Clan noviClan, int id) throws SQLException {
//        String sql = "INSERT INTO `clan`(`ClanID`,`Ime`, `Prezime`, `DatumRodjenja`, `JMBG`, `KontaktTelefon`, `Email`) VALUES(?, ?, ?, ?, ?, ?, ?)";
//
//        PreparedStatement preparedStatement = konekcija.prepareStatement(sql);
//
//        preparedStatement.setInt(1, id);
//        preparedStatement.setString(2, noviClan.getIme());
//        preparedStatement.setString(3, noviClan.getPrezime());
//        preparedStatement.setDate(4, new java.sql.Date(noviClan.getDatumRodjenja().getTime()));
//        preparedStatement.setLong(5, noviClan.getJmbg());
//        preparedStatement.setString(6, noviClan.getKontaktTelefon());
//        preparedStatement.setString(7, noviClan.getEmail());
//
//        preparedStatement.executeUpdate();
//
//    }

//    public void obrisiClana(Clan clanZaBrisanje) throws SQLException {
//        String sql = "DELETE FROM `clan` WHERE `ClanID` = ?";
//        PreparedStatement preparedStatement = konekcija.prepareStatement(sql);
//        preparedStatement.setInt(1, clanZaBrisanje.getClanID());
//
//        preparedStatement.executeUpdate();
//    }

//    public void izmeniClana(Clan clanZaIzmenu) throws SQLException {
//        String sql = "UPDATE clan SET `Email` = ?, `Ime` = ?, `Prezime` = ?, `DatumRodjenja` = ?, `KontaktTelefon` = ?, `JMBG` = ? WHERE `ClanID` = ?";
//        PreparedStatement preparedStatement = konekcija.prepareStatement(sql);
//        preparedStatement.setString(1, clanZaIzmenu.getEmail());
//        preparedStatement.setString(2, clanZaIzmenu.getIme());
//        preparedStatement.setString(3, clanZaIzmenu.getPrezime());
//        preparedStatement.setDate(4, new java.sql.Date(clanZaIzmenu.getDatumRodjenja().getTime()));
//        preparedStatement.setString(5, clanZaIzmenu.getKontaktTelefon());
//        preparedStatement.setLong(6, clanZaIzmenu.getJmbg());
//        preparedStatement.setInt(7, clanZaIzmenu.getClanID());
//
//        preparedStatement.executeUpdate();
//    }

//    public void obrisiKnjigu(Knjiga knjigaZaBrisanje) throws SQLException {
//        String sql = "DELETE FROM `knjiga` WHERE `ISBN` = ?";
//        PreparedStatement preparedStatement = konekcija.prepareStatement(sql);
//        preparedStatement.setString(1, knjigaZaBrisanje.getIsbn());
//
//        preparedStatement.executeUpdate();
//    }
//
    public void izmeniKnjigu(OpstiDomenskiObjekat odo) throws SQLException {
        Knjiga knjigaZaIzmenu = (Knjiga)odo;
        String sql = "UPDATE knjiga SET Naziv = ?, Pisac = ?, GodinaIzdanja = ?, Pismo = ?, BrojStrana = ?, Zanr = ?, Povez = ?, Slika =? WHERE ISBN = ?";
        PreparedStatement ps = konekcija.prepareCall(sql);
        ps.setString(1, knjigaZaIzmenu.getNaziv());
        ps.setString(2, knjigaZaIzmenu.getPisac());
        ps.setString(3, knjigaZaIzmenu.getGodinaIzdanja());
        ps.setString(4, knjigaZaIzmenu.getPismo());
        ps.setInt(5, knjigaZaIzmenu.getBrojStrana());
        ps.setString(6, knjigaZaIzmenu.getZanr());
        ps.setString(7, knjigaZaIzmenu.getPovez());
        ps.setBytes(8, knjigaZaIzmenu.getSlika());
        ps.setString(9, knjigaZaIzmenu.getIsbn());

        ps.executeUpdate();
    }
//
    public void unesiNovuKnjigu(OpstiDomenskiObjekat odo) throws SQLException {
        Knjiga novaKnjiga = (Knjiga)odo;
        String sql = "INSERT INTO knjiga(`Naziv`, `Pisac`, `GodinaIzdanja`, `Pismo`,`BrojStrana`, `Zanr`,`Povez`, `Slika`, `ISBN`) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = konekcija.prepareCall(sql);
        ps.setString(1, novaKnjiga.getNaziv());
        ps.setString(2, novaKnjiga.getPisac());
        ps.setString(3, novaKnjiga.getGodinaIzdanja());
        ps.setString(4, novaKnjiga.getPismo());
        ps.setInt(5, novaKnjiga.getBrojStrana());
        ps.setString(6, novaKnjiga.getZanr());
        ps.setString(7, novaKnjiga.getPovez());
        ps.setBytes(8, novaKnjiga.getSlika());
        ps.setString(9, novaKnjiga.getIsbn());

        ps.executeUpdate();
    }

//    private static String decrypt(String strEncrypted, String strKey) throws Exception {
//        String strData = "";
//
//        try {
//            SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
//            Cipher cipher = Cipher.getInstance("Blowfish");
//            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
//            byte[] decrypted = cipher.doFinal(strEncrypted.getBytes());
//            strData = new String(decrypted);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception(e);
//        }
//        return strData;
//    }
//    public Clanarina vratiClanarinu(Clan clan) {
//        Clanarina clanarina = null;
//        String sql = "SELECT c.`CenaClanarine`, c.`ClanarinaID`, c.`DatumDO`, c.`DatumOd`, r.`Ime`,r.`KorisnickoIme`, r.`Lozinka`, r.`Prezime`, r.`RadnikID`, cl.`ClanID`, cl.`DatumRodjenja`,cl.`Email`, cl.`Ime`, cl.`JMBG`, cl.`KontaktTelefon`, cl.`Prezime`  FROM `clanarina` c JOIN `radnik` r ON c.`RadnikID` = r.`RadnikID`  JOIN `clan` cl ON c.`ClanID` = cl.`ClanID`   WHERE cl.`ClanID` = " + clan.getClanID() + "";
//        try {
//            Statement s = konekcija.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            if (rs.next()) {
//                int clanarinaID = rs.getInt("ClanarinaID");
//                int clanID = rs.getInt("cl.ClanID");
//                String imeC = rs.getString("cl.Ime");
//                String prezimeC = rs.getString("cl.Prezime");
//                java.util.Date datumRodjenja = (new java.util.Date(rs.getDate("cl.DatumRodjenja").getTime()));
//                long jmbg = rs.getLong("cl.JMBG");
//                String kontaktTel = rs.getString("cl.KontaktTelefon");
//                String email = rs.getString("cl.Email");
//                Clan c = new Clan(clanID, jmbg, imeC, prezimeC, datumRodjenja, kontaktTel, email);
//
//                java.util.Date datumOd = (new java.util.Date(rs.getDate("c.DatumOd").getTime()));
//                java.util.Date datumDo = (new java.util.Date(rs.getDate("c.DatumDo").getTime()));
//                double cena = rs.getDouble("c.CenaClanarine");
//                int radnikID = rs.getInt("r.RadnikID");
//                String imeR = rs.getString("r.Ime");
//                String prezimeR = rs.getString("r.Prezime");
//                String korisnickoIme = rs.getString("r.KorisnickoIme");
//                String lozinka = rs.getString("r.Lozinka");
//                Radnik radnik = new Radnik(radnikID, imeR, prezimeR, korisnickoIme, lozinka);
//
//                clanarina = new Clanarina(clanarinaID, datumOd, datumDo, cena, radnik, c);
//
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return clanarina;
//    }

//!!!!!
//    public void evidentirajClanarinu(Clanarina evidentiranaClanarina, int clanarinaID) throws SQLException {
//        String sql = "INSERT INTO `clanarina`(`ClanarinaID`, `ClanID`, `DatumOd`, `DatumDO`, `CenaClanarine`, `RadnikID`) VALUES(?, ?, ?, ?, ?, ?) ";
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//
//        ps.setInt(1, clanarinaID);
//        ps.setInt(2, evidentiranaClanarina.getClan().getClanID());
//        ps.setDate(3, new java.sql.Date(evidentiranaClanarina.getDatumOd().getTime()));
//        ps.setDate(4, new java.sql.Date(evidentiranaClanarina.getDatumDo().getTime()));
//        ps.setDouble(5, evidentiranaClanarina.getCenaClanarine());
//        ps.setInt(6, evidentiranaClanarina.getRadnik().getRadnikID());
//
//        ps.executeUpdate();
//
//    }
//!!!!
//    public int vratiSledeciIDClanarine() {
//        int id = 0;
//        String sql = "SELECT MAX(ClanarinaID) as id FROM `clanarina`";
//        try {
//            Statement s = konekcija.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            if (rs.next()) {
//                id = rs.getInt("id");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ++id;
//    }

//!
//    public Clanarina vratiClanarinu(Clan clan) {
//        Clanarina clanarina = null;
//        String sql = "SELECT * FROM `clanarina` WHERE `ClanID` = " + clan.getClanID();
//        try {
//            Statement s = konekcija.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            if (rs.next()) {
//
//                int clanarinaID = rs.getInt("ClanarinaId");
//                java.util.Date datumOd = new java.util.Date(rs.getDate("DatumOd").getTime());
//                java.util.Date datumDo = new java.util.Date(rs.getDate("DatumDo").getTime());
//                double cena = rs.getDouble("CenaClanarine");
//
//                clanarina = new Clanarina(clanarinaID, datumOd, datumDo, cena, null, clan);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return clanarina;
//    }

//    public void azurirajClanarinu(Clanarina evidentiranaClanarina) throws SQLException {
//        String sql = "UPDATE `clanarina` SET `DatumOd` = ?, DatumDO = ?, `CenaClanarine` = ?, `RadnikID` = ? WHERE `ClanID` = ? ";
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//
//        ps.setDate(1, new java.sql.Date(evidentiranaClanarina.getDatumOd().getTime()));
//        ps.setDate(2, new java.sql.Date(evidentiranaClanarina.getDatumDo().getTime()));
//        ps.setDouble(3, evidentiranaClanarina.getCenaClanarine());
//        ps.setInt(4, evidentiranaClanarina.getRadnik().getRadnikID());
//        ps.setInt(5, evidentiranaClanarina.getClan().getClanID());
//
//        ps.executeUpdate();
//    }

//!!!    
    public ArrayList<Primerak> vratiPrimerke(OpstiDomenskiObjekat odo) {
        Knjiga knjiga = (Knjiga)odo;
        ArrayList<Primerak> listaPrimeraka = new ArrayList<>();
        String sql = "SELECT * FROM `primerak` WHERE `ISBN` = '" + knjiga.getIsbn() + "'";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                int idP = rs.getInt("PrimerakID");
                String isbn = rs.getString("ISBN");
                boolean zaduzen = rs.getBoolean("Zaduzen");
                boolean rashodovan = rs.getBoolean("Rashodovan");
                Primerak p = new Primerak(idP, isbn, zaduzen, rashodovan);
                listaPrimeraka.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPrimeraka;
    }

//    public ArrayList<Zaduzenje> vratizaduzenja(Knjiga knjigaZaZaduzenje) {
//        ArrayList<Zaduzenje> listaZaduzenja = new ArrayList<>();
//        String sql = "SELECT * FROM `zaduzenje` z JOIN clan c ON z.`ClanID` = c.`ClanID` LEFT JOIN `primerak` p ON z.`PrimerakID` = p.`PrimerakID` WHERE z.ISBN = '" + knjigaZaZaduzenje.getIsbn() + "' GROUP BY z.`ZaduzenjeID` ORDER BY z.DatumZaduzenja";
//        try {
//            Statement s = konekcija.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            while (rs.next()) {
//                int clanID = rs.getInt("c.ClanID");
//                String ime = rs.getString("c.Ime");
//                String prezime = rs.getString("c.Prezime");
//                java.util.Date datumRodjenja = new Date(rs.getDate("c.DatumRodjenja").getTime());
//                long jmbg = rs.getLong("c.JMBG");
//                String kontaktTelefon = rs.getString("c.KontaktTelefon");
//                String email = rs.getString("c.Email");
//
//                Clan c = new Clan(clanID, jmbg, ime, prezime, datumRodjenja, kontaktTelefon, email);
//
//                int primerakID = rs.getInt("z.PrimerakID");
//                String isbn = rs.getString("z.ISBN");
//                boolean zaduzen = rs.getBoolean("p.Zaduzen");
//                boolean rashodovan = rs.getBoolean("p.Rashodovan");
//                Primerak p = new Primerak(primerakID, isbn, zaduzen, rashodovan);
//
//                int zaduzenjeID = rs.getInt("z.ZaduzenjeID");
//                java.util.Date datumZaduzenja = new Date(rs.getDate("z.DatumZaduzenja").getTime());
//                java.util.Date datumRazduzenja = null;
//                try {
//                    datumRazduzenja = new Date(rs.getDate("z.DatumRazduzenja").getTime());
//                } catch (Exception e) {
//                    datumRazduzenja = null;
//                }
//
//                int radnikZaduzioID = rs.getInt("z.RadnikZaduzioID");
//                int radnikRazduzioID = rs.getInt("z.RadnikRazduzioID");
//
//                Radnik rZaduzio = new Radnik();
//                rZaduzio.setRadnikID(radnikZaduzioID);
//
//                Radnik rRazduzio = new Radnik();
//                rRazduzio.setRadnikID(radnikRazduzioID);
//
//                Zaduzenje z = new Zaduzenje(zaduzenjeID, c, p, knjigaZaZaduzenje, datumZaduzenja, datumRazduzenja, rZaduzio, rRazduzio);
//                listaZaduzenja.add(z);
//
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return listaZaduzenja;
//    }

    //!
//    public void razduzi(OpstiDomenskiObjekat odo) throws SQLException {
//        Zaduzenje zaduzenje = (Zaduzenje)odo;
//        String sql = "UPDATE `zaduzenje` SET DatumRazduzenja = ?, `RadnikRazduzioID` = ? WHERE `PrimerakID` = ? AND `ISBN` = ?";
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//
//        ps.setDate(1, new java.sql.Date(zaduzenje.getDatumRazduzenja().getTime()));
//        ps.setInt(2, zaduzenje.getRadnikRazduzio().getRadnikID());
//        ps.setInt(3, zaduzenje.getPrimerak().getPrimerakId());
//        ps.setString(4, zaduzenje.getPrimerak().getIsbn());
//
//        ps.executeUpdate();
//    }
//!
    public ArrayList<Clan> vratiVazeceClanove() {
        ArrayList<Clan> listaclanova = new ArrayList<>();
        java.util.Date datum = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String danasnjiDatum = sdf.format(datum);
        String sql = "SELECT * FROM clan c JOIN clanarina cl ON c.`ClanID` = cl.`ClanID` WHERE cl.`DatumDO` >= '" + danasnjiDatum + "' ORDER BY c.`ClanID`";

        try {
            Statement statement = konekcija.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int clanID = resultSet.getInt("c.ClanID");
                String ime = resultSet.getString("c.Ime");
                String prezime = resultSet.getString("c.Prezime");
                java.util.Date datumRodjenja = new Date(resultSet.getDate("c.DatumRodjenja").getTime());
                long jmbg = resultSet.getLong("c.JMBG");
                String kontaktTelefon = resultSet.getString("c.KontaktTelefon");
                String email = resultSet.getString("c.Email");

                Clan clan = new Clan(clanID, jmbg, ime, prezime, datumRodjenja, kontaktTelefon, email);
                listaclanova.add(clan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaclanova;
    }

//    public void sacuvajNovoZaduzenje(OpstiDomenskiObjekat odo) throws SQLException {
//        Zaduzenje novoZaduzenje = (Zaduzenje)odo;
//        String sql = "INSERT INTO `zaduzenje`(`ClanID`, `PrimerakID`, `ISBN`, `DatumZaduzenja`, `RadnikZaduzioID`) VALUES(?, ?, ?, ?, ?) ;";
//
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//        ps.setInt(1, novoZaduzenje.getClan().getClanID());
//        ps.setInt(2, novoZaduzenje.getPrimerak().getPrimerakId());
//        ps.setString(3, novoZaduzenje.getPrimerak().getIsbn());
//        ps.setDate(4, new java.sql.Date(novoZaduzenje.getDatumZaduzenja().getTime()));
//        ps.setBoolean(5, novoZaduzenje.getPrimerak().isZaduzen());
//
//        ps.executeUpdate();
//    }

    //!
//    public void zaduziPrimerak(OpstiDomenskiObjekat odo) throws SQLException {
//        Zaduzenje novoZaduzenje = (Zaduzenje)odo;
//        String sql = "UPDATE `primerak` SET zaduzen = ? WHERE `PrimerakID` =? AND `ISBN` = ?";
//
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//        ps.setBoolean(1, true);
//        ps.setInt(2, novoZaduzenje.getPrimerak().getPrimerakId());
//        ps.setString(3, novoZaduzenje.getKnjiga().getIsbn());
//
//        ps.executeUpdate();
//    }

    //!
    public ArrayList<Knjiga> vratiZaduzenjaClana(OpstiDomenskiObjekat odo) {
        Clan clanZaBrisanje = (Clan)odo;
        ArrayList<Knjiga> listaKnjiga = new ArrayList<>();
        String sql = "SELECT DISTINCT * FROM `zaduzenje` z JOIN `clan` c ON z.`ClanID` = c.`ClanID` JOIN `knjiga` k ON z.`ISBN` = k.`ISBN` WHERE c.`ClanID` = " + clanZaBrisanje.getClanID() + " AND z.`DatumRazduzenja` IS NULL ";

        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String naziv = rs.getString("k.Naziv");
                String isbn = rs.getString("z.ISBN");
                Knjiga knjiga = new Knjiga();
                knjiga.setNaziv(naziv);
                knjiga.setIsbn(isbn);
                listaKnjiga.add(knjiga);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaKnjiga;
    }
//!
//    public void obrisiClanarinuzaClana(OpstiDomenskiObjekat odo) throws SQLException {
//        Clan clanZaBrisanje = (Clan)odo;
//        String sql = "DELETE FROM clanarina WHERE `ClanID` = ?";
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//        ps.setInt(1, clanZaBrisanje.getClanID());
//
//        ps.executeUpdate();
//    }
//!
    public ArrayList<Clan> vratiClanoveZaKnjigu(OpstiDomenskiObjekat odo) {
        Knjiga knjigaZaBrisanje = (Knjiga)odo;
        ArrayList<Clan> listaClanova = new ArrayList<>();
        String sql = "SELECT DISTINCT c.`Ime`, c.`Prezime`, c.`JMBG` FROM `zaduzenje` z JOIN clan c ON z.`ClanID` = c.`ClanID` WHERE z.`ISBN` = '" + knjigaZaBrisanje.getIsbn() + "' AND z.`DatumRazduzenja` IS NULL";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String ime = rs.getString("c.Ime");
                String prezime = rs.getString("c.Prezime");
                long jmbg = rs.getLong("c.JMBG");

                Clan c = new Clan();
                c.setIme(ime);
                c.setPrezime(prezime);
                c.setJmbg(jmbg);
                listaClanova.add(c);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaClanova;

    }
//!!!!!
//    public void razduziPrimerak(OpstiDomenskiObjekat odo) throws SQLException {
//        Zaduzenje zaduzenje = (Zaduzenje)odo;
//        String sql = "UPDATE `primerak` SET `Zaduzen` = ? WHERE `PrimerakID` = ? AND `ISBN` = ?";
//
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//
//        ps.setBoolean(1, false);
//        ps.setInt(2, zaduzenje.getPrimerak().getPrimerakId());
//        ps.setString(3, zaduzenje.getPrimerak().getIsbn());
//
//        ps.executeUpdate();
//    }
//!
    public ArrayList<String> proveriZaduzenjeClana(OpstiDomenskiObjekat odo) {
        Zaduzenje novoZaduzenje = (Zaduzenje)odo;
        ArrayList<String> listaZaduzenihKnjiga = new ArrayList<>();
        String sql = "SELECT z.`ISBN` FROM `zaduzenje` z WHERE z.`ClanID` = " + novoZaduzenje.getClan().getClanID() + " AND z.`DatumRazduzenja` IS NULL";

        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                String isbn = rs.getString("z.ISBN");
                listaZaduzenihKnjiga.add(isbn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaZaduzenihKnjiga;

    }
//!!!!!
//    public ArrayList<Zaduzenje> vratiSvaZaduzenjaClana(Clan clanZaZaduzenja) {
//        ArrayList<Zaduzenje> listaZaduzenja = new ArrayList<>();
//        String sql = "SELECT * FROM `zaduzenje` z JOIN knjiga k ON z.`ISBN` = k.`ISBN` WHERE z.`ClanID` = " + clanZaZaduzenja.getClanID() + " ORDER BY z.DatumZaduzenja DESC";
//        try {
//            Statement s = konekcija.createStatement();
//            ResultSet rs = s.executeQuery(sql);
//            while (rs.next()) {
//                String isbn = rs.getString("k.ISBN");
//                String naziv = rs.getString("k.Naziv");
//                String pisac = rs.getString("k.Pisac");
//                String godinaIZdanja = rs.getString("k.GodinaIzdanja");
//                String pismo = rs.getString("k.Pismo");
//                int brojStrana = rs.getInt("k.BrojStrana");
//                String zanr = rs.getString("k.Zanr");
//                String povez = rs.getString("k.Povez");
//                byte[] slika = rs.getBytes("k.Slika");
//                Knjiga k = new Knjiga(isbn, naziv, pisac, godinaIZdanja, pismo, brojStrana, zanr, povez, slika, null);
//
//                int primerakId = rs.getInt("z.PrimerakID");
//                String isbnPrimerka = rs.getString("z.ISBN");
//                Primerak primerak = new Primerak();
//                primerak.setPrimerakId(primerakId);
//                primerak.setIsbn(isbnPrimerka);
//
//                int radnikZaduzioId = rs.getInt("z.RadnikZaduzioID");
//                Radnik radinkZaduzio = new Radnik();
//                radinkZaduzio.setRadnikID(radnikZaduzioId);
//
//                int radnikRazduzioId = rs.getInt("z.RadnikRazduzioID");
//                Radnik radinkRazduzio = new Radnik();
//                radinkRazduzio.setRadnikID(radnikRazduzioId);
//                java.util.Date datumZaduzenja = new Date(rs.getDate("z.DatumZaduzenja").getTime());
//                java.util.Date datumRazduzenja = null;
//                try {
//                    datumRazduzenja = new Date(rs.getDate("z.DatumRazduzenja").getTime());
//                } catch (Exception e) {
//                    datumRazduzenja = null;
//                }
//
//                int zaduzenjeId = rs.getInt("z.ZaduzenjeID");
//                Zaduzenje z = new Zaduzenje(zaduzenjeId, clanZaZaduzenja, primerak, k, datumZaduzenja, datumRazduzenja, radinkZaduzio, radinkRazduzio);
//
//                listaZaduzenja.add(z);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return listaZaduzenja;
//    }
//!!!!
//    public void unesiPrimerkeKnjige(int i, String isbn) throws SQLException {
//        String sql = "INSERT INTO `primerak`(`PrimerakID`, `ISBN`, `Zaduzen`, `Rashodovan`) VALUES(?, ?, ?, ?); ";
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//        ps.setInt(1, i);
//        ps.setString(2, isbn);
//        ps.setBoolean(3, false);
//        ps.setBoolean(4, false);
//
//        ps.executeUpdate();
//
//    }

    //!!!!!
    public int vratiMAksIDPRimerka(Knjiga knjigaZaIzmenu) {
        int maksID = 0;
        String sql = "SELECT MAX(PrimerakID) as Maks FROM `primerak` WHERE `ISBN` = '" + knjigaZaIzmenu.getIsbn() + "'";
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                maksID = rs.getInt("Maks");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++maksID;
    }
//!!!!!!
//    public void rashodujPrimerak(Primerak p) throws SQLException {
//        String sql = "UPDATE `primerak` SET `Rashodovan` = ? WHERE `ISBN` = ? AND `PrimerakID` = ?";
//
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//
//        ps.setBoolean(1, true);
//        ps.setString(2, p.getIsbn());
//        ps.setInt(3, p.getPrimerakId());
//
//        ps.executeUpdate();
//    }
//!
//    public void obrisiSvePrimerkeZaDatuKnjigu(OpstiDomenskiObjekat odo) throws SQLException {
//        Knjiga knjigaZaBrisanje = (Knjiga)odo;
//        String sql = "DELETE FROM `primerak` WHERE `ISBN` = ?";
//
//        PreparedStatement ps = konekcija.prepareStatement(sql);
//        ps.setString(1, knjigaZaBrisanje.getIsbn());
//        ps.executeUpdate();
//    }

    public void insert(OpstiDomenskiObjekat odo) throws SQLException {
        String sql = "INSERT INTO " + odo.vratiNazivTabele() + "(" + odo.vratiNaziveKolona() + ")" + " VALUES " + "(" + odo.vratiVrednostiZaUnos() + ")";
        System.out.println(sql);
        Statement s = konekcija.createStatement();
        s.executeUpdate(sql);
        s.close();
    }

    public void update(OpstiDomenskiObjekat odo) throws SQLException {
        String sql = "UPDATE " + odo.vratiNazivTabele() + " SET " + odo.vratiVrednostiZaUpdate() + " WHERE " + odo.vratiWhereUslov();
        System.out.println(sql);
        Statement s = konekcija.createStatement();
        s.executeUpdate(sql);
        s.close();
    }

    public void delete(OpstiDomenskiObjekat odo) throws SQLException {
        String sql = "DELETE FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiWhereUslov();
        System.out.println(sql);
        Statement s = konekcija.createStatement();
        s.executeUpdate(sql);
        s.close();
    }

    public ResultSet select(OpstiDomenskiObjekat odo) throws SQLException {
        String sql = "SELECT " + odo.vratiKoloneZaSelektovanje() + " FROM " + odo.vratiNazivTabele() + " AS " + odo.vratiAlijas() + odo.vratiUslovZaJoin() + odo.vratiWhereUslovSelect() + odo.vratiGrupisanje();
        System.out.println(sql);
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(sql);
        return rs;
    }

    public int vratiID(OpstiDomenskiObjekat odo) throws SQLException {
        String sql = "SELECT MAX(" + odo.vratiKolonuZaID() + ") AS Maks FROM " + odo.vratiNazivTabele();
        System.out.println(sql);
        otvoriKonekciju();
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(sql);
        int id = 1;
        if (rs.next()) {
            id = rs.getInt("Maks");
        }
        return id;
    }

}
