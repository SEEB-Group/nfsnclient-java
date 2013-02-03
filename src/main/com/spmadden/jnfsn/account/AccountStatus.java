/*
 * AccountStatus.java
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
package com.spmadden.jnfsn.account;

import org.json.JSONObject;

/**
 * @author sean
 *
 */
public class AccountStatus {

	private String status;
	private String shortStatus;
	private String color;
	
	private AccountStatus(){
		// do nothing
	}
	
	public static AccountStatus fromJSONObject(JSONObject object){
		final AccountStatus status = new AccountStatus();
		
		status.status = object.optString("status");
		status.shortStatus = object.optString("short");
		status.color = object.optString("color");
		
		return status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the shortStatus
	 */
	public String getShort() {
		return shortStatus;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountStatus [status=");
		builder.append(status);
		builder.append(", shortStatus=");
		builder.append(shortStatus);
		builder.append(", color=");
		builder.append(color);
		builder.append("]");
		return builder.toString();
	}
	
	
}
