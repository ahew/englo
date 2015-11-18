/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trello4j.model;

public class CustomList extends List{
    
    private java.util.List<Card> cards;

    public java.util.List<Card> getCards() {
        return cards;
    }

    public void setCards(java.util.List<Card> cards) {
        this.cards = cards;
    }
        
}
