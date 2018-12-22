package symbiosproduction.com.morbuslogs.database.models.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.DBCollection;
import symbiosproduction.com.morbuslogs.database.ToMap;
import symbiosproduction.com.morbuslogs.database.models.symptoms.AbstractSymptom;
import symbiosproduction.com.morbuslogs.database.models.symptoms.pain.PainSymptom;
import symbiosproduction.com.morbuslogs.database.models.symptoms.temperature.AbnormalTempSymptom;

public class SymptomsLog implements DBCollection,ToMap {

    private String dateOfSubmission;
    private String title;
    private Map<String, Map<String,Object>> mapOfSymptoms;
    private Integer sizeOfMap = 1;

    public static final String DB_SUB_COLLECTION = "userLogs";


    public SymptomsLog(String title) {
        SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("HH:mm:s dd-MM-yyyy");
        this.dateOfSubmission = ISO_8601_FORMAT.format(Calendar.getInstance().getTime());
        this.title = title;
        mapOfSymptoms = new HashMap<>();
    }

    public SymptomsLog(Map<String, Object> initData)
    {
        this.title = (String) initData.get("title");
        this.dateOfSubmission = (String) initData.get("dateOfSubmission");
        this.mapOfSymptoms = (Map<String, Map<String, Object>>) initData.get("mapOfSymptoms");
    }

    public String getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void addSymptomList(List<AbstractSymptom> listOfSymptoms)
    {
        for (AbstractSymptom symptom:
             listOfSymptoms) {
            addSymptom(symptom);
        }
    }

    public void addSymptom(AbstractSymptom abstractSymptom)
    {
        //TODO: Possibly change key so its easier to fetch
        mapOfSymptoms.put( sizeOfMap.toString() ,abstractSymptom.toMap());
        sizeOfMap++;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("dateOfSubmission",dateOfSubmission);
        dataMap.put("mapOfSymptoms",mapOfSymptoms);

        return dataMap;
    }

    public String getMainCollection() {
        return DB_MAIN_COLLECTION;
    }

    @Override
    public String getSubCollection()
    {
        return DB_SUB_COLLECTION;
    }

    public String getTitle()
    {
        return title;
    }


    public ArrayList<AbstractSymptom> getArrayOfSymptoms()
    {
        ArrayList<AbstractSymptom> abstractSymptoms = new ArrayList<>();
        for(Map.Entry<String, Map<String, Object>> entry: mapOfSymptoms.entrySet())
        {
            Map<String, Object> symptomInMap = entry.getValue();
            AbstractSymptom abstractSymptom = null;
            String symptomName = (String) symptomInMap.get("symptomName");
            switch(symptomName)
            {
                case "Pain":
                    abstractSymptom = new PainSymptom(symptomInMap);
                    break;
                case "Temperature":
                    abstractSymptom = new AbnormalTempSymptom(symptomInMap);
                    break;
                default:
                    break;
            }
            if(abstractSymptom != null)
            {
                abstractSymptoms.add(abstractSymptom);
            }
        }


        return abstractSymptoms;
    }
}
