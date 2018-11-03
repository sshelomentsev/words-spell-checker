package com.sinergy.model;

public class Result {

    private final int id;

    private String name;
    private String surname;
    private String thirdName;

    private int nameOrdinal;
    private int surnameOrdinal;
    private int thirdNameOrdinal;

    public Result(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public int getNameOrdinal() {
        return nameOrdinal;
    }

    public void setNameOrdinal(int nameOrdinal) {
        this.nameOrdinal = nameOrdinal;
    }

    public int getSurnameOrdinal() {
        return surnameOrdinal;
    }

    public void setSurnameOrdinal(int surnameOrdinal) {
        this.surnameOrdinal = surnameOrdinal;
    }

    public int getThirdNameOrdinal() {
        return thirdNameOrdinal;
    }

    public void setThirdNameOrdinal(int thirdNameOrdinal) {
        this.thirdNameOrdinal = thirdNameOrdinal;
    }

    @Override
    public String toString() {
        int ordinal = Math.max(nameOrdinal, Math.max(surnameOrdinal, thirdNameOrdinal));

        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(",");
        sb.append(ordinal);
        sb.append(",");

        if (1 == ordinal) {
            sb.append(surname);
            if (null != name) {
                sb.append(name);
                sb.append(" ");
            }
            if (null != thirdName) {
                sb.append(thirdName);
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
