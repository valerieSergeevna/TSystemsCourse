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
import com.spring.webapp.service.AdminServiceImpl;
import com.spring.webapp.service.DoctorUserServiceImpl;
import com.spring.webapp.service.NurseUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
//@ComponentScan(basePackages = "com.spring.webapp")
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;


    @Autowired
    UserDAO userRepository;
    @Autowired
    RoleDAO roleRepository;
    @Autowired
    DoctorDAOImpl doctorDAO;
    @Autowired
    DoctorUserServiceImpl doctorService;

    @Autowired
    NurseDAOImpl nurseDAO;
    @Autowired
    NurseUserServiceImpl nurseService;

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

  /*  @Autowired(required = true)
    public void setUserRepository(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired(required = true)
    public void setRoleRepository(RoleDAO roleRepository) {
        this.roleRepository = roleRepository;
    }*/

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

    public boolean saveUser(User user, HttpServletRequest request) throws DataBaseException {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("position");
        String role = request.getParameter("role");
        String userName = user.getUsername();
        int roleId = 0;

        switch (role) {
            case "doctor":
                role = "ROLE_DOCTOR";
                doctorService.save((DoctorDTOImpl)setFields(new DoctorDTOImpl(),
                        name,surname,position,userName));
                roleId = 1;
                break;
            case "nurse":
                role = "ROLE_NURSE";
                nurseService.save((NurseDTOImpl) setFields(new NurseDTOImpl(),
                        name,surname,position,userName));
                roleId = 3;
                break;
            case "admin":
                role = "ROLE_ADMIN";
                adminService.save((AdminDTOImpl) setFields(new AdminDTOImpl(),
                        name,surname,position,userName));
                roleId = 2;
                break;
            default:break;
        }

        user.setRoles(Collections.singleton(new Role(Integer.toUnsignedLong(roleId), role)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    private String getRole(Set<Role> roles) {
        for (Role role : roles) {
            return role.getAuthority();
        }
        return null;
    }

    private AllDTOUser setFields(AllDTOUser allDTOUser, String name, String surname,
                                 String position, String username){
        allDTOUser.setName(name);
        allDTOUser.setSurname(surname);
        allDTOUser.setPosition(position);
        allDTOUser.setUsername(username);
        return allDTOUser;
    }
}