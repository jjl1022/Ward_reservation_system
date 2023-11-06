public class Column {
    protected String valueName;

    protected String valueType;

    public Column(String valueName, String valueType) {
        this.valueName = valueName;
        this.valueType = valueType;
    }

    public String getValueName() {
        return valueName;
    }

    public String getValueType() {
        return valueType;
    }

    public String toString() {
        return valueName + " " + valueType;
    }
}
