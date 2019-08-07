package com.asiapay.controller;


import com.asiapay.model.User;
import com.asiapay.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController()
public class UserController {  
	private static Logger log=LoggerFactory.getLogger(UserController.class);
	 @Resource  
	 private IUserService userService;
    
    /*@RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getStudentList() {
        List<User> students = userService.getStudentList();
        return students;
    }*/

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)

    public ResponseEntity<User> queryOneStudent(@PathVariable("id") int id) {
        User User = userService.getUserById(id);
        return new ResponseEntity<User>(User,HttpStatus.OK);
    }

    /*@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteStudent(@PathVariable("id") long id) {
        long showId = userService.deleteStudent(id);
        return new ResponseEntity<Long>(showId,HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    public ResponseEntity<User> addStudent(@PathVariable("id") long id) {
        User User = new User(id,"rex", "male");
        User = userService.addStudent(User);
        return new ResponseEntity<User>(User,HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> changeStudent(@PathVariable("id") long id) {
        User User = new User(id,"leona", "female");
        User = userService.changeStudent(User);
        return new ResponseEntity<User>(User,HttpStatus.OK);
    }*/
}  