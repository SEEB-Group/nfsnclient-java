/*
 * SelfSignedSSLSocketFactory.java
 * 
 *    Copyright (C) 2011 Sean P Madden
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *    If you would like to license this code under the GNU LGPL, please see
 *    http://www.seanmadden.net/licensing for details.
 *
 */
package com.spmadden.jnfsn.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;

/**
 * This is here because of Java's fucked up handling of SSL Certs.
 * 
 * @author Sean P Madden
 */
public final class SelfSignedSSLSocketFactory extends SSLSocketFactory {

	/**
	 * RIT's PEM encoded CA certificate.
	 */
	private static final String RIT_CERTIFICATE = "-----BEGIN CERTIFICATE-----\nMIIHLzCCBRegAwIBAgIJALqgDOFt1vtYMA0GCSqGSIb3DQEBBQUAMIGvMQswCQYDVQQGEwJVUzERMA8GA1UECBMITmV3IFlvcmsxEjAQBgNVBAcTCVJvY2hlc3RlcjEq\nMCgGA1UEChMhUm9jaGVzdGVyIEluc3RpdHV0ZSBvZiBUZWNobm9sb2d5MQwwCgYD\nVQQLEwNJVFMxIjAgBgNVBAMTGVJJVCBDZXJ0aWZpY2F0ZSBBdXRob3JpdHkxGzAZ\nBgkqhkiG9w0BCQEWDHJvb3RAcml0LmVkdTAeFw0wOTA2MDgxNzM2NTBaFw0xOTA2\nMDgxNzM2NTBaMIGvMQswCQYDVQQGEwJVUzERMA8GA1UECBMITmV3IFlvcmsxEjAQ\nBgNVBAcTCVJvY2hlc3RlcjEqMCgGA1UEChMhUm9jaGVzdGVyIEluc3RpdHV0ZSBv\nZiBUZWNobm9sb2d5MQwwCgYDVQQLEwNJVFMxIjAgBgNVBAMTGVJJVCBDZXJ0aWZp\nY2F0ZSBBdXRob3JpdHkxGzAZBgkqhkiG9w0BCQEWDHJvb3RAcml0LmVkdTCCAiIw\nDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBANPFn5BNR/YtImOmx1gCtzmkEJ4T\nlitexJ2UYifbwRMXELYGbdceBP8+6XZN4tOurnOCzBrjJqGDHfltSkJbS4grJr0i\nGJDjRV6SKs8MbGG7VxZiV4pTitkFIWASK+u4ILyqZbjyx3uZD7JKqPYFwj2fJfSw\nks//w391fdkGt6tWALWMjNi7MHzR8zQkAtlILdClO9lbEZOqvrI0R+F3J5NuWkzH\nifN6+bSQWEO9ZBb6qZqE9MkpN8WK7O53ieTXAnRirds+P/35oGsyO5yVdgG3WeOL\n30IJPbw7UPNGr/wlyBbNGoj8/OLY9XXV/BKo6A6AAb/rq9uDDhfhtW3XWB+kocov\n/NcmVL5Mv7quaYiw9swEKkvKA6wUwKXck6BbaTq7L4BYnNSKFhG8WRyah7ysRcP6\ns9loAK1yWgTQy/WCbXRNSojw84StHsaieOVigePVdtT2ZQAlxHKRneyqTUXee5/n\nGncIjvDZ+j0aFd9KDrlrsIMf6WQBXbRfCpB5K2fnI2miRmQd4vYZLVzeEQk56tkI\njY3m31KNAqRpfVLMIqGYZ3e8CkXs7xu96en3QRSotQEbfHHBmptIUVgHP7t9bAID\nKzGog85Lj/jKOne69SUP/PQjAwrHE8g6MiGyqi/XLG//tLavm+rwzH/c07Ahbre0\nseiZYjRQHSB+qhllAgMBAAGjggFKMIIBRjAwBgNVHR8EKTAnMCWgI6Ahhh9odHRw\nOi8vY2VydHMucml0LmVkdS9jYS1jcmwuY3JsMB0GA1UdDgQWBBSlvxR2dllnS6Tr\nbXWbhGLEDPOLuzCB5AYDVR0jBIHcMIHZgBSlvxR2dllnS6TrbXWbhGLEDPOLu6GB\ntaSBsjCBrzELMAkGA1UEBhMCVVMxETAPBgNVBAgTCE5ldyBZb3JrMRIwEAYDVQQH\nEwlSb2NoZXN0ZXIxKjAoBgNVBAoTIVJvY2hlc3RlciBJbnN0aXR1dGUgb2YgVGVj\naG5vbG9neTEMMAoGA1UECxMDSVRTMSIwIAYDVQQDExlSSVQgQ2VydGlmaWNhdGUg\nQXV0aG9yaXR5MRswGQYJKoZIhvcNAQkBFgxyb290QHJpdC5lZHWCCQC6oAzhbdb7\nWDAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBQUAA4ICAQAA0PGictX/K0Bhe7xr\nZj8HyDhXYQvrChtGbJv6wm5UXYS2SNjjPaVykeJmuvGzOACrIo3O5UvDNbxcLkm7\niFe7i8KgkRI2OeEHDa+n71nVfQs7YBeb8tdtYLeIn8sf04Sb2HeKTmBA7naHa2o+\n/yyb95MYr8SgAf+M9wdvSeZdWfTWe1QI2LWsGmtKHNMWdpXXcBFFj8JRCmTP+gyt\nqgC3Pjv+Ls+g6CWa5mKORNTJuo6QqIf+b63JsQ6LWg0e51i45Dm9sqlqUV0jnaqQ\n8KoFgP1jx0Bxc6cKbyr5QfEgnqsY107xJU5nD/MrIE4hAqnt3sbC483dK0yEuODn\nIkjJ9mwh+qlsu4/FmzMIulCpmuzklNq36kJLsejl5vOM9VZTxy8TGKcijfjgPuc6\nMLfX1kw4XMnyZiRio2vZBd8wP/mzK/IeVGO3pCqsj0XIDeU2Swe85OwWxa0lzUyT\ndjvpT9boCEP9FkKYTt3Qh/zUDhA20gMc7NMkkDFqcmT+RGZWCCAqEP4hCBnKk2Kk\nMf0JCu2ElqHgtCyrgn1wbjbqDcu7l3tXUe1qZFr028AZMrRAQWMUcpJSVjX6d0P9\nFWDHydTsiXBlcjNpG6pRd0pLapC3VbSUMkL/Hcl5D6KYxqzELt7flDn/muQC7xlz\n/rgs3h5GbWPMWlE6PVxSqSF1ag==\n-----END CERTIFICATE-----";
	/**
	 * NFSN's PEM encoded CA certificate.
	 */
	private static final String NFSN_CERTIFICIATE = "-----BEGIN CERTIFICATE-----\nMIIDjDCCAvWgAwIBAgIBADANBgkqhkiG9w0BAQQFADCBkTELMAkGA1UEBhMCVVMx\nDjAMBgNVBAgTBVRleGFzMRYwFAYDVQQHEw1UaGUgV29vZGxhbmRzMRMwEQYDVQQK\nEwpORlNOLCBJbmMuMTMwMQYDVQQLEypOZWFybHlGcmVlU3BlZWNoLk5FVCBDZXJ0\naWZpY2F0ZSBBdXRob3JpdHkxEDAOBgNVBAMTB05GU04gQ0EwHhcNMDQwNzMwMjMw\nNjQ3WhcNMTQwNzI4MjMwNjQ3WjCBkTELMAkGA1UEBhMCVVMxDjAMBgNVBAgTBVRl\neGFzMRYwFAYDVQQHEw1UaGUgV29vZGxhbmRzMRMwEQYDVQQKEwpORlNOLCBJbmMu\nMTMwMQYDVQQLEypOZWFybHlGcmVlU3BlZWNoLk5FVCBDZXJ0aWZpY2F0ZSBBdXRo\nb3JpdHkxEDAOBgNVBAMTB05GU04gQ0EwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJ\nAoGBAMTNaw9akY8tZfUgM7m4uEgcJEwq1eoX1gIuv9VZ7RVMKe5L6Sc31yEI3bxQ\nwjFjZbxQMfGOPUoBzyB2uWiyFf0N1thSbq3stFhJVjOk/F68fJdYUN7nqfmvve/U\nZ9ILQm+KNJYDKF6g/NhwVxlogzF6v3MJr43mxcMzpIpv1g+fAgMBAAGjgfEwge4w\nHQYDVR0OBBYEFAUV17b7WHspDVPf1nQ8DmIjCdVpMIG+BgNVHSMEgbYwgbOAFAUV\n17b7WHspDVPf1nQ8DmIjCdVpoYGXpIGUMIGRMQswCQYDVQQGEwJVUzEOMAwGA1UE\nCBMFVGV4YXMxFjAUBgNVBAcTDVRoZSBXb29kbGFuZHMxEzARBgNVBAoTCk5GU04s\nIEluYy4xMzAxBgNVBAsTKk5lYXJseUZyZWVTcGVlY2guTkVUIENlcnRpZmljYXRl\nIEF1dGhvcml0eTEQMA4GA1UEAxMHTkZTTiBDQYIBADAMBgNVHRMEBTADAQH/MA0G\nCSqGSIb3DQEBBAUAA4GBAJc+Rh3mNrwy0c3QaR1qLvZdoZP1fhU1RWxWG/ex0/M1\njVPvvfExn6UrDGtLMDUn1MwnQ5PtQ1fmwGqiev7N4uEOqiUdcgO4+ZAUHxSyOHX2\nTj5CJ8PhRwlJBf/X8keQAXJtRexAPJgvHabb/aQrOoB9Gh4kBuQHt/Ijyg9+cg0M\n-----END CERTIFICATE-----";
	
