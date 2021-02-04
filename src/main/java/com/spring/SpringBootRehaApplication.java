package com.spring;


import com.spring.webapp.TreatmentType;
import com.spring.webapp.dto.TreatmentEventDTOImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EntityScan(basePackages = "com.spring.webapp.entity")
public class SpringBootRehaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRehaApplication.class, args);
     /*   TreatmentEventDTOImpl treatmentEventDTO = new TreatmentEventDTOImpl(1, TreatmentType.medicine,
        LocalDateTime.now(),1,"in plan");
        List<TreatmentEventDTOImpl> treatmentEventDTOList = new ArrayList<>();
        treatmentEventDTOList.add(treatmentEventDTO);
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
                        subJson.put("name", event.getProcedureMedicine().getName());
                    } catch (JSONException e) {}
                    eventsArray.put(subJson);
                });
            }
            jsonInfo.put("events", eventsArray);
        } catch (JSONException e1) {}
        producer.send(jsonInfo.toString());*/
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootRehaApplication.class);
    }
}
