package com.httpProxy.server.core;

import com.httpProxy.server.CertPool;
import com.httpProxy.server.request.HttpRequest;
import com.httpProxy.server.response.HttpResponse;
import com.httpProxy.server.response.HttpResponseHeader;
import com.httpProxy.server.response.HttpResponseStatus;

import javax.net.ssl.SSLSocket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpProxyServer {
   private int listenPort;
   private int backlog;
   private InetAddress bindAddr;
   private boolean nextSocket = true;
   private CertPool certPool;
   private ServerSocket serverSocket;
   private HttpProxyHandle proxyHandle;

   public HttpProxyServer(int listenPort, int backlog, InetAddress bindAddr, CertPool certPool, HttpProxyHandle proxyHandle) {
      this.listenPort = listenPort;
      this.backlog = backlog;
      this.bindAddr = bindAddr;
      this.certPool = certPool;
      this.proxyHandle = proxyHandle;
   }

   public boolean startup() {
      try {
         this.serverSocket = new ServerSocket(this.listenPort, this.backlog, this.bindAddr);
      } catch (Exception var2) {
         var2.printStackTrace();
         return false;
      }

      return this.acceptService(this.serverSocket);
   }

   public void shutdown() {
      try {
         this.serverSocket.close();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public void handler(Socket socket, HttpRequest httpRequest) throws Exception {
      try {
         if (this.proxyHandle != null) {
            this.proxyHandle.handler(socket, httpRequest);
         } else {
            HttpResponse httpResponse = new HttpResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, (HttpResponseHeader)null, "No Input HttpProxyHandle");
            socket.getOutputStream().write(httpResponse.encode());
         }
      } catch (Exception var7) {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         PrintStream printStream = new PrintStream(byteArrayOutputStream);
         var7.printStackTrace(printStream);
         printStream.flush();
         printStream.close();
         HttpResponse httpResponse = new HttpResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, (HttpResponseHeader)null, byteArrayOutputStream.toByteArray());
         byteArrayOutputStream.close();
         socket.getOutputStream().write(httpResponse.encode());
      }

      this.closeSocket(socket);
   }

   private boolean acceptService(ServerSocket sslServerSocket) {
      (new Thread(() -> {
         Socket sslSocket = null;
         Socket finalSslSocket = sslSocket;
         Socket finalSslSocket1 = sslSocket;
         for(; this.nextSocket; (new Thread(() -> {
            try {
               finalSslSocket.setSoTimeout(100);
               Socket client = finalSslSocket;
               InputStream inputStream = finalSslSocket.getInputStream();
               HttpRequest httpRequest = HttpRequest.Decode(inputStream);
               if (httpRequest.isHttps()) {
                  finalSslSocket.getOutputStream().write((new HttpResponse(new HttpResponseStatus(200, "Connection established"))).encode());
                  client = this.certPool.getSslContext(httpRequest.getHost()).getSocketFactory().createSocket(finalSslSocket, finalSslSocket1.getInetAddress().getHostAddress(), finalSslSocket1.getPort(), true);
                  ((SSLSocket)client).setUseClientMode(false);
                  inputStream = client.getInputStream();
                  httpRequest = HttpRequest.Decode(inputStream, httpRequest);
               }

               this.handler(client, httpRequest);
            } catch (Exception var5) {
               this.closeSocket(finalSslSocket);
            }

            this.closeSocket(finalSslSocket);
         })).start()) {
            try {
               sslSocket = sslServerSocket.accept();
            } catch (IOException var4) {
               return;
            }
         }

      })).start();
      return true;
   }

   protected void closeSocket(Socket socket) {
      if (socket != null || socket.isClosed()) {
         try {
            if (!socket.isClosed()) {
               socket.close();
            }
         } catch (IOException var3) {
         }

      }
   }

   public HttpProxyHandle getProxyHandle() {
      return this.proxyHandle;
   }

   public void setProxyHandle(HttpProxyHandle proxyHandle) {
      this.proxyHandle = proxyHandle;
   }

   public boolean isNextSocket() {
      return this.nextSocket;
   }

   public void setNextSocket(boolean nextSocket) {
      this.nextSocket = nextSocket;
   }
}
