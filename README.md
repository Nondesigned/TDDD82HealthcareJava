#Java server for call functionality
Repo for the java server that is used for the call functionality.
In the following sections you will find the documentation based on the JavaDoc.0

#NOTE!
##Server
Remember to add the keystore *certs.jks*.
##Clients
In order to be able to use self-signed certificates you have to accept them on the client-side.
Use the following context to create a new factory for the creation of an SSL-socket:
```java
    SSLContext context = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
            }
        } };
        context.init(null, trustAllCerts, null);
```
##Documentation
###ControlPacket
Contains the TCP-based protocol. Has getters and setters for each field. Documentation given below.

###### `public class ControlPacket` 

ControlPacket

###### `public byte[] getPacketBytes()`

Returns the byte-data

###### `public boolean getFlag(int i)`

Returns the value of flag i

###### `public Integer getSource()`

Returns source address

###### `public Integer getDestination()`

Returns source address

###### `public String getKey()`

Returns key for symmetric encryption

###### `public Integer getContentLength()`

Returns content-length

###### `public byte[] getPayload()`

Returns payload content

###### `public void setFlag(ControlFlags flags)`

Sets the value of flag i

###### `public void setDestination(int destination)`

Sets destination address

###### `public void setKey(String key)`

Sets key for symmetric encryption

###### `public void setPayload(byte[] content)`

Sets payload content and sets the Content-length

###### `private byte[] getFlagArray()`

Returns byte[] with flags

###### `private byte[] getSourceArray()`

Returns byte[] with source address

###### `private byte[] getDestinationArray()`

Returns byte[] with destination address

###### `private byte[] getKeyArray()`

Returns byte[] with destination address

###### `private byte[] getContentLengthArray()`

Returns byte[] with contetlength


### ControlFlags
The flags used for the controlpacket. 

###### `public class ControlFlags implements Flags`

ControlFlags

###### `public ControlFlags ()`

Constructer: Creates new byte array

###### `public byte[] getBytes()`

Returns the bytes containing the flags

###### `public boolean getFlag(int i)`

Returns the flag at position i

###### `public boolean geFlag(ControlFlag flag)`

Returns flag based on enum Controlflag

###### `public void setFlag(ControlFlag flag, boolean value)`

Sets flag based on enum and boolean value
