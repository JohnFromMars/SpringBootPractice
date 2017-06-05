package com.marsbase.springboot.util;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * It is collection of useful string formatting methods
 * 
 * @author user
 *
 */
@Component
public class StringFormatUtil {

	@Autowired
	private PolicyFactory policyFactory;

	/**
	 * this method aim to sure that interest text is form 1st upper case and the
	 * rest is lower case and remove script and harmful html tag. EX.
	 * tennis->Tennis, joHN->John, have fun->Have fun
	 * 
	 * @param interestText
	 * @return
	 */
	public String getUpperAndLowerCase(String text) {
		String cleanText = sanitize(text);
		return cleanText.substring(0, 1).toUpperCase() + cleanText.substring(1).toLowerCase();
	}
	
	/**
	 * this method is aim to remove the harmful tag like script
	 * 
	 * @param text
	 * @return
	 */
	public String sanitize(String text){
		return policyFactory.sanitize(text.trim());
	}

}
