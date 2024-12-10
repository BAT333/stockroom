package com.github.bat333.stockroom.controller;

import com.github.bat333.stockroom.model.*;
import com.github.bat333.stockroom.service.PartService;
import com.github.bat333.stockroom.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@RequestMapping("/ui")
public class FrontController {

    @Autowired
    private SectorService sectorService;

    @Autowired
    private PartService partService;

    private final ErrorAttributes errorAttributes;

    public FrontController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping("/form")
    public String index(Model model) {
        Page<DataAllSector> sectors = sectorService.getAll(PageRequest.of(0, Integer.MAX_VALUE));
        model.addAttribute("sectors", sectors.getContent());
        model.addAttribute("partnew", new DataPart("", null, 0.0));
        model.addAttribute("sector", new DataSector("", "", ""));
        return "form";
    }

    @GetMapping("/search")
    public String searchParts(
            @RequestParam(name = "cod", required = false) Long cod,
            @RequestParam(name = "name", required = false) String name,
            Model model) {

        Page<DataAllPart> parts = partService.search(
                cod,
                (name == null || name.isEmpty()) ? null : name,
                PageRequest.of(0, Integer.MAX_VALUE)
        );

        model.addAttribute("parts", parts.getContent());
        return "search";
    }

    @GetMapping("/part/{id}")
    public String showPartDetails(@PathVariable Long id, Model model) {
        model.addAttribute("part", partService.get(id));
        return "part-details";
    }

    @GetMapping("/delete/{id}")
    public String deletePartDetails(@PathVariable Long id) {
        partService.delete(id);
        return "redirect:/ui/search";
    }

    @GetMapping("/update/{id}")
    public String updatePartDetails(@PathVariable Long id, Model model) {
        Page<DataAllSector> sectors = sectorService.getAll(PageRequest.of(0, Integer.MAX_VALUE));
        DataAllPart part = partService.get(id);
        DataAllSector currentSector = sectorService.getSector(part.sector().id());

        model.addAttribute("sectors", sectors.getContent());
        model.addAttribute("sector", new DataSector("", "", ""));
        model.addAttribute("part", part);
        model.addAttribute("currentSector", currentSector);

        return "part-update.html";
    }

    @RequestMapping("/error")
    public String handleError(WebRequest webRequest, Model model) {
        Map<String, Object> errors = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        int status = (int) errors.getOrDefault("status", 500);

        model.addAttribute("status", status);
        model.addAttribute("error", errors.getOrDefault("error", "Erro"));
        model.addAttribute("message", errors.getOrDefault("message", "Página não encontrada."));

        return status == 404 ? "404" : "error";
    }
}
