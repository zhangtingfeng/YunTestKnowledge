package cn.eggsoft.sp34pricereport.excel;


public class intField{
    private  double posRow;
    private  String FiledName;

    public double getPosRow() {
        return posRow;
    }

    public String getFiledName() {
        return FiledName;
    }

    public void setFiledName(String filedName) {
        FiledName = filedName;
    }

    public void setPosRow(double posRow) {
        this.posRow = posRow;
    }

    @Override
    public String toString() {
        return "intField{" +
                "posRow=" + posRow +
                ", FiledName='" + FiledName + '\'' +
                '}';
    }
    public intField() {
        // Create new Animal instance.

    }

    public intField(int posRow, String FiledName) {
        // Create new Animal instance.
        this.posRow = posRow;
        this.FiledName = FiledName;
    }
}
