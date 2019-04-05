package com.wechat.weather;

public class Result {

    private Sk sk;
    private Today today;
    private Future[] future;

    public Sk getSk() {
        return sk;
    }

    public void setSk(Sk sk) {
        this.sk = sk;
    }

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public Future[] getFuture() {
        return future;
    }

    public void setFuture(Future[] future) {
        this.future = future;
    }
}
