package com.spring.webapp.service.securityService;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dao.DoctorDAOImpl;
import com.spring.webapp.dao.NurseDAOImpl;
import com.spring.webapp.dao.securityDAO.RoleDAO;
import com.spring.webapp.dao.securityDAO.UserDAO;
import com.spring.webapp.dto.AbstractDTOUser;
import com.spring.webapp.dto.UserDTO;
import com.spring.webapp.entity.securityEntity.Role;
import com.spring.webapp.entity.securityEntity.User;
import com.spring.webapp.service.AdminServiceImpl;
import com.spring.webapp.service.DoctorServiceImpl;
import com.spring.webapp.service.NurseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    DoctorServiceImpl doctorService;

    @Autowired
    NurseDAOImpl nurseDAO;
    @Autowired
    NurseServiceImpl nurseService;

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

    public Map<User, AbstractDTOUser> allUsersWithInfo() throws ServerException, DataBaseException {

        List<User> users = userRepository.findAll();
        Map<User, AbstractDTOUser> userDTOMap = new HashMap<>();

        for (User user : users) {
            AbstractDTOUser userDTO;
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

    public boolean saveUser(User user, String role) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, role)));
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
}