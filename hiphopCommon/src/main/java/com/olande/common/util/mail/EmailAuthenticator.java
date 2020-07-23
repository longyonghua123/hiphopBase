package com.olande.common.util.mail;

import javax.mail.PasswordAuthentication;

/**
 * 
 * @author chengfr
 *
 */
public final class EmailAuthenticator extends javax.mail.Authenticator {
	private String userName;
	private String passWord;

	public EmailAuthenticator(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, passWord);
	}

}
