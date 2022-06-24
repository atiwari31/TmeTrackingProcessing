package com.dcardprocessing.util;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.springframework.stereotype.Component;

import java.io.*;
@Component
public class TlsUtility {

    private static final String[] protocols = new String[]{"TLSv1.2"};
    private static final String[] cipher_suites = new String[]{"tls_ecdhe_rsa_with_aes_128_gcm_sha256"};
    
    public static void main(String[] args) throws Exception {
    {
        SSLSocket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            SSLSocketFactory factory =
                    (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket =
                    (SSLSocket) factory.createSocket("localhost", 8080);
            
            socket.setEnabledProtocols(protocols);
            socket.setEnabledCipherSuites(cipher_suites);

            socket.startHandshake();

            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())));

            out.println("GET / HTTP/1.0");
            out.println();
            out.flush();

            if (out.checkError())
                System.out.println("SSLSocketClient:  java.io.PrintWriter error");

            /* read response */
            in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

}}
