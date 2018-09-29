package Action.srxinput;
import com.opensymphony.xwork2.ActionSupport;
import exp.Search;
import struct.Sequence;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by srx on 2017/5/18.
 */
public class user_search extends ActionSupport{
    private String dataset;
    private String k1;
    private String k2;
    private String threshold;
    private String tradeOff;
    private String qInput;
    private String algorithm;

    public String getDataset() {
        return this.dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getK1() {
        return this.k1;
    }

    public void setK1(String k1) {
        this.k1 = k1;
    }

    public String getK2() {
        return this.k2;
    }

    public void setK2(String k2) {
        this.k2 = k2;
    }

    public String getThreshold() {
        return this.threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getTradeOff() {
        return this.tradeOff;
    }

    public void setTradeOff(String tradeOff) {
        this.tradeOff = tradeOff;
    }

    public String getqInput() {
        return this.qInput;
    }

    public void setqInput(String qInput) {
        this.qInput = qInput;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getSearchInfo() throws Exception {
        String s = this.dataset+this.k1+this.k2+this.qInput+this.tradeOff+this.algorithm;
        System.out.print(s);
        return s;
    }

    public String execute() throws Exception {
       // getSearchInfo();
        HttpServletRequest request = ServletActionContext.getRequest ();
        ArrayList<Sequence> fnlResult = Search.searchInfo(this.dataset, Integer.parseInt(this.k1), Integer.parseInt(this.k2), Integer.parseInt(this.threshold), this.qInput, Float.parseFloat(this.tradeOff), this.algorithm);
        request.setAttribute("result",fnlResult);
        return SUCCESS;
    }

}
