package com.smartsoft.main.controller;

import com.smartsoft.main.service.CSVService;
import com.smartsoft.main.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
public class MainController {

    private DBService dbService;

    @Autowired
    public MainController(DBService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/")
    public String root(Model model) {
        model.addAttribute("message", "Добро пожаловать!");
        return "main";
    }

    @GetMapping("/last")
    public String last(Model model) {
        model.addAttribute("headers", new String[]{"SSOID", "ID форм(ы)"});
        model.addAttribute("colWidth", 6);
        //Date now = new Date();
        var now = new GregorianCalendar(2017, 6, 11, 9, 0).getTime();
        var past = new Date(now.getTime() - 3600 * 1000);

        var usersForms = dbService.formsByTime(past, now);
        model.addAttribute("records", usersForms.stream().map(uf -> new String[]{uf.getSsoId(), uf.formIdsToString()}).toArray());
        return "main";
    }

    @GetMapping("/unfinished")
    public String unfinished(Model model) {
        model.addAttribute("headers", new String[]{"SSOID", "ID форм(ы)", "Состояние"});
        model.addAttribute("colWidth", 4);
        model.addAttribute("records", dbService.unfinishedForms().stream().map(uf -> new String[]{uf.getSsoid(), uf.getFormid(), uf.getSubtype()}).toArray());
        return "main";
    }

    @GetMapping("/top")
    public String top(Model model) {
        model.addAttribute("headers", new String[]{"ID форм(ы)", "Количество"});
        model.addAttribute("colWidth", 6);
        model.addAttribute("records", dbService.topForms().entrySet().stream().map(uf -> new String[]{uf.getKey(), uf.getValue().toString()}).toArray());
        return "main";
    }

    @GetMapping("/loadCSV")
    public String loadCSV(Model model) {
        try {
            dbService.deleteAll();
            dbService.addAll(CSVService.parseCSVFileToRecords());
            model.addAttribute("message", "Готово");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Ошибка");
        }
        return "main";
    }
}
