package com.investec.service;

public interface IPrinter {

    /**
     * Custom print format
     */
    String PRINTER_FORMAT = "%s: %s - %s - %s - %s - %s";

    String printAddress();
}
