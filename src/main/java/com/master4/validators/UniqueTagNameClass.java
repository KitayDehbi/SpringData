package com.master4.validators;

import com.master4.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueTagNameClass implements ConstraintValidator<UniqueTagName, String> {
   @Autowired
   TagService tagService;
   public void initialize(UniqueTagName constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {

      try {
         return tagService.getTagByTitle(obj) ==null;

      }
      catch (Exception a){
         return true;
      }
   }
}
