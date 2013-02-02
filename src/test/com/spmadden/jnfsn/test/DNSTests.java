/*
 * DNSTests.java
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.spmadden.jnfsn.NFSNAPIManager;
import com.spmadden.jnfsn.NFSNDns;

/**
 * @author sean
 *
 */
public class DNSTests {

	
	public static void main(final String[] args) throws IOException{
		final InputStream is = DNSTests.class.getResourceAsStream("/testprops.properties");
		final Properties props = new Properties();
		props.load(is);
		
		final String login = props.getProperty("apiuser");
		final String apiKey = props.getProperty("apikey");
		final String domain = props.getProperty("domain");
		
		final NFSNAPIManager api = new NFSNAPIManager(login, apiKey);
		final NFSNDns dns = api.getDNS(domain);
		
		System.out.println(dns);
	}
}
