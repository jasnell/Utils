package snell.utils;

import com.google.common.base.Objects;

public class Triple<V1,V2,V3> {

  public static <V1,V2,V3>Triple<V1,V2,V3> of(V1 v1, V2 v2, V3 v3) {
    return new Triple<V1,V2,V3>(v1,v2,v3);
  }
  
  private final V1 v1;
  private final V2 v2;
  private final V3 v3;
  private transient int hash = 1;
  
  Triple(V1 v1, V2 v2, V3 v3) {
    this.v1 = v1;
    this.v2 = v2;
    this.v3 = v3;
  }
  
  public final Pair<Pair<V1,V2>,V3> splitLeft() {
    return Pair.of(Pair.of(v1,v2),v3);
  }
  
  public final Pair<V1,Pair<V2,V3>> splitRight() {
    return Pair.of(v1, Pair.of(v2,v3));
  }
  
  public final Triple<V3, V1, V2> rotateRight() {
	return of(v3,v1,v2);
  }
  
  public final Triple<V2, V3, V1> rotateLeft() {
    return of(v2,v3,v1);
  }
  
  public final Triple<V3, V2, V1> swap() {
	return of(v3,v2,v1);
  }
  
  public final Pair<Pair<V1,V2>,Pair<V2,V3>> split() {
    return Pair.of(Pair.of(v1,v2),Pair.of(v2,v3));
  }
  
  public final V1 one() {
    return v1;
  }
  
  public final V2 two() {
    return v2;
  }

  public final V3 three() {
    return v3;
  }
  
  public String toString() {
    return Objects.toStringHelper("Triple")
      .add("one", one())
      .add("two", two())
      .add("three", three())
      .toString();
  }

  @Override
  public int hashCode() {
    if (hash == 1)
      hash = Objects.hashCode(v1,v2,v3);
    return hash;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) 
      return false;
    Triple<V1,V2,V3> other = (Triple<V1,V2,V3>) obj;
    if (!Objects.equal(v1,other.v1) || 
        !Objects.equal(v2,other.v2) ||
        !Objects.equal(v3,other.v3)) 
      return false;
    return true;
  }
}
