/*
 * NFSNAccount.java
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

import java.io.IOException;

import org.apache.log4j.Logger;

import com.spmadden.jnfsn.net.HTTPConnection;
import com.spmadden.jnfsn.net.NFSNHeaderGenerator;
import com.spmadden.jnfsn.net.NetUtils;

/**
 * @author sean
 *
 */
public class NFSNAccount {

	private static final Logger LOG = Logger.getLogger(NFSNAccount.class);
	
	private final NFSNHeaderGenerator generator;
	private final String account;

	NFSNAccount(
			final String account,
			final NFSNHeaderGenerator generator){
		this.account = account;
		this.generator = generator;
	}
	
	public double getBalance(){
		return getDouble("balance");
	}
	
	public double getBalanceCash(){
		return getDouble("balanceCash");
	}
	
	public double getBalanceHigh(){
		return getDouble("balanceHigh");
	}
	
	public double getBalanceCredit(){
		return getDouble("balanceCredit");
	}
	
	public String getFriendlyName(){
		return getString("friendlyName");
	}
	
	public String getStatus(){
		return getString("status");
	}
	
	public String getSites(){
		return getString("sites");
	}
	
	protected String getURL(final String method){
		final String url = NetUtils.getAPIUrl("account/" + account + "/");
		return url + method;
	}
	
	protected double getDouble(final String name) throws APIException {
		final String value = getString(name);
		return Double.valueOf(value);
	}

	protected String getString(final String name) throws APIException {
		try {
			final String url = getURL(name);
			final HTTPConnection conn = new HTTPConnection(url, generator);
			final String value = NetUtils.consumeAll(conn.getDataStream());
			LOG.debug("GetString: " + name + " = " + value);
			return value;
		} catch (IOException e) {
			LOG.error(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NFSNAccount [account=");
		builder.append(account);
		builder.append(", getBalance()=");
		builder.append(getBalance());
		builder.append(", getBalanceCash()=");
		builder.append(getBalanceCash());
		builder.append(", getBalanceHigh()=");
		builder.append(getBalanceHigh());
		builder.append(", getBalanceCredit()=");
		builder.append(getBalanceCredit());
		builder.append(", getFriendlyName()=");
		builder.append(getFriendlyName());
		builder.append(", getStatus()=");
		builder.append(getStatus());
		builder.append(", getSites()=");
		builder.append(getSites());
		builder.append("]");
		return builder.toString();
	}
	
}
