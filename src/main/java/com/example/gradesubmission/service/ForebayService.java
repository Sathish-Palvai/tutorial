package com.example.gradesubmission.service;

import com.example.gradesubmission.dto.ForebayBidQuery;
import com.example.gradesubmission.dto.ForebayBidSetType;
import com.example.gradesubmission.dto.ForebayBidSubmit;

public interface ForebayService {

    ForebayBidSetType retrieveForebay(ForebayBidQuery forebayQuery);

    boolean persistForebay(ForebayBidSubmit forebaySubmit);

}
