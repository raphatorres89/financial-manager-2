package com.raphaowl.financialowl.controllers;

import com.raphaowl.financialowl.dtos.MovementDTO;
import com.raphaowl.financialowl.dtos.SuccessAlert;
import com.raphaowl.financialowl.services.MovementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/movements")
@AllArgsConstructor
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping({"", "/"})
    public String findAll(@RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().getMonthValue()}") Integer month,
                         @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year,
                         Model model) {
        log.info("Request - Movements");
        if (month < 1) {
            month = 12;
            year--;
        }
        if (month > 12) {
            month = 1;
            year++;
        }
        PageRequest pageRequest = PageRequest.of(0, 30, Sort.by("dueDate"));

        model.addAttribute("actualDate", LocalDate.now());
        model.addAttribute("movements", movementService.findByYearMonth(year, month, pageRequest));
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        return "movements/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("movementDTO", new MovementDTO());
        return "movements/details";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("movementDTO", movementService.findById(id));
        return "movements/details";
    }

    @PostMapping({"", "/"})
    public String save(@Valid MovementDTO movementDTO, BindingResult result, RedirectAttributes redirectAttrs) {
        log.info("Saving movement: {}", movementDTO);

        validate(movementDTO, result);

        if (result.hasErrors()) {
            log.error("Error validating movement: {}, {}", movementDTO, result.getAllErrors());
            return "movements/details";
        }

        MovementDTO newMovement = movementService.save(movementDTO);
        redirectAttrs.addFlashAttribute("alertDTO", new SuccessAlert(String.format("Movimento %s criado com sucesso!", newMovement.getName())));

        return "redirect:movements";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @Valid MovementDTO movementDTO, BindingResult result, RedirectAttributes redirectAttrs) {
        log.info("Updating movement by id: {}, {}", id, movementDTO);

        validate(movementDTO, result);

        if (result.hasErrors()) {
            return "movements/details";
        }

        movementService.update(id, movementDTO);
        redirectAttrs.addFlashAttribute("alertDTO", new SuccessAlert(String.format("Movimento %s criado com sucesso!", movementDTO.getName())));

        return "redirect:/movements";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttrs) {
        log.info("Deleting movement by id: {}", id);
        movementService.delete(id);
        redirectAttrs.addFlashAttribute("alertDTO", new SuccessAlert(String.format("Movimento %s excluído com sucesso!", id)));

        return "redirect:";
    }

    private void validate(MovementDTO movementDTO, BindingResult result) {
        if (Objects.nonNull(movementDTO.getInstallment())) {
            if (Objects.isNull(movementDTO.getTotalInstallment())) {
                result.addError(new ObjectError("totalInstallment", "Total de parcelas não pode ser nulo"));
            } else if (movementDTO.getTotalInstallment() < movementDTO.getInstallment()) {
                result.addError(new ObjectError("totalInstallment", "Total de parcelas não pode ser menor que Parcela"));
            }
        }
    }

}
