package com.loadot.service;

import com.loadot.dto.response.GuildDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuildService {

    public GuildDetailResponse getGuildDetail(String guildName, String serverName) {

        // 예시 Response 객체 생성 (실제 API 응답 데이터 매핑 필요)
        List<GuildDetailResponse.GuildMemberDto> memberList = new ArrayList<>();
        
        memberList.add(GuildDetailResponse.GuildMemberDto.builder()
                .characterName("길드장캐릭터")
                .characterClassName("버서커")
                .itemMaxLevel(1630.0)
                .guildMemberGrade(1)
                .build());

        memberList.add(GuildDetailResponse.GuildMemberDto.builder()
                .characterName("길드원1")
                .characterClassName("바드")
                .itemMaxLevel(1620.0)
                .guildMemberGrade(2)
                .build());

        return GuildDetailResponse.builder()
                .guildName(guildName)
                .serverName(serverName)
                .masterName("길드장캐릭터")
                .memberCount(memberList.size())
                .maxMemberCount(50)
                .guildLevel(25)
                .guildMessage("즐거운 길드입니다!")
                .members(memberList)
                .build();
    }
}