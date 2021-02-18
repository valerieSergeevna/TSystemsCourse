package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;
import com.spring.webapp.dto.AllDTOUser;

import java.util.List;


public class AllUserService {
    private AbstractUserService abstractUserService;

     public AllUserService(AbstractUserService abstractUserService){
         this.abstractUserService = abstractUserService;
     }

    public  List<AllDTOUser> getAll() throws DataBaseException{
        return abstractUserService.getAll();
    }

    public AllDTOUser save(AllDTOUser userDTO) throws DataBaseException{
        return (AllDTOUser) abstractUserService.save(userDTO);
    }

    public  void delete(int id) throws DataBaseException{
        abstractUserService.delete(id);
    }

    public AllDTOUser get(int id) throws DataBaseException{
        return (AllDTOUser) abstractUserService.get(id);
    }

    public AllDTOUser getByUserName(String name) throws DataBaseException, ServerException{
         return (AllDTOUser)abstractUserService.getByUserName(name);
    }

    public AllDTOUser update(AllDTOUser UserDTO) throws DataBaseException{
        return (AllDTOUser)abstractUserService.update(UserDTO);
    }
}
