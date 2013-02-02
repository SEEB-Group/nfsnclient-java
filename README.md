nfsnclient-java
===============

API HTTP REST client for Nearly Free Speech.net's API.

Things working:

Accounts
 * Balance
 * Balance Cash
 * Balance High
 * Balance Credit
 * Friendly Name
 * Status
 * Sites

Example:

```java
String login = props.getProperty("apiuser");
String apiKey = props.getProperty("apikey");
String account = props.getProperty("account");

NFSNAPIManager api = new NFSNAPIManager(login, apiKey);
NFSNAccount acc = api.getAccount(account);

double balance = acc.getBalance();
```
See [AccountTests.java](https://github.com/spmadden/nfsnclient-java/blob/master/src/test/com/spmadden/jnfsn/test/AccountTests.java) for more info (and a full example).

DNS
 * Expire
 * TTL
 * Refresh
 * Retry
 * Serial
 * Listing Resource Records (name, type, data)

```java
String login = props.getProperty("apiuser");
String apiKey = props.getProperty("apikey");
String domain = props.getProperty("domain");

NFSNAPIManager api = new NFSNAPIManager(login, apiKey);
NFSNDns dns = api.getDNS(domain);

long expire = dns.getExpire();

DNSResourceRecord[] rrs = dns.getAllRecords();
DNSResourceRecord[] typeARrs = dns.getRecordsByType("A");

```
See [DNSTests.java](https://github.com/spmadden/nfsnclient-java/blob/master/src/test/com/spmadden/jnfsn/test/DNSTests.java) for more info (and a full example).
