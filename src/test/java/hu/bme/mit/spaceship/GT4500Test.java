package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private TorpedoStore mockTSp;
  private TorpedoStore mockTSs;
  private GT4500 ship;

  @BeforeEach
  public void init(){
    mockTSp = mock(TorpedoStore.class);
    mockTSs = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTSp, mockTSs);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange

    when(mockTSp.fire(1)).thenReturn(true);
    // when(mockTSp.isEmpty()).thenReturn(false);
    // when(mockTSp.getTorpedoCount()).thenReturn(10);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTSp, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTSp.fire(1)).thenReturn(true);
    when(mockTSs.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSs, times(1)).fire(1);
  }

  @Test
  public void does_not_fire_empty(){
    // Arrange
    when(mockTSp.isEmpty()).thenReturn(true);
    when(mockTSs.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockTSp, times(0)).fire(1);
    verify(mockTSs, times(0)).fire(1);
  }

  @Test
  public void fires_other_when_empty(){
    // Arrange
    when(mockTSp.isEmpty()).thenReturn(true);
    when(mockTSs.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTSp, times(0)).fire(1);
    verify(mockTSs, times(1)).fire(1);
  }

  @Test
  public void fire_single_twice_both_has(){
    // Arrange
    when(mockTSp.fire(1)).thenReturn(true);
    when(mockTSs.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSs, times(1)).fire(1);
  }


  @Test
  public void fire_single_twice_first_has_not(){
    // Arrange
    when(mockTSp.isEmpty()).thenReturn(true);
    when(mockTSs.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTSp, times(0)).fire(1);
    verify(mockTSs, times(2)).fire(1);
  }

  @Test
  public void fire_single_twice_second_has_not(){
    // Arrange
    when(mockTSs.isEmpty()).thenReturn(true);
    when(mockTSp.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockTSs, times(0)).fire(1);
    verify(mockTSp, times(2)).fire(1);
  }

  @Test
  public void does_not_fire_when_failing(){
    // Arrange
    when(mockTSp.fire(1)).thenReturn(false);
    when(mockTSs.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSs, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Fails(){
    // Arrange
    when(mockTSp.fire(1)).thenReturn(false);
    when(mockTSs.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockTSp, times(1)).fire(1);
    verify(mockTSs, times(1)).fire(1);
  }

}
