package com.climbershangout.entities;

import java.util.EventListener;

/**
 * Created by lyuboslav on 11/22/2016.
 */

public interface ITimerListener extends EventListener {
    void OnTick(Period period, float currentTime);
    void OnFinish(boolean isCompleted);
}
