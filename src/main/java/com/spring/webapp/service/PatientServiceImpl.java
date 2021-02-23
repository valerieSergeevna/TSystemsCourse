package com.spring.webapp.service;

import com.spring.exception.ClientException;
import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.jms.JmsProducer;
import com.spring.utils.TimeParser;
import com.spring.webapp.PatientStatus;
import com.spring.webapp.TreatmentType;
import com.spring.webapp.dao.*;
import com.spring.webapp.dto.*;
import com.spring.webapp.entity.*;
import com.spring.webapp.entity.securityEntity.Role;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class
PatientServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    private PatientDAOImpl patientDAO;

    private TreatmentDAOImpl treatmentDAO;

    private BinTreatmentsDAOImpl binTreatmentsDAO;

    private ProcedureMedicineDAOImpl procedureMedicineDAO;

    private DoctorDAOImpl doctorDAO;

    private TreatmentEventDAOImpl treatmentEventDAO;

    private TreatmentServiceImpl treatmentService;

    private DoctorUserServiceImpl doctorService;

    private TreatmentEventServiceImpl treatmentEventService;

    private JmsProducer producer;

    @Autowired
    public void setPatientDAO(PatientDAOImpl patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Autowired
    public void setTreatmentDAO(TreatmentDAOImpl treatmentDAO) {
        this.treatmentDAO = treatmentDAO;
    }

    @Autowired
    public void setProcedureMedicineDAO(ProcedureMedicineDAOImpl procedureMedicineDAO) {
        this.procedureMedicineDAO = procedureMedicineDAO;
    }

    @Autowired
    public void setDoctorDAO(DoctorDAOImpl doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Autowired
    public void setTreatmentEventDAO(TreatmentEventDAOImpl treatmentEventDAO) {
        this.treatmentEventDAO = treatmentEventDAO;
    }

    @Autowired
    public void setTreatmentService(TreatmentServiceImpl treatmentService) {
        this.treatmentService = treatmentService;
    }

    @Autowired
    public void setDoctorService(DoctorUserServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Autowired
    public void setBinTreatmentsDAO(BinTreatmentsDAOImpl binTreatmentsDAO) {
        this.binTreatmentsDAO = binTreatmentsDAO;
    }


    @Autowired
    public void setTreatmentEventService(TreatmentEventServiceImpl treatmentEventService) {
        this.treatmentEventService = treatmentEventService;
    }

    @Autowired
    public void setProducer(JmsProducer producer) {
        this.producer = producer;
    }

    @Transactional
    public List<PatientDTOImpl> getAll() throws DataBaseException {
        try {
            List<Patient> patientsList = patientDAO.getAll();
            return toPatientDTOList(patientsList);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'getAll' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public List<PatientDTOImpl> getAllByDoctorUserName(String name) throws DataBaseException, ServerException {
        try {
            Doctor doctor = doctorDAO.getByUserName(name);
            List<Patient> patientsList = patientDAO.getAllByDoctorId(doctor.getId());
            return toPatientDTOList(patientsList);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'getAllByDoctorUserName' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        } catch (NullPointerException ex) {
            logger.error("[!PatientServiceImpl 'getAllByDoctorUserName' method:" + "Can't find doctor with " + name + " username" + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new ServerException("Can't find doctor with " + name + " username");
        }
    }

    @Transactional
    public List<BinTreatmentDTOImpl> getAllBinTreatmentsById(int id) throws DataBaseException, ServerException {
        try {
            List<BinTreatment> binList = binTreatmentsDAO.getByPatientId(id);
            return treatmentService.toBinTreatmentDTOList(binList);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'getAllBinTreatmentsById' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        } catch (NullPointerException ex) {
            logger.error("[!PatientServiceImpl 'getAllBinTreatmentsById' method:");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new ServerException("");
        }
    }

    @Transactional
    public List<PatientDTOImpl> getBySurname(String surname) throws DataBaseException {
        try {
            List<Patient> patientsList = patientDAO.getBySurname(surname + "%");
            return toPatientDTOList(patientsList);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'getBySurname' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public PatientDTOImpl save(PatientDTOImpl patientDTO) throws DataBaseException {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        try {
            return toPatientDTO(patientDAO.save(patient));
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'save' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }

    }

    @Transactional
    public PatientDTOImpl update(PatientDTOImpl patientDTO) throws DataBaseException {
        try {
            patientDTO.setDoctor(doctorDAO.get(patientDTO.getDoctor().getId()));
            Patient patient = toPatient(patientDTO);
            return toPatientDTO(patientDAO.update(patient));
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'update' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public PatientDTOImpl eraseDoctor(PatientDTOImpl patientDTO) throws DataBaseException {
        try {
            patientDTO.setDoctor(null);
            Patient patient = toPatient(patientDTO);
            return toPatientDTO(patientDAO.update(patient));
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'update' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    //  @Transactional
    public void delete(int id) throws DataBaseException {
        try {
            patientDAO.delete(id);
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'delete' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    // @Transactional
    public void deleteTreatments(int id) throws DataBaseException {
        try {
            List<Treatment> treatmentList = patientDAO.get(id).getTreatments();
            for (Treatment treatment : treatmentList) {
                Treatment binTreatment = new Treatment(treatment.getType(), treatment.getTimePattern(),
                        treatment.getDose(), treatment.getStartDate(), treatment.getEndDate());
                //     binTreatment.setTreatmentId(treatment.getTreatmentId());
                binTreatment.setProcedureMedicine(treatment.getProcedureMedicine());
                binTreatment.setPatient(treatment.getPatient());
                binTreatmentsDAO.save(treatmentService.toBinTreatment(binTreatment));
                treatmentDAO.delete(treatment.getTreatmentId());
            }
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'deleteTreatments' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }

    @Transactional
    public PatientDTOImpl get(int id) throws DataBaseException {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        try {
            Patient patient = patientDAO.get(id);
            // BeanUtils.copyProperties(patient, patientDTO);
            patientDTO = toPatientDTO(patient);
            patientDTO.setTreatments(treatmentDAO.toTreatmentDTOList(patient.getTreatments()));
            return patientDTO;
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'get' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    public void saveOrUpdateTreatments(List<TreatmentDTOImpl> treatments, PatientDTOImpl patientDTO, DoctorDTOImpl doctor) throws DataBaseException {

        try {
            patientDTO.getTreatments().clear();
            Patient patient = patientDAO.get(patientDTO.getId());

            if (patient != null) {
                patient.getTreatments().clear();
            }
            patient = toPatient(patientDTO);

            List<Treatment> treatmentList = treatments.stream()
                    .map(treatment ->
                    {
                        Treatment newTreatment = new Treatment(treatment.getType(), treatment.getTimePattern(), treatment.getDose(),
                                TimeParser.fromLocalDateToLocalDateTime(treatment.getStartDate()),
                                TimeParser.fromLocalDateToLocalDateTime(treatment.getEndDate()));
                        newTreatment.setTreatmentId(treatment.getTreatmentId());
                        newTreatment.setStartDate(TimeParser.fromLocalDateToLocalDateTime(treatment.getStartDate()));
                        newTreatment.setEndDate(TimeParser.fromLocalDateToLocalDateTime(treatment.getEndDate()));
                        //in TreatmentService duplicated code
                        int procedureMedicineID = procedureMedicineDAO.getIdByName(treatment.getTypeName());
                        ProcedureMedicine procedureMedicine;
                        if (procedureMedicineID > 0) {
                            procedureMedicine = procedureMedicineDAO.get(procedureMedicineID);
                        } else {
                            procedureMedicine = new ProcedureMedicine(treatment.getTypeName(), treatment.getType());
                            procedureMedicineDAO.save(procedureMedicine);
                        }
                        newTreatment.setProcedureMedicine(procedureMedicine);

                        return newTreatment;
                    })
                    .collect(Collectors.toList());

            patient.setDoctor(doctorDAO.get(doctor.getId()));
            if (patientDAO.get(patientDTO.getId()) == null) {
                patientDAO.save(patient);
            } else {
                patientDAO.update(patient);
            }
            patientDTO.setId(patient.getId());

            for (Treatment treatment : treatmentList) {
                treatment.setPatient(patient);
                //       int oldPattern = treatmentDAO.get(treatment.getTreatmentId()).getTimePattern();
                if (treatmentDAO.get(treatment.getTreatmentId()) == null) {
                    treatmentDAO.save(treatment);
                    treatmentEventDAO.createTimeTable(treatment);
                } else {
                    treatmentEventDAO.createTimeTable(treatment);
                    treatmentDAO.update(treatment);
                }
            }

            patient.setTreatments(treatmentList);

            patientDTO.setTreatments(treatmentDAO.toTreatmentDTOList(treatmentList));
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'saveOrUpdateTreatments' method:" + ex.getMessage() + "!]");
            throw new DataBaseException(ex.getMessage());
        }
    }

    //main methods for controller

    public PatientDTOImpl createNewPatient() {
        PatientDTOImpl patientDTO = new PatientDTOImpl();
        List<TreatmentDTOImpl> treatmentDTO = new ArrayList<>();
        patientDTO.setTreatments(treatmentDTO);
        return patientDTO;
    }

    @Transactional
    public void saveTreatmentInfo(PatientDTOImpl patientDTO, HttpServletRequest request, Authentication authentication)
            throws DataBaseException, ClientException, ServerException {
        List<TreatmentDTOImpl> treatmentDTOList = new ArrayList<>();
        String[] itemValues = request.getParameterValues("treatment");
        String[] typeValues = request.getParameterValues("treatmentType");
        String[] typeNameValues = request.getParameterValues("treatmentName");
        String[] patternValues = request.getParameterValues("treatmentPattern");
        String[] doseValues = request.getParameterValues("treatmentDose");
        String[] startDate = request.getParameterValues("startDate");
        String[] endDate = request.getParameterValues("endDate");


        try {
            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
            String role = "";
            for (int i = 0; i < roles.size(); i++) {
                role = ((Role) roles.toArray()[i]).getAuthority() + "";
                break;
            }
            if (role.equals("ROLE_ADMIN")) {
                if (request.getParameter("doctorId") == null || request.getParameter("doctorId").length() == 0) {
                    throw new IllegalArgumentException();
                }

                DoctorDTOImpl checkDoc = doctorService.get(Integer.parseInt(request.getParameter("doctorId")));
                if (checkDoc == null) {
                    throw new IllegalArgumentException();
                }
            }

            int treatmentIdCount = 0;
            if (itemValues != null) {
                for (int i = 0; i < itemValues.length; i++) {
                    TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl(Integer.parseInt(itemValues[i]),
                            TreatmentType.valueOf(typeValues[i]), Integer.parseInt(patternValues[i]),
                            typeValues[i].equals("medicine") ? Double.parseDouble(doseValues[i]) : 1);
                    treatmentDTO.setTypeName(typeNameValues[i]);
                    treatmentDTO.setStartDate(TimeParser.parseToLocalDate(startDate[i]));
                    treatmentDTO.setEndDate(TimeParser.parseToLocalDate(endDate[i]));
                    treatmentDTOList.add(treatmentDTO);
                }
                treatmentIdCount = itemValues.length;
            }

            if (typeValues != null) {
                if (typeValues.length > treatmentIdCount) {
                    for (int i = treatmentIdCount; i < typeValues.length; i++) {
                        TreatmentDTOImpl treatmentDTO = new TreatmentDTOImpl();
                        //DUPLICATE CODE^
                        treatmentDTO.setType(TreatmentType.valueOf(typeValues[i]));
                        treatmentDTO.setTypeName(typeNameValues[i]);
                        treatmentDTO.setTimePattern(Integer.parseInt(patternValues[i]));
                        treatmentDTO.setDose(typeValues[i].equals("medicine") ? Double.parseDouble(doseValues[i]) : 1);
                        treatmentDTO.setStartDate(TimeParser.parseToLocalDate(startDate[i]));
                        treatmentDTO.setEndDate(TimeParser.parseToLocalDate(endDate[i]));
                        treatmentDTOList.add(treatmentDTO);
                    }
                }
            }
            if (patientDTO.getDoctor() == null) {
                patientDTO.setDoctor(doctorService.toDoctor(doctorService.get(Integer.parseInt(request.getParameter("doctorId")))));
                update(patientDTO);
            }
            if (patientDTO.getStatus().equals(PatientStatus.DISCHARGED.toString())) {
                update(patientDTO);
                for (TreatmentDTOImpl treatmentDTO : treatmentDTOList) {
                    treatmentService.delete(treatmentDTO.getTreatmentId());
                }
            } else {
                List<TreatmentDTOImpl> updateTreatmentList;
                if (patientDTO.getId() > 0) {
                    updateTreatmentList = geTreatmentsToUpdateOrAdd(treatmentDTOList,
                            treatmentService.toTreatmentDTOList(patientDAO.get(patientDTO.getId()).getTreatments()));
//                    patientDAO.getTreatments(patientDTO.getId()));
                    if (updateTreatmentList == null || updateTreatmentList.size() == 0) {
                        return;
                    }
                } else {
                    updateTreatmentList = treatmentDTOList;
                }

                String doctorName = authentication.getName();

                if (role.equals("ROLE_ADMIN")) {
                    doctorName = doctorService.get(Integer.parseInt(request.getParameter("doctorId"))).getUsername();
                }

                patientDTO.setStatus(PatientStatus.IN_PROCESS.toString());
                saveOrUpdateTreatments(updateTreatmentList, patientDTO, doctorService.getByUserName(doctorName));
            }

            producer.send("UPDATE");

        } catch (NumberFormatException ex) {
            throw new ClientException("incorrect input, please, double check your inputs");
        } catch (IllegalArgumentException e) {
            throw new ClientException("doctor id must be not null, please, fix it");
        } catch (DataBaseException e) {
            throw new ClientException("doctor with this id doesn't exist, please, double check it");
        }
    }


    private List<TreatmentDTOImpl> geTreatmentsToUpdateOrAdd(List<TreatmentDTOImpl> treatmentDTOList, List<TreatmentDTOImpl> treatments) {
        if (treatments == null) return treatmentDTOList;
        if (treatmentDTOList == null) return null;
        List<TreatmentDTOImpl> updateTreatmentDTOList = new ArrayList<>();
        for (TreatmentDTOImpl newTreatment : treatmentDTOList) {
            if (!isTreatmentExist(newTreatment, treatments)) {
                updateTreatmentDTOList.add(newTreatment);
            }
        }
        return updateTreatmentDTOList;
    }

    private boolean isTreatmentExist(TreatmentDTOImpl newTreatmentDTO, List<TreatmentDTOImpl> treatments) {
        for (TreatmentDTOImpl treatmentDTO : treatments) {
            if (procedureMedicineDAO.getIdByName(newTreatmentDTO.getTypeName()) == -1)
                return false;
            treatmentDTO.setTypeName(procedureMedicineDAO
                    .get(procedureMedicineDAO.getIdByName(newTreatmentDTO.getTypeName())).getName());
            if (treatmentDTO.equals(newTreatmentDTO)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void deletePatient(int id) throws DataBaseException {
        List<TreatmentEventDTOImpl> treatmentEventDTOList = treatmentEventService.getByPatient(id);
        for (TreatmentEventDTOImpl treatmentEventDTO : treatmentEventDTOList) {
            treatmentEventService.delete(treatmentEventDTO.getId());
        }
        deleteTreatmentsWithPatient(id);
        delete(id);
    }

    public void deleteTreatmentsWithPatient(int id) throws DataBaseException {
        try {
            List<Treatment> treatmentList = patientDAO.get(id).getTreatments();
            List<BinTreatment> binTreatmentList = binTreatmentsDAO.getByPatientId(id);
            for (BinTreatment binTreatment : binTreatmentList) {
                binTreatmentsDAO.delete(binTreatment.getTreatmentId());
            }
            for (Treatment treatment : treatmentList) {
                treatmentDAO.delete(treatment.getTreatmentId());
            }
        } catch (HibernateException ex) {
            logger.error("[!PatientServiceImpl 'deleteTreatments' method:" + ex.getMessage() + "!]");
            logger.error("STACK TRACE: " + Arrays.toString(ex.getStackTrace()));
            throw new DataBaseException(ex.getMessage());
        }
    }


    //convector's method from-to

    public List<PatientDTOImpl> toPatientDTOList(List<Patient> patientList) {
        return patientList.stream()
                .map(this::toPatientDTO)
                .collect(Collectors.toList());
    }

    public PatientDTOImpl toPatientDTO(Patient patient) {
        PatientDTOImpl patientDTO = new PatientDTOImpl(patient.getId(), patient.getName(),
                patient.getSurname(), patient.getAges(), patient.getDisease(), patient.getStatus().getTitle(),
                treatmentDAO.toTreatmentDTOList(patient.getTreatments()), patient.getDoctor());
        patientDTO.setInsuranceNumber(patient.getInsuranceNumber());
        return patientDTO;
    }

    public Patient toPatient(PatientDTOImpl patientDTO) {
        Patient patient = new Patient(patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAges(), patientDTO.getInsuranceNumber(),
                patientDTO.getDisease(), PatientStatus.fromTitle(patientDTO.getStatus()));
        if (patientDTO.getId() > 0) {
            patient.setId(patientDTO.getId());
        }
        patient.setDoctor(patientDTO.getDoctor());
        return patient;
    }

}
