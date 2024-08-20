package com.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.report.entity.CitizenPlan;
import com.report.request.SearchRequest;
import com.report.service.ReportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class ReportController {
    @Autowired
    private ReportService service;
    
    @GetMapping("/")
    public String getIndexPage(Model model){
        model.addAttribute("search", new SearchRequest());
        init(model);
        return "index";
    }


    
    @PostMapping("/search")
    public String handleSearch(@ModelAttribute("search") SearchRequest request, Model model){

        System.out.println(request);

        List<CitizenPlan> planslist = service.search(request);
        model.addAttribute("planslist", planslist);
        init(model);

        return "index";
    }

    private void init(Model model){

        model.addAttribute("planNames", service.getPlanNames());
        model.addAttribute("planStatus", service.getPlanStatuses());

    }
}
