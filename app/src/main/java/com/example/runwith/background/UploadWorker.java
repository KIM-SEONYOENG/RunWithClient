package com.example.runwith.background;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.runwith.domain.StepCount;

public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    StepCount step = StepCount.getInstance();
    int count;

    @NonNull
    @Override
    public Result doWork() {
        count = step.getCount();
        step.setCount(0);

        return null;
    }
}
