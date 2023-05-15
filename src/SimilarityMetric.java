import business.Business;

public class SimilarityMetric {
    private static HashTable table = new HashTable();

    public SimilarityMetric(){

    }


    public void setTable(HashTable tableIn){
        table = tableIn;
    }

    public int wordDistance(String a, String b){
        int edits = 0;

        //string a vars
        String aCharPos = "";
        String[] aLetters = new String[a.length() + b.length()];

        //string b vars
        String bCharPos = "";
        String[] bLetters = new String[b.length() + a.length()];

        //Gets each letter from string a and puts it into an array
        for(int i = 0; i < a.length(); i++) {
             aCharPos = a.substring(i, i + 1);
            aLetters[i] = aCharPos;
        }

        //Gets each letter from string b and puts it into an array
        for(int i = 0; i < b.length(); i++) {
            bCharPos = b.substring(i, i + 1);
            bLetters[i] = bCharPos;
        }

            for(int i = 0; i < a.length(); i++) {
                if (!aLetters[i].equals(bLetters[i])){
                    edits++;
                }
            }
        return edits;
    }

    //Haversine formula
    public double getDistance(Business b1, Business b2){
        double earthRadius = 3959.87433;   //Earth radius in MI



        double startLat = Math.toRadians(b1.getLatitude());     //b1
        double startLong = Math.toRadians(b1.getLongitude());   //b2

        double endLat = Math.toRadians(b2.getLatitude());
        double endLong = Math.toRadians(b2.getLongitude());

        double dLatitude = endLat - startLat;
        double dLongitude = endLong - startLong;

        double a = Math.pow((Math.sin(dLatitude/2)), 2) + Math.cos(startLat) * Math.cos(endLat)
                * Math.pow(Math.sin(dLongitude/2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return (earthRadius * c);
    }

    public Business[] distanceSimilarity(String baseBusinessName){
        Business[] closestFive = new Business[4];
        //For each node in the table ...
        for (Node node : table.bucketArray) {
            //Skip over null Nodes
            if (node != null){
                Business currentBusiness = node.value; //Initializes current Business in loop
                //Gets the distance from the specified Business to the currentBusiness
                double currentDistance = getDistance(currentBusiness, searchByBusinessName(baseBusinessName));
                //Checks if any values in closestFive are null, if so, add current business at null spot
                for (int j = 0; j < closestFive.length; j++) {
                    if (closestFive[j] == null) {
                        closestFive[j] = node.value;
                        break;
                    }
                    //Calculates distance of current closestFive array Business and determines whether to replace it
                    double closestDistance = getDistance(closestFive[j], table.get(searchByBusinessName(baseBusinessName).getId()));
                    //Checks if it isn't zero to ensure we don't add the baseBusiness
                    if (currentDistance < closestDistance && currentDistance != 0) {
                        //Shift the closestFive over at the point we are adding it to
                        System.arraycopy(closestFive, j, closestFive, j + 1, closestFive.length-(j+1));
                        closestFive[j] = node.value;
                        break;
                    }
                }
            }

        }
        return closestFive;
    }

    //Method to search the table by business name
    public Business searchByBusinessName(String businessSearchName){
        //For each Node in the table...
        for (Node n : table.bucketArray) {
            //Check each next value of the Node to ensure each value gets checked
            for(Node e = n; e != null; e = e.next) {
                if (e.value.getName().equals(businessSearchName)) {
                    return e.value;
                }
            }
        }
        return null; //If the business can't be found, return null
    }
}
