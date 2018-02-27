/*
 * Auteur: Willem Korsten
 * Klas: Bin-2B
 * Datum: 27-02-2018
 */
package eindopdrachtblok6willemkorsten;

import java.util.*;


public class NrOfHostsComparator implements Comparator<Virus>{
   public int compare(Virus v1,Virus v2){
     if(v1.getHostList().size() == v2.getHostList().size())
       return 0;
   else if(v1.getHostList().size() > v2.getHostList().size())
       return 1;
   else
       return -1;
  }
}
    

