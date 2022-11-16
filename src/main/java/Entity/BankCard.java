package Entity;


public class BankCard {
    public String username;
    public String cardNo;
    //有效期
    public String indate;
    //验证码
    public String vCode;

    public BankCard(String username, String cardNo, String indate, String vCode) {
        this.username = username;
        this.cardNo = cardNo;
        this.indate = indate;
        this.vCode = vCode;
    }

    /**
     * 格式化数据
     *
     * @return
     */
    public String format() {
        return username + "," + cardNo + "," + indate +
                "," + vCode;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "username='" + username + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", indate='" + indate + '\'' +
                ", vCode='" + vCode + '\'' +
                '}';
    }
}
