package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dto.AbstractDTOUser;
import com.spring.webapp.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public class AllUserService {
    private AbstractUserService abstractUserService;

     public AllUserService(AbstractUserService abstractUserService){
         this.abstractUserService = abstractUserService;
     }

    public  List<AbstractDTOUser> getAll() throws DataBaseException{
        return abstractUserService.getAll();
    }

    public  AbstractDTOUser save(AbstractDTOUser userDTO) throws DataBaseException{
        return (AbstractDTOUser) abstractUserService.save(userDTO);
    }

    public  void delete(int id) throws DataBaseException{
        abstractUserService.delete(id);
    }

    public  AbstractDTOUser get(int id) throws DataBaseException{
        return (AbstractDTOUser) abstractUserService.get(id);
    }

    public  AbstractDTOUser getByUserName(String name) throws DataBaseException, ServerException{
         return (AbstractDTOUser)abstractUserService.getByUserName(name);
    }

    public  AbstractDTOUser update(AbstractDTOUser UserDTO) throws DataBaseException{
        return (AbstractDTOUser)abstractUserService.update(UserDTO);
    }
}
