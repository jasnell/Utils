package snell.utils;

import com.google.common.base.Objects;

public final class IntPair {

  public static IntPair of(int v1, int v2) {
    return new IntPair(v1,v2);
  }
  
  private final int v1;
  private final int v2;
  private transient int hash = 1;
  
  IntPair(int v1, int v2) {
    this.v1 = v1;
    this.v2 = v2;
    this.hash = 
      hashCode();
  }
  
  public IntPair swap() {
    return of(v2,v1);
  }
  
  public int one() {
    return v1;
  }
  
  public int two() {
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) 
      return false;
    IntPair other = (IntPair) obj;
    return 
      v1 == other.v1 &&
      v2 == other.v2; 
  }
}
