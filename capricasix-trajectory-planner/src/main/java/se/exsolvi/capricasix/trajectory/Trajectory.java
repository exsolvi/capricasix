package se.exsolvi.capricasix.trajectory;

import java.util.concurrent.CompletableFuture;

public interface Trajectory {
  
  public CompletableFuture<Void> move(int positionInDegrees);
  
}