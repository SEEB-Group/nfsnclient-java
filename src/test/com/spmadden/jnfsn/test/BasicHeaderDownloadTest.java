/*
 * BasicHeaderDownloadTest.java
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
package com.spmadden.jnfsn.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.spmadden.jnfsn.net.HTTPConnection;
import com.spmadden.jnfsn.net.NFSNHeaderGenerator;

/**
 * @author sean
 *
 */
public class BasicHeaderDownloadTest {

	private static final Logger LOG = Logger.getLogger(BasicHeaderDownloadTest.class);
	
	/**
	 * To use this test - create a file called 'testprops.properties'
	 * with the keys 'apiuser' and 'apikey' - filling out these fields
	 * with appropriate values.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		final InputStream is = BasicHeaderDownloadTest.class.getResourceAsStream("/testprops.properties");
		final Properties p = new Properties();
		p.load(is);
		p.list(System.out);
		
		final String apiuser = p.getProperty("apiuser");
		final String apikey = p.getProperty("apikey");
		final String account = p.getProperty("account");
		final String url = "/account/"+account+"/friendlyName";
		final String fullurl = "https://api.nearlyfreespeech.net" + url;
		
		final NFSNHeaderGenerator gen = new NFSNHeaderGenerator(apiuser, apikey);
		HTTPConnection conn = new HTTPConnection(fullurl, gen);
		
		final InputStream stream = conn.getDataStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		LOG.debug("Received: ");
		
		String line;
		final StringBuffer buf = new StringBuffer();
		while((line = reader.readLine()) != null){
			buf.append(line);
		}
		LOG.debug(buf.toString());
		System.out.println(buf.toString());
	}

}
