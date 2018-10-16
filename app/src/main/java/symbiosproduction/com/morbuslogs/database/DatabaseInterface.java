package symbiosproduction.com.morbuslogs.database;

import java.util.Map;

public interface DatabaseInterface {

     Map<String,Object> toMap();
     String getCollection();

}
