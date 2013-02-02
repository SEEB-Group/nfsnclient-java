/*
 * NFSNDns.java
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
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.spmadden.jnfsn.dns.DNSResourceRecord;
import com.spmadden.jnfsn.net.HTTPConnection;
import com.spmadden.jnfsn.net.HTTPConnection.RequestMethod;
import com.spmadden.jnfsn.net.NFSNHeaderGenerator;
import com.spmadden.jnfsn.net.NetUtils;

/**
 * The NFSN Dns object -
 * 
 * @author sean
 * 
 */
public class NFSNDns {

	private static final Logger LOG = Logger.getLogger(NFSNDns.class);

	public final String domain;
	public final NFSNHeaderGenerator generator;

	/**
	 * Creates a new NFSNDns object. Get me from {@link NFSNAPIManager}
	 * 
	 * @param domain
	 * @param login
	 * @param apiKey
	 */
	NFSNDns(final String domain, final NFSNHeaderGenerator generator) {
		this.domain = domain;
		this.generator = generator;
	}

	/**
	 * Gets the expire time for this domain.
	 * 
	 * @return
	 * @throws IOException
	 */
	public long getExpire() throws APIException {
		return getLong("expire");
	}

	/**
	 * Gets the TTL time for this domain.
	 * 
	 * @return
	 * @throws IOException
	 */
	public long getTTL() throws APIException {
		return getLong("minTTL");
	}

	public long getRefresh() throws APIException {
		return getLong("refresh");
	}

	public long getRetry() throws APIException {
		return getLong("retry");
	}

	public String getSerial() throws APIException {
		return getString("serial");
	}

	protected long getLong(final String name) throws APIException {
		final String value = getString(name);
		return Long.valueOf(value);
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

	public DNSResourceRecord[] getResourceRecords(
			final String name,
			final String type,
			final String data) throws APIException{
		try{
			final String url = getURL("listRRs");
			final HTTPConnection conn = new HTTPConnection(url, RequestMethod.POST, generator);
			
			if(name != null){
				conn.addFormField("name", name);
			}
			if(type != null && !"".equals(type)){
				conn.addFormField("type", type);
			}
			if(data != null && !"".equals(data)){
				conn.addFormField("data", data);
			}
			
			final String value = NetUtils.consumeAll(conn.getDataStream());
			LOG.debug("JSON: " + value);
			
			final JSONArray arr = new JSONArray(value);
			final LinkedList<DNSResourceRecord> rrs = new LinkedList<>();
			for(int i = 0; i < arr.length(); ++i){
				rrs.add(DNSResourceRecord.fromJSONObject(arr.getJSONObject(i)));
			}
			
			return rrs.toArray(new DNSResourceRecord[rrs.size()]);
		}catch(IOException e){
			LOG.error(e);
		} catch (JSONException e) {
			LOG.error(e);
		}
		return null;
	}
	
	public DNSResourceRecord[] getAllRecords() throws APIException{
		return getResourceRecords(null, null, null);
	}
	
	public DNSResourceRecord[] getRecordsByName(final String name){
		return getResourceRecords(name, null, null);
	}
	
	public DNSResourceRecord[] getRecordsByType(final String type){
		return getResourceRecords(null, type, null);
	}
	
	protected String getURL(final String method){
		final String url = NetUtils.getAPIUrl("dns/" + domain + "/");
		return url + method;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NFSNDns [domain=");
		builder.append(domain);
		builder.append(", getExpire()=");
		builder.append(getExpire());
		builder.append(", getTTL()=");
		builder.append(getTTL());
		builder.append(", getRefresh()=");
		builder.append(getRefresh());
		builder.append(", getRetry()=");
		builder.append(getRetry());
		builder.append(", getSerial()=");
		builder.append(getSerial());
		builder.append("]");
		return builder.toString();
	}

}
