package com.raphaowl.financialowl.controllers;

import com.raphaowl.financialowl.services.SummaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/summary")
@AllArgsConstructor
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    @GetMapping
    public String getSummary(@RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year,
                             Model model) {
        model.addAttribute("summary", summaryService.getSummary(year));
        model.addAttribute("year", year);
        return "summary/index";
    }
}
