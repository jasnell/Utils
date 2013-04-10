/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright in this work, please see the NOTICE file in the top level
 * directory of this distribution.
 */
package snell.utils;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;

import javax.crypto.Mac;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import static com.google.common.base.Throwables.propagate;

public final class Crypto {

  private Crypto() {}
  
  public static Function<byte[],String> sig(
    final PrivateKey key, final String alg) {
      return new Function<byte[],String>() {
        public String apply(byte[] input) {
          return sig(key,alg,input);
        }
      };
  }
  
  public static Function<byte[],String> hmac(
    final Key key, final String alg) {
      return new Function<byte[],String>() {
        public String apply(byte[] input) {
          return hmac(key,alg,input);
        }
      };
  }
  
  public static Predicate<String> stringSignatureValid(
      final PublicKey key, 
      final String alg, 
      final byte[] source) {
      return new Predicate<String>() {
        public boolean apply(String mat) {
          return sigval(key,alg,source,decodeBase64(mat));
        }
      };
    }
  
  public static Predicate<byte[]> signatureValid(
    final PublicKey key, 
    final String alg, 
    final byte[] source) {
    return new Predicate<byte[]>() {
      public boolean apply(byte[] mat) {
        return sigval(key,alg,source,mat);
      }
    };
  }
  
  public static Predicate<Pair<byte[],byte[]>> signatureValid(
      final PublicKey key, 
      final String alg) {
      return new Predicate<Pair<byte[],byte[]>>() {
        public boolean apply(Pair<byte[],byte[]> mat) {
          return sigval(key,alg,mat.one(),mat.two());
        }
      };
    }

  public static Predicate<Pair<byte[],byte[]>> hmacValid(
      final Key key, 
      final String alg) {
      return new Predicate<Pair<byte[],byte[]>>() {
        public boolean apply(Pair<byte[],byte[]> mat) {
          return hmacval(key,alg,mat.one(),mat.two());
        }
      };
    }
 
  public static Predicate<String> stringHmacValid(
      final Key key, 
      final String alg, 
      final byte[] source) {
      return new Predicate<String>() {
        public boolean apply(String mat) {
          return hmacval(key,alg,source,decodeBase64(mat));
        }
      };
    }
  
  public static Predicate<byte[]> hmacValid(
      final Key key, 
      final String alg, 
      final byte[] source) {
      return new Predicate<byte[]>() {
        public boolean apply(byte[] mat) {
          return hmacval(key,alg,source,mat);
        }
      };
    }
  
  public static String sig(PrivateKey key, String alg, byte[] mat) {
    try {
      Signature sig = Signature.getInstance(alg);
      sig.initSign((PrivateKey)key);
      sig.update(mat);
      byte[] dat = sig.sign();
      return encodeBase64URLSafeString(dat);
    } catch (Throwable t) {
      throw propagate(t);
    }
  }
  
  public static String hmac(Key key, String alg, byte[] mat) {
    try {
      Mac mac = Mac.getInstance(alg);
      mac.init(key);
      mac.update(mat,0,mat.length);
      byte[] sig = mac.doFinal();
      return encodeBase64URLSafeString(sig);
    } catch (Throwable t) {
      throw propagate(t);
    }
  }
  
  public static boolean sigval(PublicKey key, String alg, byte[] mat, byte[] dat) {
    try {
      Signature sig = Signature.getInstance(alg);
      sig.initVerify(key);
      sig.update(mat);
      return sig.verify(dat);
    } catch (Throwable t) {
      throw propagate(t);
    }
  }
  
  public static boolean hmacval(Key key, String alg, byte[] mat, byte[] dat) {
    try {
      Mac mac = Mac.getInstance(alg);
      mac.init(key);
      byte[] sig = mac.doFinal(mat);
      return Arrays.equals(sig, dat);
    } catch (Throwable t) {
      throw propagate(t);
    }
  }
    
}
