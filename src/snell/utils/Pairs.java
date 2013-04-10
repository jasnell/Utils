package snell.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public final class Pairs {

  private Pairs() {}
  
  public static <V1,V2>Map<V1,V2> asLinkedHashMap(
    Pair<V1,V2>... pairs) {
    checkNotNull(pairs);
    Map<V1,V2> map = Maps.newLinkedHashMap();
    for (Pair<V1,V2> pair : pairs)
      map.put(pair.one(),pair.two());
    return map;
  }  

  public static <V1,V2>Map<V1,V2> asHashMap(
    Pair<V1,V2>... pairs) {
    checkNotNull(pairs);
    Map<V1,V2> map = Maps.newHashMap();
    for (Pair<V1,V2> pair : pairs)
      map.put(pair.one(),pair.two());
    return map;
  }  
  
  public static <V1,V2>Map<V1,V2> asImmutableMap(
    Pair<V1,V2>... pairs) {
    checkNotNull(pairs);
    ImmutableMap.Builder<V1, V2> builder = 
      ImmutableMap.builder();
    for (Pair<V1,V2> pair : pairs)
      builder.put(pair.one(),pair.two());
    return builder.build();
  }
  
  public static <V1,V2,V3>Map<V1,Pair<V2,V3>> asImmutableMap(
    Triple<V1,V2,V3>...triples) {
    checkNotNull(triples);
    ImmutableMap.Builder<V1,Pair<V2,V3>> builder = 
      ImmutableMap.builder();
    for (Triple<V1,V2,V3> triple : triples) {
      Pair<V1,Pair<V2,V3>> split = 
        triple.splitRight();
      builder.put(split.one(), split.two());
    }
    return builder.build();
  }
  
  public static <V1,V2,V3>Map<V1,Pair<V2,V3>> asHashMap(
    Triple<V1,V2,V3>...triples) {
    checkNotNull(triples);
    Map<V1,Pair<V2,V3>> map = Maps.newHashMap();
    for (Triple<V1,V2,V3> triple : triples) {
      Pair<V1,Pair<V2,V3>> split = 
        triple.splitRight();
      map.put(split.one(), split.two());
    }
    return map;
  }
  
  public static <V1,V2,V3>Map<V1,Pair<V2,V3>> asLinkedHashMap(
    Triple<V1,V2,V3>...triples) {
    checkNotNull(triples);
    Map<V1,Pair<V2,V3>> map = Maps.newLinkedHashMap();
    for (Triple<V1,V2,V3> triple : triples) {
      Pair<V1,Pair<V2,V3>> split = 
        triple.splitRight();
      map.put(split.one(), split.two());
    }
    return map;
  }
}
