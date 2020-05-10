package com.master4.validators;

import com.master4.entities.Article;
import com.master4.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueArticleNameClass implements ConstraintValidator<UniqueArticleName, String> {
   @Autowired
   private ArticleService articleService;
   public void initialize(UniqueArticleName constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
     try {
        return articleService.findArticleWithName(obj)==null;
     }catch (Exception e ){
        return true;
     }
   }
}
