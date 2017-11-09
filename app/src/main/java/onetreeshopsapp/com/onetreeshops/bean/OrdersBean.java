package onetreeshopsapp.com.onetreeshops.bean;


import java.io.Serializable;
import java.util.List;

import onetreeshopsapp.com.onetreeshops.utils.NumberFormatUtil;


/**
 * Created by admin on 2016-05-24.
 */
public class OrdersBean implements Serializable{
    private double amount_paid;
    private double amount_return;
    private double amount_tota;
    private String name;
    private String pos_session_id;
    private String terminal;
    private int user_id;
    /**
     * price_unit : 30
     * product_id : 1
     * qty : 5
     */

    private List<LinesBean> lines;
    /**
     * amount : 150
     * journal_id : 8
     * name : 2016-05-11 7:16:14
     * statement_id : 240
     */

    private List<StatementIdsBean> statement_ids;





    public double getAmount_paid() {
        return NumberFormatUtil.formatToDouble2(amount_paid);
    }

    public void setAmount_paid(double amount_paid) {
        this.amount_paid = NumberFormatUtil.formatToDouble2(amount_paid);
    }

    public double getAmount_return() {
        return NumberFormatUtil.formatToDouble2(amount_return);
    }

    public void setAmount_return(double amount_return) {
        this.amount_return =  NumberFormatUtil.formatToDouble2(amount_return);
    }

    public double getAmount_tota() {
        return NumberFormatUtil.formatToDouble2(amount_tota);
    }

    public void setAmount_tota(double amount_tota) {
        this.amount_tota =  NumberFormatUtil.formatToDouble2(amount_tota);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPos_session_id() {
        return pos_session_id;
    }

    public void setPos_session_id(String pos_session_id) {
        this.pos_session_id = pos_session_id;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<LinesBean> getLines() {
        return lines;
    }

    public void setLines(List<LinesBean> lines) {
        this.lines = lines;
    }

    public List<StatementIdsBean> getStatement_ids() {
        return statement_ids;
    }

    public void setStatement_ids(List<StatementIdsBean> statement_ids) {
        this.statement_ids = statement_ids;
    }

}
