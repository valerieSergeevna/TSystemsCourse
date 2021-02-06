package com.spring;


import com.spring.jms.JmsProducer;
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
        /*String jsonText =  new Gson().toJson(listOfObjects);
         Type listType = new TypeToken<List<Model>>() {}.getType();

         List<Model> myModelList = new Gson().fromJson(jsonText , listType);*/
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootRehaApplication.class);
    }
}
