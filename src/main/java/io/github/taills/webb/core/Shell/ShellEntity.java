package io.github.taills.webb.core.Shell;

import io.github.taills.webb.core.ApplicationContext;
import io.github.taills.webb.core.Encoding;
import io.github.taills.webb.core.Shell.cache.CachePayload;
import io.github.taills.webb.util.functions;
import io.github.taills.webb.util.http.Http;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class ShellEntity {
   private String url;
   private String password;
   private String secretKey;
   private String payload;
   private String cryption;
   private String remark;
   private String encoding;
   private Map<String, String> headers;
   private String reqLeft;
   private String reqRight;
   private int connTimeout;
   private int readTimeout;
   private String proxyType;
   private String proxyHost;
   private int proxyPort;
   private Cryption cryptionModel;
   private Payload payloadModel;
   private String id;
   private JFrame frame;
   private Encoding encodingModule;
   private Encoding dbEncodingModule;
   private String dbEncoding;
   private Http http;
   private boolean isSendLRReqData;
   private boolean useCache;

   public ShellEntity(boolean useCache) {
      this.url = "";
      this.password = "";
      this.secretKey = "";
      this.payload = "";
      this.cryption = "";
      this.remark = "";
      this.encoding = "";
      this.headers = new HashMap();
      this.reqLeft = "";
      this.reqRight = "";
      this.connTimeout = 60000;
      this.readTimeout = 60000;
      this.proxyType = "";
      this.proxyHost = "";
      this.proxyPort = 8888;
      this.id = "";
      this.useCache = useCache;
   }

   public ShellEntity() {
      this(false);
   }

   public boolean initShellOpertion() {
      boolean state = false;

      try {
//         this.http = ApplicationContext.getHttp(this);
         if (this.isUseCache()) {
            this.payloadModel = CachePayload.openUseCachePayload(this, ApplicationContext.getPayload(this.payload).getClass());
            if (this.payloadModel != null) {
               this.payloadModel.init(this);
               return true;
            } else {
               return false;
            }
         } else {
//            if (ApplicationContext.isOpenCache()) {
//               this.payloadModel = CachePayload.openCachePayload(this, ApplicationContext.getPayload(this.payload).getClass());
//            } else {
//               this.payloadModel = ApplicationContext.getPayload(this.payload);
//            }
            this.payloadModel = ApplicationContext.getPayload(this.payload);
            this.cryptionModel = ApplicationContext.getCryption(this.payload, this.cryption);
            this.cryptionModel.init(this);// error
            if (this.cryptionModel.check()) {
               this.payloadModel.init(this);
               if (this.payloadModel.test()) {
                  state = true;
               } else {
                  log.error("payload Initialize Fail !");
               }
            } else {
               log.error("cryption Initialize Fail !");
            }

            return state;
         }
      } catch (Throwable e) {
         log.error(String.valueOf(e));
         ByteArrayOutputStream stream = new ByteArrayOutputStream();
         PrintStream printStream = new PrintStream(stream);
         e.printStackTrace(printStream);
         printStream.flush();
         printStream.close();
         log.info(stream.toString());
         return state;
      }
   }

   public Http getHttp() {
      return this.http;
   }

   public Cryption getCryptionModule() {
      return this.cryptionModel;
   }

   public Payload getPayloadModule() {
      return this.payloadModel;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getPassword() {
      return this.password;
   }

   public String getSecretKey() {
      return this.secretKey;
   }

   public String getSecretKeyX() {
      return functions.md5(this.getSecretKey()).substring(0, 16);
   }

   public String getPayload() {
      return this.payload;
   }

   public String getEncoding() {
      return this.encoding;
   }

   public synchronized Encoding getEncodingModule() {
      if (this.encodingModule == null) {
         this.encodingModule = Encoding.getEncoding(this.getEncoding());
      }

      return this.encodingModule;
   }

   public synchronized String getDbEncoding() {
      if (this.dbEncoding == null) {
         this.dbEncoding = "UTF-8";
      }

      return this.dbEncoding;
   }

   public synchronized Encoding getDbEncodingModule() {
      if (this.dbEncodingModule == null) {
         this.dbEncodingModule = Encoding.getEncoding(this.getDbEncoding());
      }

      return this.dbEncodingModule;
   }

   public String getProxyType() {
      return this.proxyType;
   }

   public String getProxyHost() {
      return this.proxyHost;
   }

   public int getProxyPort() {
      return this.proxyPort;
   }

   public String getCryption() {
      return this.cryption;
   }

   public void setCryption(String cryption) {
      this.cryption = cryption;
   }

   public void setHeaders(Map<String, String> headers) {
      this.headers = headers;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setSecretKey(String secretKey) {
      this.secretKey = secretKey;
   }

   public void setPayload(String Payload) {
      this.payload = Payload;
   }

   public void setEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void setProxyType(String proxyType) {
      this.proxyType = proxyType;
   }

   public void setProxyHost(String proxyHost) {
      this.proxyHost = proxyHost;
   }

   public void setProxyPort(int proxyPort) {
      this.proxyPort = proxyPort;
   }

   public int getConnTimeout() {
      return this.connTimeout;
   }

   public int getReadTimeout() {
      return this.readTimeout;
   }

   public void setConnTimeout(int connTimeout) {
      this.connTimeout = connTimeout;
   }

   public void setReadTimeout(int readTimeout) {
      this.readTimeout = readTimeout;
   }

   public Map<String, String> getHeaders() {
      return this.headers;
   }

   public String getHeaderS() {
      StringBuilder builder = new StringBuilder();
      Iterator iterator = this.headers.keySet().iterator();

      while(iterator.hasNext()) {
         String key = (String)iterator.next();
         String value = (String)this.headers.get(key);
         builder.append(key);
         builder.append(": ");
         builder.append(value);
         builder.append("\r\n");
      }

      return builder.toString();
   }

   public String getRemark() {
      return this.remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }

   public JFrame getFrame() {
      return this.frame;
   }

   public void setFrame(JFrame frame) {
      this.frame = frame;
   }

   public void setHeader(String reqString) {
      if (reqString != null) {
         String[] reqLines = reqString.split("\n");
         this.headers = new Hashtable();

         for(int i = 0; i < reqLines.length; ++i) {
            if (!reqLines[i].trim().isEmpty()) {
               int index = reqLines[i].indexOf(":");
               if (index > 1) {
                  String keyName = reqLines[i].substring(0, index).trim();
                  String keyValue = reqLines[i].substring(index + 1).trim();
                  this.headers.put(keyName, keyValue);
               }
            }
         }
      }

   }

   public String getReqLeft() {
      return this.reqLeft;
   }

   public void setReqLeft(String reqLeft) {
      this.reqLeft = reqLeft;
   }

   public String getReqRight() {
      return this.reqRight;
   }

   public void setReqRight(String reqRight) {
      this.reqRight = reqRight;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public boolean isSendLRReqData() {
      return this.cryptionModel.isSendRLData();
   }

   public boolean setEnv(String key, String value) {
     return false;
   }

   public String getEnv(String key, String defaultValue) {
      String getShellEnvSql = "SELECT value FROM shellEnv WHERE shellId=? and key=?";
      return getShellEnvSql;
   }

   public void setGroup(String groupId) {
      this.setEnv("ENV_GROUP_ID", groupId);
   }

   public String getGroup() {
      return this.getEnv("ENV_GROUP_ID", "/");
   }

   public boolean removeEnv(String key) {
      return true;
   }

   public boolean existsSetingKey(String key) {
      return true;
   }

   public void setUseCache(boolean useCache) {
      this.useCache = useCache;
   }

   public boolean isUseCache() {
      return this.useCache;
   }

   public String toString() {
      return "ShellEntity [id=" + this.id + ", url=" + this.url + ", password=" + this.password + ", secretKey=" + this.secretKey + ", payload=" + this.payload + ", cryption=" + this.cryption + ", remark=" + this.remark + ", encoding=" + this.encoding + ", headers=" + this.headers + ", reqLeft=" + this.reqLeft + ", reqRight=" + this.reqRight + ", connTimeout=" + this.connTimeout + ", readTimeout=" + this.readTimeout + ", proxyType=" + this.proxyType + ", proxyHost=" + this.proxyHost + ", proxyPort=" + this.proxyPort + "]";
   }
}
