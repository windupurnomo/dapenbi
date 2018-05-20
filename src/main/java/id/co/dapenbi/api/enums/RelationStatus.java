package id.co.dapenbi.api.enums;

public enum RelationStatus {
    I(0), A1(1), A2(2);

    private int value;

    RelationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RelationStatus parse(int id) {
        RelationStatus status = null;
        for (RelationStatus item : RelationStatus.values()) {
            if (item.getValue() == id) {
                status = item;
                break;
            }
        }
        if (status == null) return null;
        else return status;
    }
}