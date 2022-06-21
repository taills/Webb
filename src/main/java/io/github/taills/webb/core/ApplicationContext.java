package io.github.taills.webb.core;


import com.formdev.flatlaf.util.SystemInfo;
import io.github.taills.webb.core.Shell.Cryption;
import io.github.taills.webb.core.Shell.Payload;
import io.github.taills.webb.core.Shell.Plugin;
import io.github.taills.webb.core.annotation.CryptionAnnotation;
import io.github.taills.webb.core.annotation.PayloadAnnotation;
import io.github.taills.webb.core.annotation.PluginAnnotation;
import io.github.taills.webb.util.functions;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.annotation.Annotation;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class ApplicationContext {
   public static final String VERSION = "4.01";
   private static final HashMap<String, Class<?>> payloadMap;
   private static final HashMap<String, HashMap<String, Class<?>>> cryptionMap;
   private static final HashMap<String, HashMap<String, Class<?>>> pluginMap;
   private static File[] pluginJarFiles;
   public static int windowWidth;
   public static int windowsHeight;
   public static ThreadLocal<Boolean> isShowHttpProgressBar;
   public static final CoreClassLoader PLUGIN_CLASSLOADER;
   public static boolean easterEgg;
   private static Font font;
   private static Map<String, String> headerMap;

   protected ApplicationContext() {
   }

   public static void init() {
      initHttpHeader();
      scanPluginJar();
      scanPayload();
      scanCryption();
      scanPlugin();
   }
   

   private static void initHttpHeader() {
      String headerString = "k: v";
      if (headerString != null) {
         String[] reqLines = headerString.split("\n");
         headerMap = new Hashtable();

         for(int i = 0; i < reqLines.length; ++i) {
            if (!reqLines[i].trim().isEmpty()) {
               int index = reqLines[i].indexOf(":");
               if (index > 1) {
                  String keyName = reqLines[i].substring(0, index).trim();
                  String keyValue = reqLines[i].substring(index + 1).trim();
                  headerMap.put(keyName, keyValue);
               }
            }
         }
      }

   }

   private static void scanPayload() {
      try {
         URL url = ApplicationContext.class.getResource("/shells/payloads/");
         ArrayList<Class> destList = new ArrayList();
         int loadNum = scanClass(url.toURI(), "shells.payloads", Payload.class, PayloadAnnotation.class, destList);
         destList.forEach((t) -> {
            try {
               Annotation annotation = t.getAnnotation(PayloadAnnotation.class);
               String name = (String)annotation.annotationType().getMethod("Name").invoke(annotation, (Object[])null);
               payloadMap.put(name, t);
               cryptionMap.put(name, new HashMap());
               pluginMap.put(name, new HashMap());
            } catch (Exception var3) {
               log.error(String.valueOf(var3));
            }

         });
         log.info(String.format("load payload success! payloadMaxNum:{} onceLoadPayloadNum:{}", payloadMap.size(), loadNum));
      } catch (Exception var3) {
         log.error(String.valueOf(var3));
      }


   }

   private static void scanCryption() {
      try {
         URL url = ApplicationContext.class.getResource("/shells/cryptions/");
         ArrayList<Class> destList = new ArrayList();
         int loadNum = scanClass(url.toURI(), "shells.cryptions", Cryption.class, CryptionAnnotation.class, destList);
         int pluginMaxNum = 0;
         destList.forEach((t) -> {
            try {
               Annotation annotation = t.getAnnotation(CryptionAnnotation.class);
               String name = (String)annotation.annotationType().getMethod("Name").invoke(annotation, (Object[])null);
               String payloadName = (String)annotation.annotationType().getMethod("payloadName").invoke(annotation, (Object[])null);
               HashMap destMap = cryptionMap.get(payloadName);
               if (destMap == null) {
                  cryptionMap.put(payloadName, new HashMap());
                  destMap = cryptionMap.get(payloadName);
               }

               destMap.put(name, t);
            } catch (Exception var5) {
               var5.printStackTrace();
               log.error(String.valueOf(var5));
            }

         });
         Iterator iterator = cryptionMap.keySet().iterator();

         while(iterator.hasNext()) {
            String keyString = (String)iterator.next();
            HashMap map = cryptionMap.get(keyString);
            if (map != null) {
               pluginMaxNum += map.size();
            }
         }

         log.info(String.format("load cryption success! cryptionMaxNum:{} onceLoadCryptionNum:{}", pluginMaxNum, loadNum));
      } catch (Exception var7) {
         log.error(String.valueOf(var7));
      }

   }

   private static void scanPlugin() {
      try {
         URL url = ApplicationContext.class.getResource("/shells/plugins/");
         ArrayList<Class> destList = new ArrayList();
         int loadNum = scanClass(url.toURI(), "shells.plugins", Plugin.class, PluginAnnotation.class, destList);
         int pluginMaxNum = 0;
         destList.forEach((t) -> {
            try {
               Annotation annotation = t.getAnnotation(PluginAnnotation.class);
               String name = (String)annotation.annotationType().getMethod("Name").invoke(annotation, (Object[])null);
               String payloadName = (String)annotation.annotationType().getMethod("payloadName").invoke(annotation, (Object[])null);
               HashMap destMap = pluginMap.get(payloadName);
               if (destMap == null) {
                  pluginMap.put(payloadName, new HashMap());
                  destMap = pluginMap.get(payloadName);
               }

               destMap.put(name, t);
            } catch (Exception var5) {
               log.error(String.valueOf(var5));
            }

         });
         Iterator iterator = pluginMap.keySet().iterator();

         while(iterator.hasNext()) {
            String keyString = (String)iterator.next();
            HashMap map = pluginMap.get(keyString);
            if (map != null) {
               pluginMaxNum += map.size();
            }
         }

         log.info(String.format("load plugin success! pluginMaxNum:{} onceLoadPluginNum:{}", pluginMaxNum, loadNum));
      } catch (Exception var7) {
         log.error(String.valueOf(var7));
      }

   }

   private static void scanPluginJar() {
      String[] pluginJars =  new String[]{};
      ArrayList list = new ArrayList();

      for(int i = 0; i < pluginJars.length; ++i) {
         File jarFile = new File(pluginJars[i]);
         if (jarFile.exists() && jarFile.isFile()) {
            addJar(jarFile);
            list.add(jarFile);
         } else {
            log.error(String.format("PluginJarFile : %s no found", pluginJars[i]));
         }
      }

      pluginJarFiles = (File[]) list.toArray(new File[0]);
      log.info(String.format("load pluginJar success! pluginJarNum:{} LoadPluginJarSuccessNum:{}", pluginJars.length, pluginJars.length));
   }

   private static int scanClass(URI uri, String packageName, Class<?> parentClass, Class<?> annotationClass, ArrayList<Class> destList) {
      int num = scanClassX(uri, packageName, parentClass, annotationClass, destList);

      for(int i = 0; i < pluginJarFiles.length; ++i) {
         File jarFile = pluginJarFiles[i];
         num += scanClassByJar(jarFile, packageName, parentClass, annotationClass, destList);
      }

      return num;
   }

   private static int scanClassX(URI uri, String packageName, Class<?> parentClass, Class<?> annotationClass, ArrayList<Class> destList) {
      String jarFileString;
      if ((jarFileString = functions.getJarFileByClass(ApplicationContext.class)) != null) {
         return scanClassByJar(new File(jarFileString), packageName, parentClass, annotationClass, destList);
      } else {
         int addNum = 0;

         try {
            File file = new File(uri);
            File[] file2 = file.listFiles();

            for(int i = 0; i < file2.length; ++i) {
               File objectFile = file2[i];
               if (objectFile.isDirectory()) {
                  File[] objectFiles = objectFile.listFiles();

                  for(int j = 0; j < objectFiles.length; ++j) {
                     File objectClassFile = objectFiles[j];
                     if (objectClassFile.getPath().endsWith(".class")) {
                        try {
                           String objectClassName = String.format("%s.%s.%s", packageName, objectFile.getName(), objectClassFile.getName().substring(0, objectClassFile.getName().length() - ".class".length()));
                           Class objectClass = Class.forName(objectClassName, true, PLUGIN_CLASSLOADER);
                           if (parentClass.isAssignableFrom(objectClass) && objectClass.isAnnotationPresent(annotationClass)) {
                              destList.add(objectClass);
                              ++addNum;
                           }
                        } catch (Exception var19) {
                           log.error(String.valueOf(var19));
                        }
                     }
                  }
               }
            }
         } catch (Exception var20) {
            log.error(String.valueOf(var20));
         }

         return addNum;
      }
   }

   private static int scanClassByJar(File srcJarFile, String packageName, Class<?> parentClass, Class<?> annotationClass, ArrayList<Class> destList) {
      int addNum = 0;

      try {
         JarFile jarFile = new JarFile(srcJarFile);
         Enumeration<JarEntry> jarFiles = jarFile.entries();
         packageName = packageName.replace(".", "/");

         while(jarFiles.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry)jarFiles.nextElement();
            String name = jarEntry.getName();
            if (name.startsWith(packageName) && name.endsWith(".class")) {
               name = name.replace("/", ".");
               name = name.substring(0, name.length() - 6);

               try {
                  Class objectClass = Class.forName(name, true, PLUGIN_CLASSLOADER);
                  if (parentClass.isAssignableFrom(objectClass) && objectClass.isAnnotationPresent(annotationClass)) {
                     destList.add(objectClass);
                     ++addNum;
                  }
               } catch (Exception var14) {
                  log.error(String.valueOf(var14));
               }
            }
         }

         jarFile.close();
      } catch (Exception var15) {
         log.error(String.valueOf(var15));
      }

      return addNum;
   }

   public static String[] getAllPayload() {
      Set<String> keys = payloadMap.keySet();
      return keys.toArray(new String[0]);
   }

   public static Payload getPayload(String payloadName) {
      Class<?> payloadClass = payloadMap.get(payloadName);
      Payload payload = null;
      if (payloadClass != null) {
         try {
            payload = (Payload)payloadClass.newInstance();
         } catch (Exception var4) {
            log.error(String.valueOf(var4));
         }
      }

      return payload;
   }

   public static Plugin[] getAllPlugin(String payloadName) {
      HashMap pluginSrcMap = pluginMap.get(payloadName);
      ArrayList<Plugin> list = new ArrayList();
      Class payloadClass = payloadMap.get(payloadName);

      while(payloadClass != null && (payloadClass = payloadClass.getSuperclass()) != null) {
         if (payloadClass != null && payloadClass.isAnnotationPresent(PayloadAnnotation.class)) {
            list.addAll(new CopyOnWriteArrayList(getAllPlugin(payloadClass)));
         }
      }

      if (pluginSrcMap != null) {
         Iterator keys = pluginSrcMap.keySet().iterator();

         while(keys.hasNext()) {
            String cryptionName = (String)keys.next();
            Class<?> pluginClass = (Class)pluginSrcMap.get(cryptionName);
            if (pluginClass != null) {
               PluginAnnotation pluginAnnotation = pluginClass.getAnnotation(PluginAnnotation.class);
               if (pluginAnnotation.payloadName().equals(payloadName)) {
                  try {
                     Plugin plugin = (Plugin)pluginClass.newInstance();
                     list.add(plugin);
                  } catch (Exception var10) {
                     log.error(String.valueOf(var10));
                  }
               }
            }
         }
      }

      return list.toArray(new Plugin[0]);
   }

   public static Plugin[] getAllPlugin(Class payloadClass) {
      Annotation annotation = payloadClass.getAnnotation(PayloadAnnotation.class);
      if (annotation != null) {
         PayloadAnnotation payloadAnnotation = (PayloadAnnotation)annotation;
         return getAllPlugin(payloadAnnotation.Name());
      } else {
         return new Plugin[0];
      }
   }

   public static String[] getAllCryption(String payloadName) {
      HashMap cryptionSrcMap = cryptionMap.get(payloadName);
      ArrayList<String> list = new ArrayList();
      if (cryptionSrcMap != null) {
         Iterator keys = cryptionSrcMap.keySet().iterator();

         while(keys.hasNext()) {
            String cryptionName = (String)keys.next();
            Class<?> cryptionClass = (Class)cryptionSrcMap.get(cryptionName);
            if (cryptionClass != null) {
               CryptionAnnotation cryptionAnnotation = cryptionClass.getAnnotation(CryptionAnnotation.class);
               if (cryptionAnnotation.payloadName().equals(payloadName)) {
                  list.add(cryptionName);
               }
            }
         }
      }

      return list.toArray(new String[0]);
   }

   public static Cryption getCryption(String payloadName, String crytionName) {
      HashMap cryptionSrcMap = cryptionMap.get(payloadName);
      if (cryptionSrcMap != null) {
         Class<?> cryptionClass = (Class)cryptionSrcMap.get(crytionName);
         if (cryptionMap != null) {
            CryptionAnnotation cryptionAnnotation = cryptionClass.getAnnotation(CryptionAnnotation.class);
            if (cryptionAnnotation.payloadName().equals(payloadName)) {
               Cryption cryption = null;

               try {
                  cryption = (Cryption)cryptionClass.newInstance();
                  return cryption;
               } catch (Exception var7) {
                  log.error(String.valueOf(var7));
                  return null;
               }
            }
         }
      }

      return null;
   }

   private static void addJar(File jarPath) {
      try {
         PLUGIN_CLASSLOADER.addJar(jarPath.toURI().toURL());
      } catch (Exception var2) {
         log.error(String.valueOf(var2));
      }

   }

   private static void InitGlobalFont(Font font) {
      FontUIResource fontRes = new FontUIResource(font);
      Enumeration keys = UIManager.getDefaults().keys();

      while(keys.hasMoreElements()) {
         Object key = keys.nextElement();
         Object value = UIManager.get(key);
         if (value instanceof FontUIResource) {
            UIManager.put(key, fontRes);
         }
      }

   }

