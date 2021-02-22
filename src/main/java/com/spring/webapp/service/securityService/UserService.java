package com.spring.webapp.service.securityService;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.NurseDAOImpl;
import com.spring.webapp.dao.securityDAO.RoleDAO;
import com.spring.webapp.dao.securityDAO.UserDAO;
import com.spring.webapp.dto.*;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    UserDAO userRepository;

    RoleDAO roleRepository;

    DoctorDAOImpl doctorDAO;

    DoctorUserServiceImpl doctorService;

    private MailService mailSender;

    NurseDAOImpl nurseDAO;

    NurseUserServiceImpl nurseService;

    PatientServiceImpl patientService;

    AdminServiceImpl adminService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserRepository(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleDAO roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setDoctorDAO(DoctorDAOImpl doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Autowired
    public void setDoctorService(DoctorUserServiceImpl doctorService) {
        this.doctorService = doctorService;
    }

    @Autowired
    public void setMailSender(MailService mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setNurseDAO(NurseDAOImpl nurseDAO) {
        this.nurseDAO = nurseDAO;
    }

    @Autowired
    public void setNurseService(NurseUserServiceImpl nurseService) {
        this.nurseService = nurseService;
    }

    @Autowired
    public void setPatientService(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @Autowired
    public void setAdminService(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Map<User, AllDTOUser> allUsersWithInfo() throws ServerException, DataBaseException {

        List<User> users = userRepository.findAll();
        Map<User, AllDTOUser> userDTOMap = new HashMap<>();

        for (User user : users) {
            AllDTOUser userDTO;
            switch (getRole(user.getRoles())) {
                case "ROLE_DOCTOR":
                    userDTO = doctorService.getByUserName(user.getUsername());
                    break;
                case "ROLE_NURSE":
                    userDTO = nurseService.getByUserName(user.getUsername());
                    break;
                default:
                    userDTO = adminService.getByUserName(user.getUsername());
                    break;
            }
            userDTOMap.put(user, userDTO);
        }
        return userDTOMap;
    }

    public boolean isUserExist(User user, HttpServletRequest request) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return true;
        }
        return false;
    }

    public boolean updateUser(User user, HttpServletRequest request) throws DataBaseException {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (user.equals(userFromDB))
            return true;
//        if (userRepository.findByUsername(request.getParameter("username")) != null)
//            return false;
        if (!request.getParameter("email").equals(userFromDB.getGoogleUsername())) {
            user.setGoogleUsername(request.getParameter("email"));
        }
        if (!request.getParameter("username").equals(userFromDB.getUsername())) {
            user.setUsername(request.getParameter("username"));
        }
        String role = ((Role) userFromDB.getRoles().toArray()[0]).getAuthority();
        setInfo(user, request, role);
        userRepository.save(user);
        return true;
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    public int setInfo(User user, HttpServletRequest request, String role) throws DataBaseException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("position");
        String userName = user.getUsername();
        int roleId = 0;

        switch (role) {
            case "doctor":
            case "ROLE_DOCTOR":
                role = "ROLE_DOCTOR";
                doctorService.save((DoctorDTOImpl) setFields(new DoctorDTOImpl(),
                        name, surname, position, userName));
                roleId = 1;
                break;
            case "nurse":
            case "ROLE_NURSE":
                role = "ROLE_NURSE";
                nurseService.save((NurseDTOImpl) setFields(new NurseDTOImpl(),
                        name, surname, position, userName));
                roleId = 3;
                break;
            case "admin":
            case "ROLE_ADMIN":
                role = "ROLE_ADMIN";
                adminService.save((AdminDTOImpl) setFields(new AdminDTOImpl(),
                        name, surname, position, userName));
                roleId = 2;
                break;
            default:
                break;
        }
        return roleId;
    }

    public boolean saveUser(User user, HttpServletRequest request) throws DataBaseException {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        String role = request.getParameter("role");
        if (isUserExist(user, request))
            return false;

        int roleId = setInfo(user, request, role);
        user.setRoles(Collections.singleton(new Role(Integer.toUnsignedLong(roleId), role)));
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        //check for null
        if (user.getGoogleUsername()!=null&&!user.getGoogleUsername().isEmpty()) {
            String message = "Hi!" +
                    "\nCatch your password: " + password + " \nAnd username: " + user.getUsername() +
                    "\n Now you can log in in this app:  http://localhost:8080/" + "\n Have a good day:)";
            mailSender.send(user.getGoogleUsername(), "RehaApp password and username", message);
        }
        return true;
    }

    public boolean deleteUser(Long userId) throws ServerException, DataBaseException {
        if (userRepository.findById(userId).isPresent()) {
            User user = findUserById(userId);
            String role = ((Role) user.getAuthorities().toArray()[0]).getAuthority();
            switch (role) {
                case "ROLE_DOCTOR":
                    List<PatientDTOImpl> patientList = patientService.getAllByDoctorUserName(user.getUsername());
                    for (PatientDTOImpl patientDTO : patientList) {
                        patientService.eraseDoctor(patientDTO);
                    }
                    //              doctorService.delete(doctorService.getByUserName(user.getUsername()).getId());
                    break;
                case "ROLE_NURSE":
                    nurseService.delete(nurseService.getByUserName(user.getUsername()).getId());
                    break;
                case "ROLE_ADMIN":
                    adminService.delete(adminService.getByUserName(user.getUsername()).getId());
                    break;
                default:
                    break;
            }
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

//    public List<User> usergtList(Long idMin) {
//        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
//                .setParameter("paramId", idMin).getResultList();
//    }

    private String getRole(Set<Role> roles) {
        for (Role role : roles) {
            return role.getAuthority();
        }
        return null;
    }

    private AllDTOUser setFields(AllDTOUser allDTOUser, String name, String surname,
                                 String position, String username) {
        allDTOUser.setName(name);
        allDTOUser.setSurname(surname);
        allDTOUser.setPosition(position);
        allDTOUser.setUsername(username);
        return allDTOUser;
    }
}