package no.hvl.dat110.ds.middleware;

import java.io.Serial;
import java.io.Serializable;

import no.hvl.dat110.ds.middleware.iface.OperationType;

/**
 * @author tdoy
 */

public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int clock;
    private final int processID;
    private OperationType optype;
    private boolean acknowledged = false;
    private double depositamount;
    private double interest;
    private double withdrawamount;

    public Message(int clock, int processID) {
        super();
        this.clock = clock;
        this.processID = processID;
    }

    public int getClock() {
        return clock;
    }

    public int getProcessID() {
        return processID;
    }

    public OperationType getOptype() {
        return optype;
    }

    public void setOptype(OperationType optype) {
        this.optype = optype;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public double getDepositamount() {
        return depositamount;
    }

    public void setDepositamount(double depositamount) {
        this.depositamount = depositamount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getWithdrawamount() {
        return withdrawamount;
    }

    public void setWithdrawamount(double withdrawamount) {
        this.withdrawamount = withdrawamount;
    }

}
