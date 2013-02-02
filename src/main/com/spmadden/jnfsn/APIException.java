/*
 * APIException.java
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

/**
 * @author sean
 *
 */
public class APIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -627147979918611521L;

	/**
	 * @param arg0
	 * @param arg1
	 */
	public APIException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public APIException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public APIException(Throwable arg0) {
		super(arg0);
	}

}
