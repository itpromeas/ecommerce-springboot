package com.meas.measecommerce.api.dtos;

public class DataChange<T> {

    /** The ChangeType. */
    private ChangeType changeType;
    /** The data being changed. */
    private T data;

    /**
     * Default constructor.
     */
    public DataChange() {
    }

    /**
     * Creates an instance.
     * @param changeType The ChangeType.
     * @param data The data changed.
     */
    public DataChange(ChangeType changeType, T data) {
        this.changeType = changeType;
        this.data = data;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * Enum to specify what kind of change is taking place.
     */
    public enum ChangeType {
        INSERT,
        UPDATE,
        DELETE
    }

}