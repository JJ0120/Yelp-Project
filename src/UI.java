import business.Business;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class UI implements ActionListener {


    public UI() {

    }

    //Globals
    private static JFrame frame;
    private JPanel panel;
    private SpringLayout layout;
    private JTextField businessNameBox;
    String businessName;
    private JComboBox<String> jComboBox;
    HashTable mainTable;
    private static final HashMap<String, String> businessLookup = new HashMap<>();
    private JButton clearButton;
    private int counter = 0;
    private LinkedList<Vertex>[] businessGraph;
    private JTextArea graphRepresentation;
    private JTextArea simBusTextArea;


    //Creates GUI window
    public void initializeGUI() {
        //GUI setup
        frame = new JFrame("business.Business similarity");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1000, 400);
        layout = new SpringLayout();
        panel = new JPanel(layout);
        frame.add(panel);
    }

    //Add all components
    public void addComponents() throws IOException {
        //Components-directions label
        JLabel directionsLabel1 = new JLabel("Enter a business");
        panel.add(directionsLabel1);

        //Components-list label
        JLabel similarBusinessesLabel = new JLabel("Graph representation");
        panel.add(similarBusinessesLabel);
        layout.putConstraint(SpringLayout.WEST, similarBusinessesLabel, 220, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, similarBusinessesLabel, 50, SpringLayout.NORTH, panel);

        //business.Business name text box
        businessNameBox = new JTextField(10);
        panel.add(businessNameBox);
        layout.putConstraint(SpringLayout.WEST, businessNameBox, 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, businessNameBox, 67, SpringLayout.NORTH, panel);

        //Components-business.Business list
        jComboBox = new JComboBox<>();
        panel.add(jComboBox);
        layout.putConstraint(SpringLayout.WEST, jComboBox, 600, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jComboBox, 20, SpringLayout.NORTH, panel);


        //Drop down selection list
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(counter != 0){
                    simBusTextArea.setText(null);
                }

                JComboBox cb = (JComboBox)e.getSource();
                String nameSelected = (String)cb.getSelectedItem();

                Cluster clusterRef = new Cluster();
                if(nameSelected != null) {
                    clusterRef.createCluster(mainTable.get(businessLookup.get(nameSelected)));
                    clusterRef.compareToMedoid();


                    ArrayList<Business[]> clusters = clusterRef.getClustersList();
                    for (int i = 0; i < clusters.get(counter).length; i++) {
                        simBusTextArea.append(clusters.get(counter)[i].getId());
                        simBusTextArea.append("\n");
                    }
                }

                counter++;
            }
        });


        //Clear button
        clearButton = new JButton("Clear");
        panel.add(clearButton);
        layout.putConstraint(SpringLayout.WEST, clearButton, 50, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, clearButton, 100, SpringLayout.NORTH, panel);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                businessNameBox.setText(null);
                graphRepresentation.setText(null);
                counter = 0;
                simBusTextArea.setText(null);
                jComboBox.removeAllItems();
            }
        });

        businessNameBox.addActionListener(this);
        frame.setVisible(true);

        //Graph text area
        graphRepresentation = new JTextArea();
        graphRepresentation.setEditable(false);
        panel.add(graphRepresentation);
        layout.putConstraint(SpringLayout.WEST, graphRepresentation, 190, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, graphRepresentation, 290, SpringLayout.NORTH, panel);

        //Similar businesses text area
        simBusTextArea = new JTextArea();
       simBusTextArea.setEditable(false);
        panel.add(simBusTextArea);
        layout.putConstraint(SpringLayout.WEST, simBusTextArea, 540, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, simBusTextArea, 290, SpringLayout.NORTH, panel);




        for (Node n : mainTable.bucketArray) {
            for (Node currentNode = n; currentNode != null; currentNode = currentNode.next) {
                businessLookup.put(currentNode.value.getName(), currentNode.value.getId());
            }
        }
    }

    public void setTable(HashTable table) {
        mainTable = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        businessName = businessNameBox.getText();


        Graph graph = new Graph();
        SimilarityMetric similarityMetric = new SimilarityMetric();
        Business[] closeBusinesses = similarityMetric.distanceSimilarity(businessName);

        //Creates inital vertex which will be compared against for distance
        Vertex vertex = new Vertex(mainTable.get(businessLookup.get(businessName)));


        //Creates four vertices and created edges for each
        int count = 1;
        for (Business business : closeBusinesses) {
            Vertex v = new Vertex(mainTable.get(businessLookup.get(business.getName())));
            graph.addEdge(0, v);
            if (count != 5) {
                graph.addEdge(count, vertex);
                count++;
            }

        }

        String[] namesOfBusInGraph = new String[5];


        //Business to select
        String[] businessNameList = new String[5];

        for (int i = 3; i > 0; i--) {
            businessNameList[i] = closeBusinesses[i].getName();
            jComboBox.addItem( closeBusinesses[i].getName());
        }
        businessNameList[4] = closeBusinesses[0].getName();
        jComboBox.addItem(closeBusinesses[0].getName());        //
        businessNameList[0] = businessName;
        jComboBox.addItem(businessName);        //


        businessGraph = graph.getGraph();

        for (int i = 0; i < 5; i++) {
            if (businessGraph[i].size() > 0) {
                graphRepresentation.append("Vertex " + businessNameList[i] + " is connected to: ");
                graphRepresentation.append("\n");
                for (int j = 0; j < businessGraph[i].size(); j++) {
                    graphRepresentation.append(businessGraph[i].get(j).value.getName() + " ");
                    graphRepresentation.append("\n");
                }
            }
        }

//        clusterRef.compareToMedoid();
//
//
//        ArrayList<Business[]> clusters = clusterRef.getClustersList();
//        for(int i = 0; i < clusters.get(0).length; i++){
//            similarBusinesses.append(clusters.get(0)[i].getId());
//            similarBusinesses.append("\n");
//        }


    }




}


