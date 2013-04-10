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
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Hex.*;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;

public abstract class KeyBase 
  implements Supplier<String> {

  public static final String DEFAULT_ALG="HmacSHA256";
  public static final int DEFAULT_SIZE=256;
  
  protected final Key key;
  protected final String alg;
  protected final int size;
  
  public abstract String generateNext();
  
  public String get() {
    return generateNext();
  }
  
  public KeyBase(Key key) {
    this(key,DEFAULT_ALG,DEFAULT_SIZE);
  }
  
  public KeyBase(Key key, int size) {
    this(key,DEFAULT_ALG,size);
  }
  
  public KeyBase(Key key, String alg, int size) { 
    this.key = key;
    this.alg = alg;
    this.size = size;
  }
  
  public KeyBase(String key) {
    this(key,DEFAULT_ALG,DEFAULT_SIZE);
  }
  
  public KeyBase(String key, int size) {
    this(key,DEFAULT_ALG,size);
  }
  
  public KeyBase(byte[] key, String alg, int size) {
    this.key = secret(key);
    this.alg = alg;
    this.size = size;
  }
  
  public KeyBase(byte[] key, int size) {
    this(key,DEFAULT_ALG,size);
  }
  
  public KeyBase(String key, String alg, int size) {
    this(dec(key),alg,size);
  }
  
  protected SecretKeySpec secret(byte[] key) {
    return new SecretKeySpec(key, "RAW");
  }
  
  protected static byte[] dec(String val) {
    try {
      return decodeHex(val.toCharArray());
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
  
  protected byte[] hmac(byte[]... mat){
    try {
      Mac hmac = Mac.getInstance(alg);
      hmac.init(key);
      for (byte[] m : mat)
        hmac.update(m);
      return hmac.doFinal();
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
  
  protected static byte[] randomBytes(int count) {
    SecureRandom random = new SecureRandom();
    byte[] buf = new byte[count];
    random.nextBytes(buf);
    return buf;
  }
  
  protected String pad(String s, int len, char c) {
    return Strings.padStart(s,len,c);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(alg, key);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    KeyBase other = (KeyBase) obj;
    if (!Objects.equal(alg,other.alg)) return false;
    if (!Objects.equal(key,other.key)) return false;
    if (size != other.size) return false;
    return true;
  }
  
}
