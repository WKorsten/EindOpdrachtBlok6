/*
 * Auteur: Willem Korsten
 * Klas: Bin-2B
 * Datum: 27-02-2018
 */
package eindopdrachtblok6willemkorsten;

import java.util.ArrayList;


public class Virus implements Comparable{
/*    Dit is de Virus-class die verschillende virus-gerelateerde informatie kan opslaan. 
 *    In deze applicatie zal dit object de informatie opslaan die afkomstig is uit het bestand dat door de VirusGUI wordt ingeladen.
 *    
 */



protected int ID;
protected ArrayList<Integer> hostList;
protected String classificatie;

    public String getClassificatie() {
        return classificatie;
    }

    public void setClassificatie(String classificatie) {
        this.classificatie = classificatie;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Integer> getHostList() {
        return hostList;
    }

    public void setHostList(ArrayList<Integer> hostList) {
        this.hostList = hostList;
    }
@Override
        public int compareTo(Object x) {

        Virus g = (Virus) x;
        if (g.getClassificatie().compareTo(this.getClassificatie()) > 0) {
            return -1;
        } else if (g.getClassificatie().compareTo(this.getClassificatie()) < 0) {
            return +1;
        } else {
            return 0;
        }

    }  


}
