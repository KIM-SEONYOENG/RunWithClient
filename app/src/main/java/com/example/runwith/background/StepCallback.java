package com.example.runwith.background;

public interface StepCallback {
    public void onUnbindService();
    public void onStepCallback(int count);
}
