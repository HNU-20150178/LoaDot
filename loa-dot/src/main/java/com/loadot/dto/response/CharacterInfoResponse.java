package com.loadot.dto.response;

import com.loadot.dto.CharacterArkGridDto;
import com.loadot.dto.CharacterArkPassiveDto;
import com.loadot.dto.CharacterInfoDto;
import com.loadot.entity.Character;
import com.loadot.util.DataUtil;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CharacterInfoResponse {

    private final Long id;
    private final String characterName;
    private final String serverName;
    private final String characterClassName;
    private final Integer characterLevel;
    private final Integer expeditionLevel;

    private final String itemAvgLevel;
    private final Double itemAvgLevelDouble;

    private final String combatPower;
    private final Double combatPowerDouble;

    private final String title;
    private final String guildName;
    private final String guildMemberGrade;

    private final Integer townLevel;
    private final String townName;

    private final Integer usingSkillPoint;
    private final Integer totalSkillPoint;
    private final Integer honorPoint;

    private final String characterImage;
    private final String symbol;
    private final String decorationEmblems;

    // 기본 스탯
    private final List<StatResponse> combatStats;
    private final BaseStatResponse baseStats;
    private final List<CharacterInfoDto.TendencyDto> tendencies;
    private final CharacterInfoDto.DecorationDto decorations;

    // 아크패시브
    private final String arkPassiveTitle;

    private final boolean isArkPassive;

    private final List<CharacterArkPassiveDto.PointsDto> arkPassivePoints;

    private final List<ArkPassiveEffectResponse> arkPassiveEffects;

    // 아크그리드 슬롯
    private final List<ArkGridSlotResponse> arkGridSlots;
    private final List<CharacterArkGridDto.EffectsDto> arkGridEffects;

    // --- 가공용 내부 클래스 ---
    @Getter
    public static class StatResponse {
        private final String type;
        private final String value;
        public StatResponse(String type, String value) { this.type = type; this.value = value; }
    }

    @Getter
    public static class BaseStatResponse {
        private final String attackPower;
        private final String maxHp;
        public BaseStatResponse(String attackPower, String maxHp) { this.attackPower = attackPower; this.maxHp = maxHp; }
    }

    @Getter
    public static class ArkPassiveEffectResponse {
        private final String name;
        private final String icon;
        private final String title;
        private final String level;
        private final String description;

        public ArkPassiveEffectResponse(String name, String icon, String title, String level, String description) {
            this.name = name;
            this.icon = icon;
            this.title = title;
            this.level = level;
            this.description = description;
        }
    }

    @Getter
    public static class EffectDetail {
        private final String title;
        private final String level;
        private final String description;

        public EffectDetail(String title, String level, String description) {
            this.title = title; this.level = level; this.description = description;
        }
    }

    @Getter
    public static class ArkGridSlotResponse {
        private final String name;
        private final String grade;
        private final List<String> parsedTooltip;
        public ArkGridSlotResponse(String name, String grade, List<String> tooltip) {
            this.name = name; this.grade = grade; this.parsedTooltip = tooltip;
        }
    }

    private final LocalDateTime updatedAt;

    // Entity,Dto -> Response DTO 변환 (정적 팩토리 메서드)
    public static CharacterInfoResponse from(Character character,
                                             CharacterInfoDto characterInfoDto,
                                             CharacterArkPassiveDto characterArkPassiveDto,
                                             CharacterArkGridDto characterArkGridDto) {
        return new CharacterInfoResponse(character, characterInfoDto, characterArkPassiveDto, characterArkGridDto);
    }

    private CharacterInfoResponse(Character character,
                                  CharacterInfoDto characterInfoDto,
                                  CharacterArkPassiveDto characterArkPassiveDto,
                                  CharacterArkGridDto characterArkGridDto) {
        this.id                 = character.getId();
        this.characterName      = character.getCharacterName();
        this.serverName         = character.getServerName();
        this.characterClassName = character.getCharacterClassName();
        this.characterLevel     = character.getCharacterLevel();
        this.expeditionLevel    = character.getExpeditionLevel();
        this.itemAvgLevel       = character.getItemAvgLevel();
        this.itemAvgLevelDouble = character.getItemAvgLevelDouble();
        this.combatPower        = character.getCombatPower();
        this.combatPowerDouble  = character.getCombatPowerDouble();
        this.title              = character.getTitle();
        this.guildName          = character.getGuildName();
        this.guildMemberGrade   = character.getGuildMemberGrade();
        this.townLevel          = character.getTownLevel();
        this.townName           = character.getTownName();
        this.usingSkillPoint    = character.getUsingSkillPoint();
        this.totalSkillPoint    = character.getTotalSkillPoint();
        this.honorPoint         = character.getHonorPoint();
        this.characterImage     = character.getCharacterImage();
        this.symbol             = character.getSymbol();
        this.decorationEmblems  = character.getDecorationEmblems();
        this.updatedAt          = character.getUpdatedAt();

        // 스탯 필터링 및 가공
        List<CharacterInfoDto.StatDto> rawStats = characterInfoDto.getStats();
        List<String> combatTargets = List.of("치명", "특화", "제압", "신속", "인내", "숙련");

        this.combatStats = rawStats.stream()
                .filter(s -> combatTargets.contains(s.getType()))
                .map(s -> new StatResponse(s.getType(), s.getValue()))
                .toList();

        String ap = rawStats.stream().filter(s -> "공격력".equals(s.getType())).findFirst().map(CharacterInfoDto.StatDto::getValue).orElse("0");
        String hp = rawStats.stream().filter(s -> "최대 생명력".equals(s.getType())).findFirst().map(CharacterInfoDto.StatDto::getValue).orElse("0");
        this.baseStats = new BaseStatResponse(ap, hp);

        this.tendencies = characterInfoDto.getTendencies();
        this.decorations = characterInfoDto.getDecorations();

        // 아크 패시브 가공 (툴팁 파싱 로직 호출)
        this.arkPassiveTitle = characterArkPassiveDto.getTitle();
        this.isArkPassive = characterArkPassiveDto.getIsArkPassive();
        this.arkPassivePoints = characterArkPassiveDto.getPoints();
        this.arkPassiveEffects = characterArkPassiveDto.getEffects().stream()
                .map(e -> {
                    // 1. 일단 툴팁을 파싱해서 리스트를 가져옵니다.
                    List<EffectDetail> details = DataUtil.parseTooltipForArkPassive(e.getTooltip());

                    // 2. 리스트가 비어있지 않다면 첫 번째 요소를 꺼내고, 비어있으면 빈 값을 줍니다.
                    String title = details.isEmpty() ? "" : details.get(0).getTitle();
                    String level = details.isEmpty() ? "" : details.get(0).getLevel();
                    String desc  = details.isEmpty() ? "" : details.get(0).getDescription();

                    // 3. 평면화된 DTO로 생성
                    return new ArkPassiveEffectResponse(e.getName(), e.getIcon(), title, level, desc);
                })
                .toList();

        // 4. 아크 그리드 가공 (툴팁 파싱 로직 호출)
        this.arkGridSlots = characterArkGridDto.getSlots().stream()
                .map(s -> new ArkGridSlotResponse(s.getName(), s.getGrade(), DataUtil.parseTooltip(s.getTooltip())))
                .toList();
        this.arkGridEffects = characterArkGridDto.getEffects();

    }
}