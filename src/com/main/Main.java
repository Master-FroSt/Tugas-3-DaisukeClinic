package com.main;

import com.handler.PatientRecordService;
import com.handler.PatientSearchTreeService;
import com.system.Helper;
import com.system.MainMenu;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PatientRecordService.makePatientRecord();
        PatientSearchTreeService.makePatientTree();
        PatientRecordService.loadPatientTXT();
        MainMenu.run(scanner);
    }
}
