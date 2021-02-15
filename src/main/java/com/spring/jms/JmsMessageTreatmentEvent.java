package com.spring.jms;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.webapp.TreatmentType;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import com.spring.webapp.entity.Patient;
import com.spring.webapp.entity.ProcedureMedicine;
import com.spring.webapp.entity.Treatment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
//
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = JmsMessageTreatmentEvent.class)
//public class JmsMessageTreatmentEvent {
//    private List<TreatmentEventDTOImpl> treatmentEventDTOList;
//
//    public JmsMessageTreatmentEvent() {
//    }
//
//    public JmsMessageTreatmentEvent(List<TreatmentEventDTOImpl> treatmentEventDTOList) {
//        this.treatmentEventDTOList = treatmentEventDTOList;
//    }
//
//    public List<TreatmentEventDTOImpl> getTreatmentEventDTOList() {
//        return treatmentEventDTOList;
//    }
//
//    public void setTreatmentEventDTOList(List<TreatmentEventDTOImpl> treatmentEventDTOList) {
//        this.treatmentEventDTOList = treatmentEventDTOList;
//    }
//
//    @Override
//    public String toString(){
//        JSONObject jsonInfo = new JSONObject();
//        try {
//            JSONArray eventsArray = new JSONArray();
//            if (treatmentEventDTOList != null) {
//                treatmentEventDTOList.forEach(event -> {
//                    JSONObject subJson = new JSONObject();
//                    try {
//                        subJson.put("id", event.getId());
//                        subJson.put("dose", event.getDose());
//                        subJson.put("cancelReason", event.getCancelReason());
//                        subJson.put("status", event.getStatus());
//                        subJson.put("type", event.getType());
//                        subJson.put("time", event.getTreatmentTime());
//                        subJson.put("name", "Advil");
//                        subJson.put("patient", event.getPatient());
//                        subJson.put("treatment", event.getTreatment());
//                        subJson.put("procedureMedicine", event.getProcedureMedicine());
//                    } catch (JSONException e) {}
//                    eventsArray.put(subJson);
//                });
//            }
//            jsonInfo.put("events", eventsArray);
//        } catch (JSONException e1) {}
//        return jsonInfo.toString();
//    }
//}
public class JmsMessageTreatmentEvent {
    private TreatmentType type;
  //  private LocalDateTime treatmentTime;
    private double dose;
    private String status;
    private String cancelReason;

    private String name;

    public JmsMessageTreatmentEvent() {
    }

    public JmsMessageTreatmentEvent(TreatmentType type,
                                    double dose, String status, String cancelReason, String name) {
        this.type = type;
     //   this.treatmentTime = treatmentTime;
        this.dose = dose;
        this.status = status;
        this.cancelReason = cancelReason;
        this.name = name;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }
//
//    public LocalDateTime getTreatmentTime() {
//        return treatmentTime;
//    }
//
//    public void setTreatmentTime(LocalDateTime treatmentTime) {
//        this.treatmentTime = treatmentTime;
//    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}