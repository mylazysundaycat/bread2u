package com.daegeon.bread2u.domain.controller;

import com.daegeon.bread2u.domain.entity.Bread;
import com.daegeon.bread2u.domain.service.BreadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequiredArgsConstructor
public class BreadController {

    private final BreadService breadService;

    @GetMapping("/{breadId}")
    @Operation(summary = "특정 빵 조회", description = "id에 해당하는 빵을 보여준다")
    public Bread findById(Long bread_id) {
        return breadService.findById(bread_id);
    }

    @GetMapping("/bread")
    @Operation(summary = "빵 작성 페이지", description = "빵 게시물 작성 페이지로 이동한다")
    public String createBred() {
        return "/BreadForm";
    }

    @PostMapping("/bread")
    @Operation(summary = "빵 작성", description = "빵 게시물을 작성한다")
    public String createBread(@ModelAttribute Bread bread) {
        Bread createdBread = breadService.createBread(bread);
        return "redirect:/"+createdBread.getId();
    }

    @GetMapping("/{breadId}/update")
    @Operation(summary = "빵 수정 페이지", description = "빵 게시물 수정 페이지로 이동한다")
    public String updateBread(@PathVariable Long breadId, Model model){
        return "/BraedForm";
    }

    //TODO
    @PostMapping("/{breadId}/update")
    @Operation(summary = "빵 수정", description = "id에 해당하는 빵 게시물을 수정한다")
    public String updateBread(@PathVariable Long breadId, @ModelAttribute Bread bread
            , RedirectAttributes redirectAttributes){
        Bread updatedBread = breadService.updateBread(bread);
        redirectAttributes.addAttribute("breadId", updatedBread.getId());
        redirectAttributes.addFlashAttribute("message", "빵이 수정되었습니다.");
        return "redirect:/"+updatedBread.getId();
    }

    @GetMapping("/{breadId}/delete")
    @Operation(summary = "빵 삭제", description = "id에 해당하는 빵 게시물을 삭제한다.")
    public String deleteBread(@PathVariable Long breadId, RedirectAttributes redirectAttributes){
        breadService.deleteBread(findById(breadId));
        redirectAttributes.addFlashAttribute("message", "빵이 삭제되었습니다.");
        return "redirect:/";
    }


}
