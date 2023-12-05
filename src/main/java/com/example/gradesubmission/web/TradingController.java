package com.example.gradesubmission.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gradesubmission.dto.BidProcessingStatusType;
import com.example.gradesubmission.dto.ForebayBidQuery;
import com.example.gradesubmission.dto.ForebayBidSetType;
import com.example.gradesubmission.dto.ForebayBidSubmit;
import com.example.gradesubmission.service.ForebayService;
import com.example.utils.LogExecutionTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/trading")
public class TradingController {

    private static final Logger LOG = LoggerFactory.getLogger(TradingController.class);

    ForebayService forebayService;

    @PostMapping("/forebayQuery")
    @LogExecutionTime
    public ResponseEntity<ForebayBidSetType> forebayQuery(@RequestBody ForebayBidQuery forebayQuery) {
        LOG.info("ForebayName {}", forebayQuery.getForebayBidQuery().getForebayName());
        ForebayBidSetType forebaySet = forebayService.retrieveForebay(forebayQuery);
        return new ResponseEntity<>(forebaySet, HttpStatus.CREATED);
    }

    @PostMapping("/forebaySubmit")
    public ResponseEntity<BidProcessingStatusType> forebaySubmit(@RequestBody ForebayBidSubmit forebaySubmit) {
        LOG.info("ForebayName {}", forebaySubmit.getForebayBidSubmit().getForebayName());
        forebayService.persistForebay(forebaySubmit);
        BidProcessingStatusType bpst = new BidProcessingStatusType();
        return new ResponseEntity<>(bpst, HttpStatus.CREATED);
    }

}