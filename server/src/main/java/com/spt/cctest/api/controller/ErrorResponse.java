package com.spt.cctest.api.controller;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize(as = ErrorResponse.class)
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;
}