/*
 * DNSResourceRecord.java
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
package com.spmadden.jnfsn.dns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author sean
 *
 */
public class DNSResourceRecord {

	private String name;
	private String type;
	private String data;
	private String ttl;
	private String scope;
	private String aux;
	
	private DNSResourceRecord(){
		// do nothing.
	}
	
	public static DNSResourceRecord fromJSONObject(JSONObject object) 
		throws JSONException{
		DNSResourceRecord rr = new DNSResourceRecord();
		
		rr.name = object.optString("name");
		rr.type = object.optString("type");
		rr.data = object.optString("data");
		rr.ttl = object.optString("ttl");
		rr.scope = object.optString("scope");
		rr.aux = object.optString("aux");
		
		return rr;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the ttl
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @return the aux
	 */
	public String getAux() {
		return aux;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aux == null) ? 0 : aux.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((ttl == null) ? 0 : ttl.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DNSResourceRecord)) {
			return false;
		}
		DNSResourceRecord other = (DNSResourceRecord) obj;
		if (aux == null) {
			if (other.aux != null) {
				return false;
			}
		} else if (!aux.equals(other.aux)) {
			return false;
		}
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (scope == null) {
			if (other.scope != null) {
				return false;
			}
		} else if (!scope.equals(other.scope)) {
			return false;
		}
		if (ttl == null) {
			if (other.ttl != null) {
				return false;
			}
		} else if (!ttl.equals(other.ttl)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DNSResourceRecord [name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", data=");
		builder.append(data);
		builder.append(", ttl=");
		builder.append(ttl);
		builder.append(", scope=");
		builder.append(scope);
		builder.append(", aux=");
		builder.append(aux);
		builder.append("]");
		return builder.toString();
	}
	
	
}
