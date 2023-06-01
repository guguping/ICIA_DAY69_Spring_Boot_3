package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        for (MemberEntity memberEntity : memberRepository.findAll()) {
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public void updateMember(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdate(memberDTO));
    }

    public void memberDelete(Long id) {
        memberRepository.deleteById(id);
    }

    public boolean loginMember(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if (memberEntity.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void loginAxios(MemberDTO memberDTO) {
        // chaining method (체이닝 메서드)
        memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                .orElseThrow(() -> new NoSuchElementException("이메일 또는 비밀번호가 틀립니다.")); // 옵셔널 객체에 값이 없으면 지정한 예외를 리턴해줌
    }
}
