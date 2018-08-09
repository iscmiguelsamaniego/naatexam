package org.samtech.naatexamen.model.database;

public class Sales {
    public static final String TABLE_NAME = "sales";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BC_NAME = "brandorcompanyname";
    public static final String COLUMN_CELL_NUMBER = "cellnumber";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CONCEPT = "concept";

    private int id;
    private String bcname;
    private String cellnumber;
    private String amount;
    private String concept;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BC_NAME + " TEXT,"
                    + COLUMN_CELL_NUMBER + " TEXT,"
                    + COLUMN_AMOUNT + " TEXT,"
                    + COLUMN_CONCEPT + " TEXT"
                    + ")";

    public Sales() {
    }

    public Sales(int id, String bcname, String cellnumber, String amount, String concept) {
        this.id = id;
        this.bcname = bcname;
        this.cellnumber = cellnumber;
        this.amount = amount;
        this.concept = concept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBcname() {
        return bcname;
    }

    public void setBcname(String bcname) {
        this.bcname = bcname;
    }

    public String getCellnumber() {
        return cellnumber;
    }

    public void setCellnumber(String cellnumber) {
        this.cellnumber = cellnumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }
}
