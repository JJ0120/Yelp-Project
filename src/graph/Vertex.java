package graph;


import business.Business;

public class Vertex {
    public Business value;
    public Boolean visited;

    public Vertex(Business business){
        this.value = business;
        visited = false;
    }


}
