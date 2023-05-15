import business.Business;

import java.util.ArrayList;

public class Cluster {

    private static HashTable table = new HashTable();
    SimilarityMetric sm = new SimilarityMetric();
    private static final ArrayList<Business[]> clustersList = new ArrayList<>();
    int[] wordDistList = new int[5];


    public Cluster(){

    }

    public void setTable(HashTable tableIn){
        table = tableIn;
    }



    //Gets the categories for a medoid and a business, then computes the similarity between them
    public void compareToMedoid() {

        for (Node n : table.bucketArray) {
            for (Node currentNode = n; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.value.getCategories() != null) {
                        Business[] currentCluster = clustersList.get(0);
                        int wordDist = sm.wordDistance(currentCluster[0].getCategories(), currentNode.value.getCategories());

                    if(wordDist < 18){
                        insertIntoCluster(currentNode.value);
                    }


                }
            }
        }
    }


    public void createCluster(Business medoid){
        //Create an array of type business.Business, insert the medoid at index 0, then insert the array into the arrayList
        Business[] businesses = new Business[1];
        businesses[0] = medoid;
       clustersList.add(businesses);


    }

    public void swapMedoid(){

    }

    public void insertIntoCluster(Business currentBusiness){
        //Save the business currently in the cluster
      Business[] oldBusinessInCluster = clustersList.get(0);
      int index = oldBusinessInCluster.length;


      Business[] newBusinessInCluster = new Business[index + 1];
      clustersList.remove(0);

      //Inserts the old business from the cluster into a new business.Business array
        for(int x = 0; oldBusinessInCluster.length > x; x++){
            newBusinessInCluster[x] = oldBusinessInCluster[x];
        }

        //Inserts the current business at the end of the array within the cluster
        newBusinessInCluster[index] = currentBusiness;
        clustersList.add(0, newBusinessInCluster);
    }


    public ArrayList<Business[]> getClustersList(){
        return clustersList;
    }

}





