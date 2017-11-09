package onetreeshopsapp.com.onetreeshops.bean;

import java.io.Serializable;

/**
 * Created by admin on 2016-05-24.
 */
public class StatementIdsBean implements Serializable {
    private double amount;
    private int journal_id;
    private String name;
    private int statement_id;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getJournal_id() {
        return journal_id;
    }

    public void setJournal_id(int journal_id) {
        this.journal_id = journal_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatement_id() {
        return statement_id;
    }

    public void setStatement_id(int statement_id) {
        this.statement_id = statement_id;
    }
}
