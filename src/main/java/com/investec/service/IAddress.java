package com.investec.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.investec.BusinessException;

public interface IAddress {

    String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    String prettyPrintAddress() throws JsonProcessingException, BusinessException;

    String formattedAddress();
}
