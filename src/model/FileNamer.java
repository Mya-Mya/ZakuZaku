package model;

public interface FileNamer {
    String getNext(String before);
    String getFirst();
}
