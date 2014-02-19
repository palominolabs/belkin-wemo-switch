package com.palominolabs.wemo;

public class App {
    public static void main(String[] args) throws Exception {
        try (InsightSwitchFinder isf = new InsightSwitchFinder("Power Testing Drive")) {
            isf.findSwitches();
        }
    }
}
