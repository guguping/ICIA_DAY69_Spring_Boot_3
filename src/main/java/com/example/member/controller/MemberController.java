package com.example.member.controller;

import com.example.member.dto.MemberDTO;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String loginMember(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        if(memberService.loginMember(memberDTO)){
            session.setAttribute("memberEmail",memberDTO.getMemberEmail());
            return "memberPages/main";
        } else {
            return "memberPages/login";
        }
    }
    @PostMapping("/member/login/axios")
    public ResponseEntity memberLoginAxios(@RequestBody MemberDTO memberDTO,HttpSession session) throws Exception {
        memberService.loginAxios(memberDTO);
        session.setAttribute("memberEmail",memberDTO.getMemberEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/member/")
    public String memberList(Model model){
        model.addAttribute("memberDTOList",memberService.findAll());
        return "memberPages/list";
    }
    @GetMapping("/member-axios/{id}")
    public ResponseEntity member_axios(@PathVariable Long id) throws Exception {
        MemberDTO memberDTO = memberService.findById(id);
        return new ResponseEntity<>(memberDTO,HttpStatus.OK);
    }
}
