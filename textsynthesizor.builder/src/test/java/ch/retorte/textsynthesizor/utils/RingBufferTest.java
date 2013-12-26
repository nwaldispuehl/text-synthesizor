package ch.retorte.textsynthesizor.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test cases for the {@link RingBuffer}.
 * 
 * @author nw
 */
public class RingBufferTest {

  private Object objectA = new Object();
  private Object objectB = new Object();

  @Test
  public void shouldInstantiate() {
    new RingBuffer<Object>(0);
  }

  @Test
  public void shouldAcceptItemsEvenIfOfZeroLength() {
    new RingBuffer<Object>(0).addItem(objectA);
  }

  @Test
  public void shouldReturnObjectsInRightOrder() {
    // given
    int bufferSize = 2;
    RingBuffer<Object> buffer = new RingBuffer<Object>(bufferSize);

    // when
    buffer.addItem(objectA);
    buffer.addItem(objectB);

    // then
    assertThat(buffer.getLatestItems().size(), is(bufferSize));
    assertThat(buffer.getLatestItems().get(0), is(objectA));
    assertThat(buffer.getLatestItems().get(1), is(objectB));
  }

  @Test
  public void shouldOverwriteOldObjects() {
    // given
    int bufferSize = 1;
    RingBuffer<Object> buffer = new RingBuffer<Object>(bufferSize);

    // when
    buffer.addItem(objectA);
    buffer.addItem(objectB);

    // then
    assertThat(buffer.getLatestItems().size(), is(bufferSize));
    assertThat(buffer.getLatestItems().get(0), is(objectB));
  }
}
