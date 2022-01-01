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
public class CameraRightOverflow extends CameraOverflow {
    public CameraRightOverflow(Position position, int shift) {
        super(position, shift);
    }

    @Override
    protected int getOverflow() {
        return ( position.getY() - shift) < 0 ? 
                Math.abs(( position.getY() - shift)) 
                : 0;
    }
    
}
