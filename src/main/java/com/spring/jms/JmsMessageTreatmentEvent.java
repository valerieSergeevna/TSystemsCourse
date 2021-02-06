package com.spring.jms;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = JmsMessageTreatmentEvent.class)
public class JmsMessageTreatmentEvent {
    private List<TreatmentEventDTOImpl> treatmentEventDTOList;

    public JmsMessageTreatmentEvent() {
    }

    public JmsMessageTreatmentEvent(List<TreatmentEventDTOImpl> treatmentEventDTOList) {
        this.treatmentEventDTOList = treatmentEventDTOList;
    }

    public List<TreatmentEventDTOImpl> getTreatmentEventDTOList() {
        return treatmentEventDTOList;
    }

    public void setTreatmentEventDTOList(List<TreatmentEventDTOImpl> treatmentEventDTOList) {
        this.treatmentEventDTOList = treatmentEventDTOList;
    }

    @Override
    public String toString(){
        JSONObject jsonInfo = new JSONObject();
        try {
            JSONArray eventsArray = new JSONArray();
            if (treatmentEventDTOList != null) {
                treatmentEventDTOList.forEach(event -> {
                    JSONObject subJson = new JSONObject();
                    try {
                        subJson.put("id", event.getId());
                        subJson.put("dose", event.getDose());
                        subJson.put("cancelReason", event.getCancelReason());
                        subJson.put("status", event.getStatus());
                        subJson.put("type", event.getType());
                        subJson.put("time", event.getTreatmentTime());
                        subJson.put("name", "Advil");
                        subJson.put("patient", event.getPatient());
                        subJson.put("treatment", event.getTreatment());
                        subJson.put("procedureMedicine", event.getProcedureMedicine());
                    } catch (JSONException e) {}
                    eventsArray.put(subJson);
                });
            }
            jsonInfo.put("events", eventsArray);
        } catch (JSONException e1) {}
        return jsonInfo.toString();
    }
}
