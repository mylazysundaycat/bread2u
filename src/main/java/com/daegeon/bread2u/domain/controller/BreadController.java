package com.daegeon.bread2u.domain.controller;

import com.daegeon.bread2u.domain.entity.Bread;
import com.daegeon.bread2u.domain.repository.dto.BreadDto;
import com.daegeon.bread2u.domain.service.BreadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BreadController {

    private final BreadService breadService;


    @GetMapping("/breadList/create")
    @Operation(summary = "빵 등록 페이지", description = "빵 등록 페이지로 이동한다")
    public String createBread(Model model) {
        model.addAttribute("bread", new BreadDto());
        return "/bread/createBreadForm";
    }

    @PostMapping("/breadList/create")
    @Operation(summary = "빵 등록", description = "빵을 등록한다.")
    public String createBread(@ModelAttribute Bread bread) {
        Bread createdBread = breadService.createBread(bread);
        return "redirect:/breadList";
        //return "redirect:/"+createdBread.getId();
    }

    //find
    @GetMapping("/bread/{breadId}")
    @Operation(summary = "특정 빵 조회", description = "id에 해당하는 빵을 보여준다")
    public Bread findById(Long bread_id) {
        return breadService.findById(bread_id);
    }

    //findAll
    @GetMapping("/breadList")
    @Operation(summary = "빵 목록 조회", description = "빵 목록을 보여준다")
    public String findAll(Model model) {
        model.addAttribute("breadList", breadService.findAll());
        return "/bread/breadList";
    }

    @GetMapping("/bread/{breadId}/update")
    @Operation(summary = "빵 수정 페이지", description = "빵 게시물 수정 페이지로 이동한다")
    public String updateBread(@PathVariable Long breadId, Model model) {
        model.addAttribute("bread", findById(breadId));
        return "/bread/updateBreadForm";
    }

    @PostMapping("/bread/{breadId}/update")
    @Operation(summary = "빵 수정", description = "id에 해당하는 빵 게시물을 수정한다")
    public String updateBread(@PathVariable Long breadId, @ModelAttribute Bread bread
            , RedirectAttributes redirectAttributes){
        Bread updatedBread = breadService.updateBread(breadId, bread);
        redirectAttributes.addAttribute("breadId", updatedBread.getId());
        redirectAttributes.addFlashAttribute("message", "빵이 수정되었습니다.");
        return "redirect:/breadList";
//        return "redirect:/"+updatedBread.getId();
    }

    @GetMapping("/bread/{breadId}/delete")
    @Operation(summary = "빵 삭제", description = "id에 해당하는 빵 게시물을 삭제한다.")
    public String deleteBread(@PathVariable(name="breadId") Long breadId, RedirectAttributes redirectAttributes){
        breadService.deleteBread(findById(breadId));
        redirectAttributes.addFlashAttribute("message", "빵이 삭제되었습니다.");
//        return "ok";
        return "redirect:/breadList";
    }


}
