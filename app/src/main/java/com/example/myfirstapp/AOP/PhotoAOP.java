package com.example.myfirstapp.AOP;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class PhotoAOP
{
    long startTime, endTime;
    @Before("execution(MainActivity takePhoto(..))")
    public void before(JoinPoint joinPoint)
    {
        startTime = System.nanoTime();
        Log.println(Log.ASSERT, "ASPECTJ", "Taking a photo");
    }

    @After("execution(MainActivity takePhoto(..))")
    public void after(JoinPoint joinPoint)
    {
        endTime = System.nanoTime() - startTime;
        Log.println(Log.ASSERT, "ASPECTJ", "Done taking a photo. Time: " + endTime + " seconds");
    }
}