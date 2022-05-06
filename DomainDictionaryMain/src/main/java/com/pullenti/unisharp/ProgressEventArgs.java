package com.pullenti.unisharp;

public class ProgressEventArgs {
    public ProgressEventArgs(int p, Object st) { perc = p; stat = st; }
    private int perc;
    private Object stat;
    public int getProgressPercentage() { return perc; }
    public Object getUserState() { return stat; }
}
