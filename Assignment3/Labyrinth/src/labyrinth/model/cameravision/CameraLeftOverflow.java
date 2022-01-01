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
public class CameraLeftOverflow extends CameraOverflow {
    int cellLength;
    public CameraLeftOverflow(Position position, int shift, int cellLength) {
        super(position, shift);
        this.cellLength = cellLength;
    }

    @Override
    protected int getOverflow() {
       return Math.abs(( position.getY() + shift) > cellLength - 1 ? 
                cellLength - 1 - (position.getY() + shift) 
                : 0);
    }
}
