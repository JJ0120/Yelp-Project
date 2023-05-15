import business.Business;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;


import java.io.File;
import java.io.IOException;

public class JSONHandler {

    HashTable table = new HashTable();
    //Constructor
    public JSONHandler() {

    }


    public HashTable parseJSON(String file) throws IOException {
        File jsonFile = new File(file);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(jsonFile);

        for (int i = 0; i < 10000; i++) {
            //business.Business attributes
            Business currentBusiness;
            String businessName = "";
            String id = "";
            String category = "";
            double latitude = 0.0;
            double longitude = 0.0;
            parser.nextToken();

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String token = parser.getCurrentName();


                if (token.equals("attributes")){
                    if (parser.nextToken() == JsonToken.VALUE_NULL){
                    }else {
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                        }
                    }
                }else if (token.equals("hours")){
                    if (parser.nextToken() == JsonToken.VALUE_NULL){
                    }else {
                        while (parser.nextToken() != JsonToken.END_OBJECT) {
                        }
                    }

                    }else if (token.equals("categories")) {
                      parser.nextToken();
                      category = parser.getValueAsString();
                  } else if (token.equals("business_id")) {  //Gets business id
                      parser.nextToken();
                      id = parser.getValueAsString();
                  } else if (token.equals("name")) {            //Gets business name
                      parser.nextToken();
                      businessName = parser.getValueAsString();
                  } else if (token.equals("latitude")){
                    parser.nextToken();
                    latitude = parser.getValueAsDouble();
                } else if(token.equals("longitude")){
                    parser.nextToken();
                    longitude = parser.getValueAsDouble();
                }

            currentBusiness = new Business(id, businessName, category, latitude, longitude);
            table.put(currentBusiness.getId(), currentBusiness);
            }

        }
        return table;
    }
}
