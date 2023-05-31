package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/member/save")
    public String memberSave(){
        return "memberPages/save";
    }
    @PostMapping("/member/save")
    public String saveMember(@ModelAttribute MemberDTO memberDTO){
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "index";
    }
    @GetMapping("/member/{id}")
    public String memberDetail(@PathVariable Long id, Model model){
        model.addAttribute("memberDTO",memberService.findById(id));
        return "memberPages/detail";
    }
    @GetMapping("/member/update/{id}")
    public String memberUpdate(@PathVariable Long id,Model model){
        model.addAttribute("memberDTO",memberService.findById(id));
        return "memberPages/update";
    }
    @PostMapping("/member/update")
    public String updateMember(@ModelAttribute MemberDTO memberDTO){
        memberService.updateMember(memberDTO);
        return "redirect:/member/";
    }
    @GetMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id){
        memberService.memberDelete(id);
        return "redirect:/member/";
    }
    @GetMapping("/member/login")
    public String memberLogin(){
        return "memberPages/login";
    }
    @PostMapping("/member/login")
    public String loginMember(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model){
        MemberDTO loginMemberDTO = memberService.loginMember(memberDTO);
        if(loginMemberDTO != null){
            session.setAttribute("memberId",loginMemberDTO.getId());
            model.addAttribute("memberDTO",loginMemberDTO);
            System.out.println("loginMemberDTO = " + loginMemberDTO);
            return "memberPages/main";
        } else {
            return "memberPages/login";
        }

    }
    @GetMapping("/member/")
    public String memberList(Model model){
        model.addAttribute("memberDTOList",memberService.findAll());
        return "memberPages/list";
    }
}
