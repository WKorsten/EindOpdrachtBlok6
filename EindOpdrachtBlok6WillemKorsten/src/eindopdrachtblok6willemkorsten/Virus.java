/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eindopdrachtblok6willemkorsten;

import java.util.ArrayList;


public class Virus {
    
protected int ID;
protected String soort;
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

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public ArrayList<Integer> getHostList() {
        return hostList;
    }

    public void setHostList(ArrayList<Integer> hostList) {
        this.hostList = hostList;
    }

    


}
