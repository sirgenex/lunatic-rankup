package br.com.lunaticmc.rankup.manager;

import lombok.Getter;

import java.text.DecimalFormat;

public class FormatManager {

    @Getter private static final FormatManager instance = new FormatManager();

    private final String[] suffix = new String[]{"","K", "M", "B", "T", "Q", "QQ"};

    public String format(double number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while(r.length() > 5 || r.matches("[0-9]+\\.[a-z]")) r = r.substring(0, r.length()-2) + r.substring(r.length() - 1);
        return r;
    }

}
