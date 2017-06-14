package com.marsbase.springboot.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.marsbase.springboot.model.entity.SiteUser;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SiteUser> {

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
		// TODO Auto-generated method stubO

	}

	@Override
	public boolean isValid(SiteUser user, ConstraintValidatorContext context) {

		String plainPassword = user.getPlainPassword();
		String repeatPassword = user.getRepeatPassword();

		if (plainPassword == null || plainPassword.length() == 0) {
			return true;
		}

		if (plainPassword == null || !plainPassword.equals(repeatPassword)) {
			return false;
		}

		return true;
	}

}
