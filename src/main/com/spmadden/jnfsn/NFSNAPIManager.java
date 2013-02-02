/*
 * NFSNAbstractAPI.java
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

import com.spmadden.jnfsn.net.NFSNHeaderGenerator;

/**
 * @author sean
 *
 */
public class NFSNAPIManager {

	/**
	 * The header generator - needs to be persistent as it maintains
	 * a time offset from the NFSN servers to be able to accurately
	 * generate the requests.
	 */
	private final NFSNHeaderGenerator generator;

	/**
	 * Creates a new NFSNApiManager object
	 * @param login username for NFSN
	 * @param apiKey apiKey requested from NFSN
	 */
	public NFSNAPIManager(final String login, final String apiKey){
		generator = new NFSNHeaderGenerator(login, apiKey);
	}
	
	/**
	 * Creates and returns a DNS object.
	 * @param domain
	 * @return
	 */
	public NFSNDns getDNS(final String domain){
		return new NFSNDns(domain, generator);
	}
	
	public NFSNAccount getAccount(final String account){
		return new NFSNAccount(account, generator);
	}
}
