/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eindopdrachtblok6willemkorsten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Beheerder
 */
public class VirusLogica {

    public static ArrayList<Virus> virusobjecten = new ArrayList<>();

    public void fileReader(File bestand) {
        ArrayList<String> viruslijst = new ArrayList();
        StringBuilder build = new StringBuilder();

        try {
            BufferedReader lezer = new BufferedReader(new FileReader(bestand));
            String regel = lezer.readLine();

            while (regel != null) {

                viruslijst.add(regel);
                regel = lezer.readLine();

            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "De file is niet gevonden");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Er is een onbekende fout opgetreden, bel Willem Korsten voor hulp");
        } catch (Exception ex) {
            Logger.getLogger(VirusLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        toVirus(viruslijst);

    }

    public void toVirus(ArrayList<String> viruslijst) {
        int i = -1;

        String[] classificatie = {"None", "dsRNA", "dsDNA", "ssRNA", "ssDNA", "Retrovirus", "Satellietvirussen en Virofagen", "Viroïden", "Anderen"};

        String[] virusInfo;

        ArrayList<Integer> hostList = new ArrayList<>();
        VirusGUI.ViralClasses.setModel(new DefaultComboBoxModel(classificatie));
        for (String virus : viruslijst) {
            if (virus.equals(viruslijst.get(0))) {
                continue;
            }
            if (!virusobjecten.isEmpty()) {
                //System.out.println(virusobjecten.get(i).getID());
            }

            virusInfo = virus.split("\t", -1);
            if (virusobjecten.isEmpty()) {

                virusobjecten.add(new Virus());
                i++;
                virusobjecten.get(i).setID(Integer.parseInt(virusInfo[0]));
                if (virusInfo[2].contains("dsRNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[1]);
                } else if (virusInfo[2].toUpperCase().contains("DSDNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[2]);
                } else if (virusInfo[2].toUpperCase().contains("SSRNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[3]);
                } else if (virusInfo[2].toUpperCase().contains("SSDNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[4]);
                } else if (virusInfo[2].toUpperCase().contains("RETRO")) {
                    virusobjecten.get(i).setClassificatie(classificatie[5]);
                } else if (virusInfo[2].toUpperCase().contains("SATELLITE") || virusInfo[2].toUpperCase().contains("VIROPHAGE")) {
                    virusobjecten.get(i).setClassificatie(classificatie[6]);
                } else if (virusInfo[2].toUpperCase().contains("VIROID")) {
                    virusobjecten.get(i).setClassificatie(classificatie[7]);
                } else {
                    virusobjecten.get(i).setClassificatie(classificatie[8]);
                }

                try {
                    hostList.add(Integer.parseInt(virusInfo[7]));
                } catch (Exception e) {

                }

            } else if (virusobjecten.get(i).getID() == Integer.parseInt(virusInfo[0])) {

                try {
                    hostList.add(Integer.parseInt(virusInfo[7]));
                } catch (Exception e) {

                }

            } else {
                if (!hostList.isEmpty()) {
                    virusobjecten.get(i).setHostList((ArrayList<Integer>) hostList.clone());
                }

                virusobjecten.add(new Virus());
                i++;
                virusobjecten.get(i).setID(Integer.parseInt(virusInfo[0]));
                if (virusInfo[2].contains("dsRNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[1]);
                } else if (virusInfo[2].toUpperCase().contains("DSDNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[2]);
                } else if (virusInfo[2].toUpperCase().contains("SSRNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[3]);
                } else if (virusInfo[2].toUpperCase().contains("SSDNA")) {
                    virusobjecten.get(i).setClassificatie(classificatie[4]);
                } else if (virusInfo[2].toUpperCase().contains("RETRO")) {
                    virusobjecten.get(i).setClassificatie(classificatie[5]);
                } else if (virusInfo[2].toUpperCase().contains("SATELLITE") || virusInfo[2].toUpperCase().contains("VIROPHAGE")) {
                    virusobjecten.get(i).setClassificatie(classificatie[6]);
                } else if (virusInfo[2].toUpperCase().contains("VIROID")) {
                    virusobjecten.get(i).setClassificatie(classificatie[7]);
                } else {
                    virusobjecten.get(i).setClassificatie(classificatie[8]);
                }
                hostList.clear();

                try {
                    hostList.add(Integer.parseInt(virusInfo[7]));
                } catch (Exception e) {

                }

            }
            //System.out.println(virusobjecten.get(i).getClassificatie());
        }

    }

    public void vergelijkHosts(String classSelectie) {
        ArrayList<Integer> hostlijst = new ArrayList<>();
        if (classSelectie.equals("None")) {
            for (Virus virus : virusobjecten) {
                if (virus.getHostList() != null) {
                    for (Integer hostID : virus.getHostList()) {
                        hostlijst.add(hostID);
                    }
                }
            }
        }

        for (Virus virus : virusobjecten) {
            if (virus.getClassificatie().equals(classSelectie)) {
                if (virus.getHostList() != null) {
                    for (Integer hostID : virus.getHostList()) {
                        hostlijst.add(hostID);
                    }
                }

            }
        }

        VirusGUI.hostID1.setModel(new DefaultComboBoxModel(Arrays.stream(hostlijst.toArray()).distinct().toArray()));
        VirusGUI.hostID2.setModel(new DefaultComboBoxModel(Arrays.stream(hostlijst.toArray()).distinct().toArray()));

    }

    public void vergelijkVirus(String hostSelectie, int lijstID) {
        HashSet<Integer> viruslijst1 = new HashSet<Integer>();
        HashSet<Integer> viruslijst2 = new HashSet<Integer>();
        
        VirusGUI.jCommonHosts.setText(null);
        if (lijstID == 1) {
            VirusGUI.jVirusLijst6.setText(null);
        } else {
            VirusGUI.jVirusLijst4.setText(null);
        }

        
        ArrayList<Integer> viruslijst = new ArrayList<>();
        for (Virus virus : virusobjecten) {

            if (virus.getHostList() != null) {
                if (virus.getHostList().contains(Integer.parseInt(hostSelectie))) {

                    
                        viruslijst.add(virus.getID());
                    }

                }

            }

        
        if (lijstID == 1) {
            for (int ID1 : viruslijst) {

                VirusGUI.jVirusLijst6.append(Integer.toString(ID1) + "\n");
                viruslijst1.add(ID1);
            }
        } else {
            for (int ID2 : viruslijst) {

                VirusGUI.jVirusLijst4.append(Integer.toString(ID2) + "\n");
                viruslijst2.add(ID2);
            }

        }
        System.out.println(viruslijst1);
        System.out.println(viruslijst2);
        if (!viruslijst1.isEmpty()& !viruslijst2.isEmpty()){viruslijst1.retainAll(viruslijst2);}
        
        
        for (int ID : viruslijst1) {

                VirusGUI.jCommonHosts.append(Integer.toString(ID) + "\n");
                
            }
        
        
    }
}
