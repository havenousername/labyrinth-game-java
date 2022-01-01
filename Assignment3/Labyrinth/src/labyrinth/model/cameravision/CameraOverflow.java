/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth.model.cameravision;

import labyrinth.model.Position;

/**
 *
 * @author andreicristea
 */
public abstract class CameraOverflow {
    protected Position position;
    protected int shift;
    protected CameraOverflow(Position position, int shift) {
        this.position = position;
        this.shift = shift;
    }
    
    protected abstract int getOverflow();
    public int getCellShiftSize() {
      return this.shift +  getOverflow();  
    }
}