	/**
	 * The logger.
	 */
	private static final Logger LOG = Logger.getLogger(SelfSignedSSLSocketFactory.class);

	/**
	 * YAY SINGLETON!
	 */
	private static SelfSignedSSLSocketFactory FACTORY;

	/**
	 * The wrapped factory.
	 */
	private SSLSocketFactory factory;

	/**
	 * Initializes the certificate processing.
	 * 
	 */
	private SelfSignedSSLSocketFactory() {
		try {

			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			SSLContext context = SSLContext.getInstance("TLS");
			
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);

			addCertificate(ks, "RITCERT", RIT_CERTIFICATE);
			addCertificate(ks, "NFSNCERT", NFSN_CERTIFICIATE);
			
			
			tmf.init(ks);
			context.init((KeyManager[]) null, tmf.getTrustManagers(),
					(SecureRandom) null);
			factory = context.getSocketFactory();

			/*
			 * The following exceptions should NEVER happen. Something is
			 * seriously wrong and we can't even continue. I can't even FATHOM
			 * why these would pop on a standard JRE. So redtexting them is just
			 * fine.
			 */
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
		} catch (KeyStoreException e) {
			LOG.error(e);
		} catch (CertificateException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		} catch (KeyManagementException e) {
			LOG.error(e);
		}

	}
	
	private void addCertificate(
			final KeyStore ks, 
			final String name, 
			final String cert) throws CertificateException, KeyStoreException{
		InputStream inStream = new ByteArrayInputStream(
				cert.getBytes());
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate c = (X509Certificate) cf
				.generateCertificate(inStream);
		
		ks.setCertificateEntry(name, c);
	}

	/**
	 * Wrapper for factory.
	 * 
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int)
	 * @param arg0
	 *            KLINGON PROGRAMMERS DON'T HAVE PARAMETERS. THE HAVE ARGUMENTS!
	 * @param arg1
	 *            AND THEY ALWAYS WIN THEM!
	 * @return A socket
	 * @throws IOException
	 *             MORE LIEK IO.INCEPTION!
	 * @throws UnknownHostException
	 *             No idea.
	 */
	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException,
			UnknownHostException {
		LOG.debug("SOCKET 1 CREATED");
		return factory.createSocket(arg0, arg1);
	}

	/**
	 * Wrapper for the factory.
	 * 
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int)
	 * @param arg0
	 *            Some address?
	 * @param arg1
	 *            YAY AN INT!
	 * @return A SOCKET!
	 * @throws IOException
	 *             BWAAAAAM
	 */
	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		LOG.debug("SOCKET 2 CREATED");
		return factory.createSocket(arg0, arg1);
	}

	/**
	 * Wrapper for the factory.
	 * 
	 * @see javax.net.SocketFactory#createSocket(java.lang.String, int,
	 *      java.net.InetAddress, int)
	 * @param arg0
	 *            Something fun
	 * @param arg1
	 *            Cool stuffs.
	 * @param arg2
	 *            I don't even know.
	 * @param arg3
	 *            Why am I not drunk?
	 * @return A socket!
	 * @throws IOException
	 *             Shit went down.
	 * @throws UnknownHostException
	 *             OH SHI...
	 */
	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
			throws IOException, UnknownHostException {
		LOG.info("SOCKET 3 CREATED");
		return factory.createSocket(arg0, arg1, arg2, arg3);
	}

	/**
	 * Wrapper for the factory.
	 * 
	 * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int,
	 *      java.net.InetAddress, int)
	 * @param arg0
	 *            Something?
	 * @param arg1
	 *            Something else?
	 * @param arg2
	 *            What is this I don't even?
	 * @param arg3
	 *            Profits?
	 * @return A socket.
	 * @throws IOException
	 *             I'm sure there are some fun circumstances that this will
	 *             happen. I don't know what these are.
	 */
	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2,
			int arg3) throws IOException {
		LOG.debug("SOCKET 4 CREATED");
		return factory.createSocket(arg0, arg1, arg2, arg3);
	}

	/**
	 * Wrapper for the factory.
	 * 
	 * @see javax.net.SocketFactory#createSocket()
	 * @return A socket.
	 * @throws IOException
	 *             shit went down.
	 */
	@Override
	public Socket createSocket() throws IOException {
		LOG.info("SOCKET 5 CREATED");
		return factory.createSocket();
	}

	/**
	 * Returns this singleton factory.
	 * 
	 * @return Instance of LdapSocketFactory
	 * @see javax.net.SocketFactory#getDefault()
	 */
	public static SSLSocketFactory getDefault() {
		if (FACTORY == null) {
			FACTORY = new SelfSignedSSLSocketFactory();
		}
		LOG.info("FACTORY RETURNED");
		return FACTORY;
	}

	@Override
	public Socket createSocket(Socket s, String host, int port,
			boolean autoClose) throws IOException {
		return factory.createSocket(s, host, port, autoClose);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return factory.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return factory.getDefaultCipherSuites();
	}
}