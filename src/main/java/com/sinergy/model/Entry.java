package com.sinergy.model;

public class Entry {

    private final int id;
    private final String surname, name, thirdname;
    private final String country;

    public Entry(int id, String surname, String name, String thirdname, String country) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.thirdname = thirdname;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getThirdname() {
        return thirdname;
    }

    public String getCountry() {
        return country;
    }

    public static Entry valueOf(String line) {
        String parts[] = line.replaceAll("\"", "").split(",");

        Integer id = Integer.parseInt(parts[0]);

        String raw = parts[1].toUpperCase().trim().replaceAll("\\s+", " ");
        String nameParts[] = raw.split("\\s");
        String surname = nameParts[0];
        String name = 2 <= nameParts.length ? nameParts[1] : null;
        String thirdname = null;
        if (nameParts.length >= 3) {
            thirdname = "";
            for (int i = 2; i < nameParts.length; i++) {
                thirdname += nameParts[i];
            }
        }

        String country = 3 <= parts.length ? parts[2] : null;

        return new Entry(id, surname, name, thirdname, country);
    }
}
