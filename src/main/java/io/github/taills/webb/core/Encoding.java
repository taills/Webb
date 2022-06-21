package io.github.taills.webb.core;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;


@Slf4j
public class Encoding {
   private String charsetString;
   private static final String[] ENCODING_TYPES = new String[]{"UTF-8", "GBK", "GB2312", "BIG5", "GB18030", "ISO-8859-1", "latin1", "UTF16", "ascii", "cp850","CP866","CP437"};

   private Encoding(String charsetString) {
      this.charsetString = charsetString;
   }

   public static String[] getAllEncodingTypes() {
      return ENCODING_TYPES;
   }

   public byte[] Encoding(String string) {
      try {
         return string.getBytes(this.charsetString);
      } catch (UnsupportedEncodingException var3) {
         log.error(String.valueOf(var3));
         return string.getBytes();
      }
   }
//编码解码
   public String Decoding(byte[] srcData) {
      if (srcData == null) {
         return "";
      } else {
         try {

            String str = new String(srcData, this.charsetString);
            return str;
         } catch (UnsupportedEncodingException var3) {
            log.error(String.valueOf(var3));
            return new String(srcData);
         }
      }
   }

   public void setCharsetString(String charsetString) {
      this.charsetString = charsetString;
   }

   public String getCharsetString() {
      return this.charsetString;
   }


   public static Encoding getEncoding(String charsetString) {
      return new Encoding(charsetString);
   }

   public String toString() {
      return this.charsetString;
   }
}
