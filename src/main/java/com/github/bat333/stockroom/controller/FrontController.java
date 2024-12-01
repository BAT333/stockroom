package com.github.bat333.stockroom.controller;

import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class FrontController {
    @Autowired
    private SectorService service;
    @GetMapping("/form")
    public String index(Model model) {
        Page<DataAllSector> sectors = service.getAll(PageRequest.of(0, Integer.MAX_VALUE));

        // Adicionar setores ao modelo
        model.addAttribute("sectors", sectors.getContent());

        // Adicionar um objeto vazio de peça para o formulário
        model.addAttribute("partnew", new DataPart("", null, 0.0));
        model.addAttribute("sector", new DataSector("", "", ""));
        return "form";  // Nome do arquivo HTML, sem a extensão
    }

}
