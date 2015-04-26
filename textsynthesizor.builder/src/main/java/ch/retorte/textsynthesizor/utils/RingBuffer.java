package ch.retorte.textsynthesizor.utils;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Ring buffer for objects which has a certain size s and endlessly accepts items, but stores only the last s. Older items are just overwritten. Returns an
 * ordered list of the latest items, which is also of size s.
 * 
 * @author nw
 */
public class RingBuffer<T> {

  private T[] buffer;
  private int position = 0;
  private final int bufferSize;

  @SuppressWarnings("unchecked")
  public RingBuffer(int bufferSize) {
    this.bufferSize = bufferSize;
    buffer = (T[]) Array.newInstance(Object.class, bufferSize);
  }

  public void addItem(T t) {
    if (0 < bufferSize) {
      buffer[position] = t;
      incrementModularPositionCounter();
    }
  }

  private void incrementModularPositionCounter() {
    position = (position + 1) % bufferSize;
  }

  public List<T> getLatestItems() {
    List<T> result = newArrayList();
    for (int i = 0; i < bufferSize; i++) {
      T t = buffer[(position + i) % bufferSize];
      if (t != null) {
        result.add(t);
      }
    }
    return result;
  }
}
