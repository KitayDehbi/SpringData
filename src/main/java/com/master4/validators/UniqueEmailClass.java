package com.master4.validators;

import com.master4.entities.User;
import com.master4.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailClass implements ConstraintValidator<UniqueEmail, User> {
   public void initialize(UniqueEmail constraint) {
   }
   @Autowired
   private UserService userService;
   @SneakyThrows
   public boolean isValid(User obj, ConstraintValidatorContext context) {
      try {

         return userService.findByEmail(obj.getEmail())==null;
      } catch (Exception e)
      {
         return true;
      }
   }
}
