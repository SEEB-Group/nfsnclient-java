/*
 * URLUtils.java
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
package com.spmadden.jnfsn.net;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sean
 *
 */
public class NetUtils {

	public static String getAPIUrl(final String requestUri){
		return "https://api.nearlyfreespeech.net/" + requestUri;
	}
	
	public static String consumeAll(final InputStream is) throws IOException{
		final StringBuffer buf = new StringBuffer();
		int chr = -1;
		while((chr = is.read()) != -1){
			buf.append((char) chr);
		}
		
		return buf.toString();
		
	}
}
