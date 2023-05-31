package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toSaveEntity(memberDTO));
    }

    public List<MemberDTO> findAll() {
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity :memberRepository.findAll()){
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void updateMember(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdate(memberDTO));
    }

    public void memberDelete(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO loginMember(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(),memberDTO.getMemberPassword());
        if(memberEntity.isPresent()){
            return MemberDTO.toDTO(memberEntity.get());
        }else {
            return null;
        }
    }
}
