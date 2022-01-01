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
public class CameraTopOverflow extends CameraOverflow {
    int cellLength;
    public CameraTopOverflow(Position position, int shift, int cellLength) {
        super(position, shift);
        this.cellLength = cellLength;
    }

    @Override
    protected int getOverflow() {
        return Math.abs(( position.getX() + shift) >= cellLength - 1
                ?  cellLength - 2 - (position.getX() + shift) 
                : 0);
    }
    
}
