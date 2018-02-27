/*
 * Auteur: Willem Korsten
 * Klas: Bin-2B
 * Datum: 27-02-2018
 */
package eindopdrachtblok6willemkorsten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Beheerder
 */
public class VirusLogica {

    /* Deze class bevat alle logica van de applicatie. Vanuit de VirusGUI wordt hier het bestand ingelezen. Om de applicatie te testen is gebruik gemaakt van het bestand "virushostdb.tsv".
 * 
 * 
 * 
     */

    public static ArrayList<Virus> virusobjecten = new ArrayList<>();
    public static HashSet<Integer> viruslijst1 = new HashSet<Integer>();
    public static HashSet<Integer> viruslijst2 = new HashSet<Integer>();
    public static HashSet<Integer> commonVirus = new HashSet<Integer>();
    public static HashMap<Integer, String> hostmap = new HashMap<>();

    public void fileReader(File bestand) {
        /* Deze methode wordt aangeroepen door een bestand te kiezen vanuit de VirusGUI. De functionaliteiten van deze methode zijn gebaseerd op de indeling een tsv-bestand.
 * De methode zal het bestand in regels opslaan in een lijst, waarna hij toVirus aanroept, met deze lijst als parameter.
 * 
 * 
         */
        ArrayList<String> datalijst = new ArrayList();
        StringBuilder build = new StringBuilder();

        try {
            BufferedReader lezer = new BufferedReader(new FileReader(bestand));
            String regel = lezer.readLine();

            while (regel != null) {

                datalijst.add(regel);
                regel = lezer.readLine();

            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "De file is niet gevonden");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Er is een onbekende fout opgetreden, bel Willem Korsten voor hulp");
        } catch (Exception ex) {
            Logger.getLogger(VirusLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        toVirus(datalijst);

    }

    public void toVirus(ArrayList<String> datalijst) {
        /*  Deze methode verwerkt de informatie uit de datalijst, en creert nieuwe objecten van de Virus-class en slaat deze op in een lijst. Vervolgens zal het ieder per object de passende informatie opslaan door de setters aan te roepen.
 *  Omdat in het geteste bestand er onder de kolom Host een aantal cellen leeg zijn, riep deze methode exceptions op wanneer deze cellen benaderd werden.
 *  Aangezien dit duidde op een lege cel, is er voor gekozen om de exception af te vangen, en vervolgens door te gaan zonder de host op te slaan.
         */
        int i = -1;
        String[] classificatie = {"All", "dsRNA", "dsDNA", "ssRNA", "ssDNA", "Retro", "Satellite", "virophage", "Viroid", "Other"};
        String[] virusInfo;
        ArrayList<Integer> hostList = new ArrayList<>();

        VirusGUI.ViralClasses.setModel(new DefaultComboBoxModel(classificatie));
        for (String dataregel : datalijst) {
            if (dataregel.equals(datalijst.get(0))) {
                continue;
            }

            virusInfo = dataregel.split("\t", -1);

            if (virusobjecten.isEmpty()) {
                virusobjecten.add(new Virus());
                i++;
                virusobjecten.get(i).setID(Integer.parseInt(virusInfo[0]));
                for (String type : classificatie) {
                    if (type.equals(classificatie[0]) | type.equals(classificatie[9])) {
                        continue;
                    }
                    if (virusInfo[2].contains(type)) {
                        virusobjecten.get(i).setClassificatie(type);
                        break;
                    } else {
                        virusobjecten.get(i).setClassificatie("Other");
                    }
                }
                try {
                    hostList.add(Integer.parseInt(virusInfo[7]));
                    if (!hostmap.containsValue(Integer.parseInt(virusInfo[7]))) {
                        hostmap.put(Integer.parseInt(virusInfo[7]), virusInfo[8] + " - " + virusInfo[7]);
                    }
                } catch (Exception e) {
                }
            } else if (virusobjecten.get(i).getID() == Integer.parseInt(virusInfo[0])) {
                try {
                    hostList.add(Integer.parseInt(virusInfo[7]));
                    if (!hostmap.containsValue(Integer.parseInt(virusInfo[7]))) {
                        hostmap.put(Integer.parseInt(virusInfo[7]), virusInfo[8] + " - " + virusInfo[7]);
                    }
                } catch (Exception e) {
                }
            } else {
                if (!hostList.isEmpty()) {
                    virusobjecten.get(i).setHostList((ArrayList<Integer>) hostList.clone());
                }
                virusobjecten.add(new Virus());
                i++;
                virusobjecten.get(i).setID(Integer.parseInt(virusInfo[0]));
                for (String type : classificatie) {
                    if (type.equals(classificatie[0]) | type.equals(classificatie[9])) {
                        continue;
                    }
                    if (virusInfo[2].contains(type)) {
                        virusobjecten.get(i).setClassificatie(type);
                        break;
                    } else {
                        virusobjecten.get(i).setClassificatie("Other");
                    }
                }
                hostList.clear();
                try {
                    hostList.add(Integer.parseInt(virusInfo[7]));
                    if (!hostmap.containsValue(Integer.parseInt(virusInfo[7]))) {
                        hostmap.put(Integer.parseInt(virusInfo[7]), virusInfo[8] + " - " + virusInfo[7]);
                    }
                } catch (Exception e) {
                }
            }
        }

    }

    public void vergelijkHosts(String classSelectie) {
        /* Deze methode accepteert de Virus-Classificatie die is gekozen door de gebruiker in het dropdownmenu bij Viral Classification. 
 * Vervolgens matched het de classificatie met de juiste virusobjecten en hun hosts, waarna deze hosts kunnen worden weergegeven in een dropdownmenu.
 * 
         */

        ArrayList<String> hostlijst = new ArrayList<>();
        if (classSelectie.equals("All")) {
            for (Virus virus : virusobjecten) {
                if (virus.getHostList() != null) {
                    for (Integer hostID : virus.getHostList()) {
                        hostlijst.add(hostmap.get(hostID));
                    }
                }
            }
        } else {
            for (Virus virus : virusobjecten) {
                if (virus.getClassificatie().equals(classSelectie)) {
                    if (virus.getHostList() != null) {
                        for (Integer hostID : virus.getHostList()) {
                            hostlijst.add(hostmap.get(hostID));
                        }
                    }

                }
            }
        }
        VirusGUI.hostID1.setModel(new DefaultComboBoxModel(Arrays.stream(hostlijst.toArray()).distinct().toArray()));
        VirusGUI.hostID2.setModel(new DefaultComboBoxModel(Arrays.stream(hostlijst.toArray()).distinct().toArray()));

    }

    public void vergelijkVirus(String hostSelectie, int lijstID) {
        /* Deze methode neemt de informatie van de geselecteerde host en van welk dropdownmenu is gekozen mee, om twee lijsten te vullen met virusID's elk horend bij de individuele dropdownmenus.
        * Beide lijsten worden omgezet naar een set waarna een vergelijking op overeenkomende virusID's mogelijk is. Dit resultaat wordt weergegeven onder common viruses. 
        * Als beide lijsten gevuld zijn gaan de radiobuttons aan, waarna sortering door goSort mogelijk is. 
        */

        VirusGUI.jCommonHosts.setText(null);
        if (lijstID == 1) {
            VirusGUI.jVirusLijst6.setText(null);
            viruslijst1.clear();
        } else {
            VirusGUI.jVirusLijst4.setText(null);
            viruslijst2.clear();
        }

        for (Virus virus : virusobjecten) {
            if (virus.getHostList() != null) {
                if (virus.getHostList().contains(Integer.parseInt(hostSelectie.split("-")[1].trim()))) {
                    if (lijstID == 1) {
                        viruslijst1.add(virus.getID());
                    } else {
                        viruslijst2.add(virus.getID());
                    }
                }
            }
        }
        if (lijstID == 1) {
            for (int ID1 : viruslijst1) {
                VirusGUI.jVirusLijst6.append(Integer.toString(ID1) + "\n");
                VirusGUI.jVirusLijst6.setCaretPosition(0);
            }
        } else {
            for (int ID2 : viruslijst2) {
                VirusGUI.jVirusLijst4.append(Integer.toString(ID2) + "\n");
                VirusGUI.jVirusLijst4.setCaretPosition(0);
            }
        
        }
        if (!viruslijst1.isEmpty() & !viruslijst2.isEmpty()) {
            VirusGUI.jRadioButton1.setEnabled(true);
            VirusGUI.jRadioButton2.setEnabled(true);
            VirusGUI.jRadioButton3.setEnabled(true);
            commonVirus = (HashSet<Integer>) viruslijst1.clone();
            commonVirus.retainAll(viruslijst2);
            for (int ID : commonVirus) {
                VirusGUI.jCommonHosts.append(Integer.toString(ID) + "\n");
                VirusGUI.jCommonHosts.setCaretPosition(0);
            }
        }
    }

    public void goSort(String eigenschap) {
        /* Deze methode zorgt ervoor dat er gesorteerd kan worden op de eigenschappen VirusID, classificatie en het aantal hosts van het Virus.
        *  Omdat VirusID een getal is, is er gekozen om gebruik te maken van de standaard collections.sort voor deze sortering.
           Omdat classificatie een string is, is er gekozen om gebruik te maken van de compareTo interface die is omschreven in de Virusclass, onderdeel van het collections framework.
        */
                
        List<Integer> sorteerLijst1 = new ArrayList(viruslijst1);
        List<Integer> sorteerLijst2 = new ArrayList(viruslijst2);
        List<Integer> sorteerCommon = new ArrayList(commonVirus);
        if (eigenschap.equals("VirusID")) {
            Collections.sort(sorteerLijst1);
            Collections.sort(sorteerLijst2);
            Collections.sort(sorteerCommon);
            VirusGUI.jVirusLijst6.setText(null);
            VirusGUI.jVirusLijst4.setText(null);
            VirusGUI.jCommonHosts.setText(null);
            for (int ID1 : sorteerLijst1) {
                VirusGUI.jVirusLijst6.append(Integer.toString(ID1) + "\n");
                VirusGUI.jVirusLijst6.setCaretPosition(0);
            }
            for (int ID2 : sorteerLijst2) {
                VirusGUI.jVirusLijst4.append(Integer.toString(ID2) + "\n");
                VirusGUI.jVirusLijst4.setCaretPosition(0);
            }
            for (int commonID : sorteerCommon) {
                VirusGUI.jCommonHosts.append(Integer.toString(commonID) + "\n");
                VirusGUI.jCommonHosts.setCaretPosition(0);
            }

        } else if (eigenschap.equals("Class") & VirusGUI.ViralClasses.getSelectedItem().equals("All")) {
            ArrayList<Virus> virusObjectsLijst1 = new ArrayList();
            ArrayList<Virus> virusObjectsLijst2 = new ArrayList();
            for (String ID1 : VirusGUI.jVirusLijst6.getText().split("\n")) {
                for (Virus virus : virusobjecten) {
                    if (virus.getID() == Integer.parseInt(ID1)) {
                        virusObjectsLijst1.add(virus);
                    }
                }
            }
            for (String ID2 : VirusGUI.jVirusLijst4.getText().split("\n")) {
                for (Virus virus : virusobjecten) {
                    if (virus.getID() == Integer.parseInt(ID2)) {
                        virusObjectsLijst2.add(virus);
                    }
                }
            }
            VirusGUI.jVirusLijst6.setText(null);
            VirusGUI.jVirusLijst4.setText(null);
            VirusGUI.jCommonHosts.setText(null);
            Collections.sort(virusObjectsLijst1);
            Collections.sort(virusObjectsLijst2);
            viruslijst1.clear();
            viruslijst2.clear();
            commonVirus.clear();
            for (Virus virus : virusObjectsLijst1) {
                viruslijst1.add(virus.getID());
            }
            for (Virus virus : virusObjectsLijst2) {
                viruslijst2.add(virus.getID());
            }
            commonVirus = (HashSet<Integer>) viruslijst1.clone();
            commonVirus.retainAll(viruslijst2);

            for (int ID1 : viruslijst1) {
                VirusGUI.jVirusLijst6.append(Integer.toString(ID1) + "\n");
                VirusGUI.jVirusLijst6.setCaretPosition(0);
            }

            for (int ID2 : viruslijst2) {
                VirusGUI.jVirusLijst4.append(Integer.toString(ID2) + "\n");
                VirusGUI.jVirusLijst4.setCaretPosition(0);
            }
            for (int ID : commonVirus) {
                VirusGUI.jCommonHosts.append(Integer.toString(ID) + "\n");
                VirusGUI.jCommonHosts.setCaretPosition(0);
            }
        } else {
            ArrayList<Virus> virusObjectsLijst1 = new ArrayList();
            ArrayList<Virus> virusObjectsLijst2 = new ArrayList();

            for (String ID1 : VirusGUI.jVirusLijst6.getText().split("\n")) {
                for (Virus virus : virusobjecten) {
                    if (virus.getID() == Integer.parseInt(ID1)) {
                        virusObjectsLijst1.add(virus);
                    }
                }
            }
            for (String ID2 : VirusGUI.jVirusLijst4.getText().split("\n")) {
                for (Virus virus : virusobjecten) {
                    if (virus.getID() == Integer.parseInt(ID2)) {
                        virusObjectsLijst2.add(virus);
                    }
                }
            }
            VirusGUI.jVirusLijst6.setText(null);
            VirusGUI.jVirusLijst4.setText(null);
            VirusGUI.jCommonHosts.setText(null);
            Collections.sort(virusObjectsLijst1, new NrOfHostsComparator());
            Collections.sort(virusObjectsLijst2, new NrOfHostsComparator());
            viruslijst1.clear();
            viruslijst2.clear();
            commonVirus.clear();
            for (Virus virus : virusObjectsLijst1) {
                viruslijst1.add(virus.getID());
            }
            for (Virus virus : virusObjectsLijst2) {
                viruslijst2.add(virus.getID());
            }
            commonVirus = (HashSet<Integer>) viruslijst1.clone();
            commonVirus.retainAll(viruslijst2);

            for (int ID1 : viruslijst1) {
                VirusGUI.jVirusLijst6.append(Integer.toString(ID1) + "\n");
                VirusGUI.jVirusLijst6.setCaretPosition(0);
            }

            for (int ID2 : viruslijst2) {
                VirusGUI.jVirusLijst4.append(Integer.toString(ID2) + "\n");
                VirusGUI.jVirusLijst4.setCaretPosition(0);
            }
            for (int ID : commonVirus) {
                VirusGUI.jCommonHosts.append(Integer.toString(ID) + "\n");
                VirusGUI.jCommonHosts.setCaretPosition(0);
            }
        }
    }
}
