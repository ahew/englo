/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trello4j.gson;

public class GsonList {
    
    /** The name. */
    private String name;

    /** The closed. */
    private boolean closed;

    /** The id board. */
    private String idBoard;

    /** The pos. */
    private double pos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getIdBoard() {
        return idBoard;
    }

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    public double getPos() {
        return pos;
    }

    public void setPos(double pos) {
        this.pos = pos;
    }
    
    
    
}
