package graph;

import java.util.LinkedList;

public class Graph {
    private final int totalVertices = 4;
    private Vertex vertex;
    private LinkedList<Vertex> list[];

    public Graph(){
        list = new LinkedList[5];
        for(int i = 0; i < 5; i++){
            list[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, Vertex vertex){

        list[source].addFirst(vertex);


    }

    public void printGraph(){
        for (int i = 0; i < 4 ; i++) {
            if(list[i].size() > 0) {
                System.out.print("Vertex " + i + " is connected to: ");
                for (int j = 0; j < list[i].size(); j++) {
                    System.out.print(list[i].get(j).value.getName() + " ");
                }
                System.out.println();
            }
        }
    }

    public LinkedList<Vertex>[] getGraph(){
        return list;
    }

}
