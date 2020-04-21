package com.kshitz.kshitz.entities.orders;

import javax.persistence.*;
import java.io.Serializable;

enum fromStatus{
    ORDERPLACED,
     CANCELLED,
     ORDERREJECTED,
     ORDERCONFIRMED,
    ORDERSHIPPED,
    DELIEVERED,
     RETURNREQUESTED,
     RETURNREJECTED,
     RETURNAPPROVED,
    PICKUPINITIATED,
     PICKUPCOMPLETED,
     REFUNDINTIATED,
     REFUNDCOMPLETED

}
enum toStatus{
    CANCELLEDAFTERPLACED(com.kshitz.kshitz.entities.orders.fromStatus.ORDERPLACED),
    ORDERCONFIRMED(com.kshitz.kshitz.entities.orders.fromStatus.ORDERPLACED),
    ORDERREJECTED(com.kshitz.kshitz.entities.orders.fromStatus.ORDERPLACED),
    REFUNDINITIATED(com.kshitz.kshitz.entities.orders.fromStatus.CANCELLED),
    CLOSEDAFTERCANCELLED(com.kshitz.kshitz.entities.orders.fromStatus.CANCELLED),
    REFUNDINITIATEDAFTERORDERREJECTED(com.kshitz.kshitz.entities.orders.fromStatus.ORDERREJECTED),
    CLOSEDANDDELIEVERED(com.kshitz.kshitz.entities.orders.fromStatus.ORDERREJECTED),
    CANCELLEDAFTERCONFIRM(com.kshitz.kshitz.entities.orders.fromStatus.ORDERCONFIRMED),
    ORDERSHIPPED(com.kshitz.kshitz.entities.orders.fromStatus.ORDERCONFIRMED),
    DELIEVERED(com.kshitz.kshitz.entities.orders.fromStatus.ORDERSHIPPED),
    RETURENREQUESTED(com.kshitz.kshitz.entities.orders.fromStatus.DELIEVERED),
    CLOSEDAFTERDELIEVERED(com.kshitz.kshitz.entities.orders.fromStatus.DELIEVERED),
    RETURENREJECTED(com.kshitz.kshitz.entities.orders.fromStatus.RETURNREQUESTED),
    RETURNAPPROVED(com.kshitz.kshitz.entities.orders.fromStatus.RETURNREQUESTED),
    CLOSEDAFTERRETURNREJECTED(com.kshitz.kshitz.entities.orders.fromStatus.RETURNREJECTED),
    PICKUPINITIATED(com.kshitz.kshitz.entities.orders.fromStatus.RETURNAPPROVED),
    PICKUPCOMPLETED(com.kshitz.kshitz.entities.orders.fromStatus.PICKUPINITIATED),
    REFUNDINITIATEDAFTERPICKUP(com.kshitz.kshitz.entities.orders.fromStatus.PICKUPCOMPLETED),
    REFUNDCOMPLETED(com.kshitz.kshitz.entities.orders.fromStatus.REFUNDINTIATED),
    CLOSEDAFTERREFUNDCOMPLETE(com.kshitz.kshitz.entities.orders.fromStatus.REFUNDCOMPLETED);

    private fromStatus fromStatus;

    private toStatus(fromStatus fromStatus){
        this.fromStatus = fromStatus;
    }

public fromStatus toFromStatus(){
        return fromStatus;
}

}


@Entity
public class OrderStatus implements Serializable {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderProductId",referencedColumnName = "id")
    private OrderProduct orderProduct;
    @Enumerated(EnumType.STRING)
    private fromStatus fromStatus;
    @Enumerated(EnumType.STRING)
    private  toStatus toStatus;
    private String transitionNotesComments;

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public com.kshitz.kshitz.entities.orders.fromStatus getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(com.kshitz.kshitz.entities.orders.fromStatus fromStatus) {
        this.fromStatus = fromStatus;
    }

    public com.kshitz.kshitz.entities.orders.toStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(com.kshitz.kshitz.entities.orders.toStatus toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransitionNotesComments() {
        return transitionNotesComments;
    }

    public void setTransitionNotesComments(String transitionNotesComments) {
        this.transitionNotesComments = transitionNotesComments;
    }
}
