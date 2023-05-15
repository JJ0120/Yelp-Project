import graph.Graph;
import graph.Vertex;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static ArrayList<business.Business> Business;

    public static void main(String[] args) throws IOException {
        JSONHandler jsonHandler = new JSONHandler();
        UI gui = new UI();

        HashTable table = jsonHandler.parseJSON("C:/yelp_dataset/business_dataset.json");

        //Creates a cluster and adds a business as a medoid
        Cluster clusterObject = new Cluster();
        clusterObject.setTable(table);

        SimilarityMetric similarityMetric = new SimilarityMetric();
        similarityMetric.setTable(table);
//         clusterObject.createCluster(table.getBusTest(530).value, 0);
//         clusterObject.createCluster(table.getBusTest(1050).value, 1);
//        clusterObject.createCluster(table.getBusTest(3079).value, 2);
//        clusterObject.createCluster(table.getBusTest(1538).value,3);
//         clusterObject.createCluster(table.getBusTest(3077).value, 4);


        Vertex b1 = new Vertex(table.bucketArray[0].value);
        Vertex b2 = new Vertex(table.bucketArray[1].value);

        Graph g1 = new Graph();
        g1.addEdge(0, b1);
        g1.addEdge(0, b2);

        gui.setTable(table);
        gui.initializeGUI();
        gui.addComponents();
        //clusterObject.compareToMedoid();





    }



    }
