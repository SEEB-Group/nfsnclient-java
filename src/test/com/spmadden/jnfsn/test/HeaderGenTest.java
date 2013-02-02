/*
 * HeaderGenTest.java
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

import org.junit.After;
import org.junit.Test;

import com.spmadden.jnfsn.NFSNHeaderGenerator;

/**
 * @author sean
 *
 */
public class HeaderGenTest {

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void firstTest() {
		NFSNHeaderGenerator gen = 
				new NFSNHeaderGenerator("testuser", "p3kxmRKf9dk3l6ls");
	
		String result = gen.generateHeader("/site/example/getInfo");
		String exp = "X-NFSN-Authentication: testuser;1012121212;dkwo28Sile4jdXkw;0fa8932e122d56e2f6d1550f9aab39c4aef8bfc4";
		
		System.out.println(result);
		System.out.println(exp);
		
	}

}
