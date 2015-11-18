package org.entando.entando.plugins.jpenglo.aps.system.services.card.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author entando
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({JaxbCard.class})
public class JaxbList {

    public JaxbCard[] getCards() {
        return cards;
    }

    public void setCards(JaxbCard[] cards) {
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getIdBoard() {
        return idBoard;
    }

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    public Double getPos() {
        return pos;
    }

    public void setPos(Double pos) {
        this.pos = pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    private JaxbCard cards[];
    private String name;
    private Boolean closed;
    private String idBoard;
    private Double pos;
    private String id;
}
