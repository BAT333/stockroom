package com.github.bat333.stockroom.controller;

import com.github.bat333.stockroom.model.DataAllPart;
import com.github.bat333.stockroom.model.DataAllSector;
import com.github.bat333.stockroom.model.DataPart;
import com.github.bat333.stockroom.model.DataSector;
import com.github.bat333.stockroom.service.PartService;
import com.github.bat333.stockroom.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
@Controller
@RequestMapping("/ui")
public class FrontController {
    @Autowired
    private SectorService service;
    @Autowired
    private PartService partService;

    private final ErrorAttributes errorAttributes;

    public FrontController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

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
    @GetMapping("/search")
    public String searchParts(
            @RequestParam(name = "cod", required = false) Long cod,
            @RequestParam(name = "name", required = false) String name,
            Model model) {
        System.out.println("cod: "+ cod +"  name:"+ name);

        Page<DataAllPart> parts =
                (name==null||name.isEmpty()&&cod ==null)? partService.search(null, null, PageRequest.of(0, Integer.MAX_VALUE))
                        :(!name.isEmpty() && cod==null)? partService.search(null, name, PageRequest.of(0, Integer.MAX_VALUE))
                        :( cod!=null && name==null || name.isEmpty())? partService.search(cod, null, PageRequest.of(0, Integer.MAX_VALUE)):
                        partService.search(cod, name, PageRequest.of(0, Integer.MAX_VALUE));


        model.addAttribute("parts", parts.getContent());
        return "search"; // Nome da página de resultados
    }

    @GetMapping("/part/{id}")
    public String showPartDetails(@PathVariable("id") Long id, Model model) {
        DataAllPart part = partService.get(id);  // Buscando a peça pelo ID
        model.addAttribute("part", part);  // Passando a peça para o modelo
        return "part-details";  // Nome da página de detalhes
    }
    @GetMapping("/delete/{id}")
    public String deletePartDetails(@PathVariable("id") Long id) {
        partService.delete(id);  // Remove a peça pelo ID
        return "redirect:/ui/search"; // Redireciona para a lista de peças ou outra página desejada
    }

    @GetMapping("/update/{id}")
    public String updatePartDetails(@PathVariable("id") Long id, Model model) {
        Page<DataAllSector> sectors = service.getAll(PageRequest.of(0, Integer.MAX_VALUE));

        model.addAttribute("sectors", sectors.getContent());
        model.addAttribute("sector", new DataSector("", "", ""));
        DataAllPart part = partService.get(id);
        DataAllSector currentSector = service.getSector(1L);
        model.addAttribute("part", part);
        model.addAttribute("currentSector", currentSector);
        return "part-update.html";
    }



    @RequestMapping("/error")
    public String handleError(WebRequest webRequest, Model model) {
        Map<String, Object> errors = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        int status = (int) errors.getOrDefault("status", 500);

        // Adicionar detalhes ao modelo
        model.addAttribute("status", status);
        model.addAttribute("error", errors.getOrDefault("error", "Erro"));
        model.addAttribute("message", errors.getOrDefault("message", "Página não encontrada."));

        // Redirecionar para a página 404
        if (status == 404) {
            return "404";
        }

        // Retornar outra página de erro genérica, se necessário
        return "error";
    }



}
