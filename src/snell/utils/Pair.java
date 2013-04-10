package snell.utils;

import com.google.common.base.Objects;

public class Pair<V1,V2> {

  public static <V1,V2>Pair<V1,V2> of(V1 v1, V2 v2) {
    return new Pair<V1,V2>(v1,v2);
  }
  
  private final V1 v1;
  private final V2 v2;
  private transient int hash = 1;
  
  Pair(V1 v1, V2 v2) {
    this.v1 = v1;
    this.v2 = v2;
  }
  
  public final Pair<V2,V1> swap() {
    return of(v2,v1);
  }
  
  public final V1 one() {
    return v1;
  }
  
  public final V2 two() {
    return v2;
  }
  
  public String toString() {
    return Objects.toStringHelper("Pair")
      .add("one", one())
      .add("two", two())
      .toString();
  }

  @Override
  public int hashCode() {
    if (hash == 1)
      hash = Objects.hashCode(v1,v2);
    return hash;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) 
      return false;
    Pair<V1,V2> other = (Pair<V1,V2>) obj;
    if (!Objects.equal(v1,other.v1) || 
        !Objects.equal(v2,other.v2)) 
      return false;
    return true;
  }
}
