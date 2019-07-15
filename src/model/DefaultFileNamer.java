package model;

public class DefaultFileNamer implements FileNamer {
    @Override
    public String getNext(String before) {
        String out = before;
        try {
            int num = Integer.parseInt(before);
            num++;
            out = Integer.toString(num);
            while (out.length() < 2) {
                out = "0" + out;
            }
        } catch (NumberFormatException e) {
        }
        return out;
    }

    @Override
    public String getFirst() {
        return "01";
    }
}