//
//   public static void genHttpsConfig() {
//      try {
//         KeyPair keyPair = CertUtil.genKeyPair();
//         String base64HttpsCert = functions.base64EncodeToString(CertUtil.genCACert("C=CN, ST=GD, L=SZ, O=lee, OU=study, CN=HttpsProxy", new Date(), new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(3650L)), keyPair).getEncoded());
//         String base64HttpsPrivateKey = functions.base64EncodeToString((new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded())).getEncoded());
//         Db.addSetingKV("HttpsPrivateKey", base64HttpsPrivateKey);
//         Db.addSetingKV("HttpsCert", base64HttpsCert);
//      } catch (Exception var3) {
//         throw new RuntimeException(var3);
//      }
//   }
//
//   public static PrivateKey getHttpsPrivateKey() {
//      try {
//         String base64String = Db.getSetingValue("HttpsPrivateKey");
//         if (base64String == null) {
//            genHttpsConfig();
//         }
//
//         return CertUtil.loadPriKey(functions.base64Decode(Db.getSetingValue("HttpsPrivateKey")));
//      } catch (Exception var1) {
//         throw new RuntimeException(var1);
//      }
//   }
//
//   public static X509Certificate getHttpsCert() {
//      try {
//         String base64String = Db.getSetingValue("HttpsCert");
//         if (base64String == null) {
//            genHttpsConfig();
//         }
//
//         return CertUtil.loadCert(new ByteArrayInputStream(functions.base64Decode(Db.getSetingValue("HttpsCert"))));
//      } catch (Exception var1) {
//         throw new RuntimeException(var1);
//      }
//   }

   static {
      windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
      windowsHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
      isShowHttpProgressBar = new ThreadLocal();
      PLUGIN_CLASSLOADER = new CoreClassLoader(ApplicationContext.class.getClassLoader());
      easterEgg = true;
      payloadMap = new HashMap();
      cryptionMap = new HashMap();
      pluginMap = new HashMap();
   }
}
