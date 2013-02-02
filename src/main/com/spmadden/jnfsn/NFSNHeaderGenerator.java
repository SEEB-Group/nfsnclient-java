/*
 * NFSNHeaderGenerator.java
 * 
 * Copyright (C) 2013 Sean P Madden
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * If you would like to license this code under the GNU LGPL, please
 * see http://www.seanmadden.net/licensing for details.
 */
package com.spmadden.jnfsn;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.DatatypeConverter;

/**
 * Generates the X-NFSN-Authentication header required for all requests
 * 
 * @author sean
 * 
 */
public class NFSNHeaderGenerator {

	private static final Logger LOG = Logger.getLogger("HeaderGen");
	
	/**
	 * The API Key for this application
	 */
	private final String apiKey;

	/**
	 * Username for the NFSN servers.
	 */
	private final String login;

	/**
	 * Constructor.
	 * 
	 * @param login
	 *            - The username you use to log into NFSN
	 * @param apiKey
	 *            - The API key you requested from NFSN - tied to login.
	 */
	public NFSNHeaderGenerator(final String login, final String apiKey) {
		this.apiKey = apiKey;
		this.login = login;
	}

	/**
	 * Generates a NFSN header with a body.
	 * 
	 * @param requestURI
	 *            The API call we're requesting
	 * @param body
	 *            - the body of the call.
	 * @return the body of the header (no X-NFSN-Authentication block)
	 */
	public String generateHeaderBody(final String requestURI, final String body) {
		final long timestamp = System.currentTimeMillis() / 1000;
		final Random rand = new Random();
		final byte[] saltBytes = new byte[512];
		rand.nextBytes(saltBytes);

		String salt = DatatypeConverter.printBase64Binary(saltBytes);
		salt = salt.replace("+", "");
		salt = salt.replace("/", "");
		salt = salt.substring(0, 16);

		final StringBuffer hashBuf = new StringBuffer(128);
		hashBuf.append(login).append(';');
		hashBuf.append(timestamp).append(';');
		hashBuf.append(salt).append(';');
		hashBuf.append(apiKey).append(';');
		hashBuf.append(requestURI).append(';');
		hashBuf.append(getSHA1Hash(body));

		final StringBuffer authBuf = new StringBuffer(128);
		authBuf.append(login).append(';');
		authBuf.append(timestamp).append(';');
		authBuf.append(salt).append(';');
		authBuf.append(getSHA1Hash(hashBuf.toString()));

		return authBuf.toString();
	}

	/**
	 * Generates a NFSN header with a blank (empty) body.
	 * 
	 * @param requestURI
	 *            URI of the API we're requesting.
	 * @return the body of the header (no X-NFSN-Authentication block)
	 */
	public String generateHeaderBody(final String requestURI) {
		return generateHeaderBody(requestURI, "");
	}

	/**
	 * Generates the full HTTP 1.1 header.
	 * @param requestURI - URI of the API we're requesting.
	 * @param body the body of the header.
	 * @return The full HTTP 1.1 header without trailing '\r\n'
	 */
	public String generateHeader(
			final String requestURI, 
			final String body){
		final StringBuffer buf = new StringBuffer(128);
		buf.append("X-NFSN-Authentication: ");
		buf.append(generateHeaderBody(requestURI, body));
		
		return buf.toString();
	}
	
	/**
	 * Generates the full HTTP 1.1 header with an empty body.
	 * @param reqURI the API URI we're requesting
	 * @return the full HTTP 1.1 header without trailing '\r\n'
	 */
	public String generateHeader(final String reqURI){
		return generateHeader(reqURI, "");
	}
	
	/**
	 * Generates the SHA-1 hash of in.
	 * 
	 * @param shaStr
	 * @return Hex representation of the SHA-1 in a string.
	 */
	protected String getSHA1Hash(final String shaStr) {
		try {
			final MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			final byte[] shaBytes = sha1.digest(shaStr.getBytes());

			return DatatypeConverter.printHexBinary(shaBytes).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			LOG.log(Level.SEVERE, "Unable to find digest instance", e);
			return null;
		}
	}
}
