package com.example.tubesp3b_2.presenter;

import java.io.BufferedReader;

public class ThreadView implements Runnable{
    private Thread thread;
    private UIHandler handler;

    //http login request needs
    private BufferedReader reader;
    String line;
}
