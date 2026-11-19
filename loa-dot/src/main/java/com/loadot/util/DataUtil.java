package com.loadot.util;

import com.loadot.dto.CharacterCardsDto;
import com.loadot.dto.CharacterEngravingsDto;
import com.loadot.dto.CharacterEquipmentDto;
import com.loadot.dto.CharacterGemsDto;
import com.loadot.dto.response.CharacterInfoResponse;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DataUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Double parseStringToDouble(String levelStr) {
        if (levelStr == null || levelStr.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(levelStr.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static List<String> parseTooltip(String tooltipJson) {
        if (tooltipJson == null || tooltipJson.isBlank()) return List.of();

        List<String> result = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(tooltipJson);

            root.forEach(node -> {
                String type = node.path("type").asString();
                JsonNode valueNode = node.path("value");

                if (valueNode.isString()) {
                    String cleaned = cleanHtml(valueNode.asString());
                    // 코어 옵션 전용 정제 적용
                    cleaned = cleanArkCoreOption(cleaned);
                    if (!cleaned.isEmpty()) result.add(cleaned);
                }
                else if ("ItemPartBox".equals(type) && valueNode.isObject()) {
                    String title = cleanHtml(valueNode.path("Element_000").asString());
                    String content = cleanHtml(valueNode.path("Element_001").asString());

                    if (!content.isEmpty()) {
                        String combined = title.isEmpty() ? content : title + ": " + content;
                        combined = cleanArkCoreOption(combined);
                        if (!combined.isEmpty()) result.add(combined);
                    }
                }
            });
        } catch (Exception e) {
            // 에러 처리
        }
        return result;
    }

    public static List<CharacterInfoResponse.EffectDetail> parseTooltipForArkPassive(String tooltipJson) {
        if (tooltipJson == null || tooltipJson.isBlank()) return List.of();

        List<CharacterInfoResponse.EffectDetail> result = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(tooltipJson);

            String title = cleanHtml(root.path("Element_000").path("value").asString());

            String rawLevel = root.path("Element_001").path("value").path("leftText").asString();
            String level = cleanHtml(rawLevel).replace("아크 패시브 레벨", "Lv.").trim();

            String description = cleanHtml(root.path("Element_002").path("value").asString());
            // 코어 데이터 전용 정제 적용
            description = cleanArkCoreOption(description);

            if (title.isEmpty() && !description.isEmpty()) {
                title = description;
                description = "";
            }

            if (!title.isEmpty()) {
                result.add(new CharacterInfoResponse.EffectDetail(title, level, description));
            }
        } catch (Exception e) {
            // 에러 처리
        }
        return result;
    }

    /**
     * 각인 툴팁 가공
     * @param rawEffects
     * @return List<CharacterEngravingsDto.ArkPassiveEffect>
     */
    public static List<CharacterEngravingsDto.ArkPassiveEffect> parseTooltipForEngravings(List<CharacterEngravingsDto.ArkPassiveEffect> rawEffects) {
        if (rawEffects == null || rawEffects.isEmpty()) {
            return List.of();
        }

        List<CharacterEngravingsDto.ArkPassiveEffect> processedList = new ArrayList<>();

        for (CharacterEngravingsDto.ArkPassiveEffect effect : rawEffects) {
            CharacterEngravingsDto.ArkPassiveEffect cleanEffect = new CharacterEngravingsDto.ArkPassiveEffect();

            cleanEffect.setAbilityStoneLevel(effect.getAbilityStoneLevel());
            cleanEffect.setGrade(effect.getGrade());
            cleanEffect.setLevel(effect.getLevel());
            cleanEffect.setName(effect.getName());

            String rawDesc = effect.getDescription();
            // 코어 데이터 전용 정제 적용
            cleanEffect.setDescription(rawDesc != null ? cleanArkCoreOption(cleanHtml(rawDesc)) : "");

            processedList.add(cleanEffect);
        }

        return processedList;
    }

    /**
     * 기본 HTML 태그 및 기본적인 특수문자 제거
     */
    public static String cleanHtml(String text) {
        if (text == null) return "";
        return text.replaceAll("(?i)<br\\s*/?>", "\n")              // <br>, <BR>, <br /> 모두 \n으로 통일
                .replaceAll("<[^>]*>", "")                          // 나머지 모든 HTML 태그 제거
                .replaceAll("&nbsp;" , " ")                         // 공백 치환
                .replaceAll("[\\t\\x0B\\f\\r]", "")                 // \n을 제외한 다른 화이트스페이스 제거
                .replaceAll("[ ]+", " ")                            // 연속된 일반 공백만 하나로 압축 (\n 유지)
                .replace("||"        , "")                          // 구분 기호 제거
                .trim();
    }

    /**
     * 아크 패시브 코어 툴팁
     */
    private static String cleanArkCoreOption(String text) {
        if (text == null || text.isBlank()) return "";

        // 1. 불필요한 단어 및 명칭 선제거
        text = text.replaceAll("(?m)^.*전용$\\n?", "");
        text = text.replaceAll("(?m)^\\|?거래\\s?불가$\\n?", "");
        text = text.replaceAll("(?m)^코어\\s?타입.*$\\n?", "");
        text = text.replaceAll("(?m)^코어\\s?공급.*$\\n?", "");
        text = text.replaceAll("코어\\s?옵션\\s?:\\s?", "");
        text = text.replaceAll("코어\\s?옵션\\s?발동\\s?조건\\s?:\\s?", "");

        // 2. 모든 "[숫자P]" 패턴 앞에 줄바꿈(\n) 삽입
        text = text.replaceAll("\\[(\\d+P)\\]", "\n[$1]");

        // 3. 줄바꿈 기준 분기 처리
        String[] lines = text.split("\n");
        StringBuilder sb = new StringBuilder();

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty()) continue;

            // 💡 [추가] 만약 줄 내용이 제목인 "질서의 해 코어 : 다크 파워"를 포함하고 있다면 중복이므로 제거
            if (trimmedLine.contains("코어 :")) continue;
            if (trimmedLine.contains("활성화 필요") || trimmedLine.contains("활성화가 필요")) continue;

            sb.append(trimmedLine.replace("|", "")).append("\n");
        }

        return sb.toString().trim();
    }

    /**
     * 카드 세트 이름 추출
     */
    public static String parseCardSetName(CharacterCardsDto characterCardsDto) {
        if (characterCardsDto == null) return "적용된 카드 세트 없음";

        List<CharacterCardsDto.EffectsDto> rawEffects = characterCardsDto.getEffects();
        if (rawEffects != null && !rawEffects.isEmpty() && rawEffects.get(0).getItems() != null && !rawEffects.get(0).getItems().isEmpty()) {
            List<CharacterCardsDto.EffectItemDto> items = rawEffects.get(0).getItems();

            // 가장 높은 활성화 옵션 이름에서 세트 명칭 추출
            String lastEffectName = items.getLast().getName();
            return lastEffectName.split(" \\d+세트")[0].trim();
        }
        return "적용된 카드 세트 없음";
    }

    /**
     * 💡 카드 세트 효과 리스트 결합 및 가공
     * 예: "세상을 구하는 빛 2세트 : 암속성 피해 감소 +10.00%"
     */
    public static List<String> parseCardSetEffects(CharacterCardsDto characterCardsDto) {
        if (characterCardsDto == null) return List.of();

        List<CharacterCardsDto.EffectsDto> rawEffects = characterCardsDto.getEffects();
        if (rawEffects != null && !rawEffects.isEmpty() && rawEffects.getFirst().getItems() != null && !rawEffects.get(0).getItems().isEmpty()) {
            List<CharacterCardsDto.EffectItemDto> items = rawEffects.getFirst().getItems();

            // Name과 Description을 결합하여 가공 문자열 리스트 생성
            return items.stream()
                    .map(item -> item.getName() + " : " + item.getDescription())
                    .toList();
        }
        return List.of();
    }

    /**
     * 캐릭터 장비 response 가공
     */
    public static List<CharacterInfoResponse.EquipmentResponse> parseEquipmentResponse(List<CharacterEquipmentDto> equipmentList) {
        if (equipmentList == null || equipmentList.isEmpty()) {
            return List.of();
        }

        return equipmentList.stream().map(dto -> {
                    Integer quality = null;
                    Integer petBorder = null;
                    String arkPassivePointText = null;
                    String mainStatText = null;
                    List<CharacterInfoResponse.AccOptionDetail> accOptions = new ArrayList<>();

                    // Tooltip JSON에서 qualityValue와 petBorder 파싱
                    if (dto.getTooltip() != null && !dto.getTooltip().isBlank()) {
                        try {
                            JsonNode root = objectMapper.readTree(dto.getTooltip());
                            JsonNode itemTitleValue = root.path("Element_001").path("value");

                            if (!itemTitleValue.isMissingNode() && itemTitleValue.isObject()) {
                                // 품질
                                if (itemTitleValue.has("qualityValue")) {
                                    quality = itemTitleValue.path("qualityValue").asInt();
                                }

                                // 테두리
                                JsonNode slotData = itemTitleValue.path("slotData");
                                if (slotData.has("petBorder")) {
                                    petBorder = slotData.path("petBorder").asInt();
                                }

                                for (JsonNode node : root) {
                                    String type = node.path("type").asString();
                                    JsonNode value = node.path("value");

                                    // ItemPartBox (연마 효과, 깨달음/도약 포인트, 힘민지 등)
                                    if ("ItemPartBox".equals(type) && value.isObject()) {
                                        String title = cleanHtml(value.path("Element_000").asString());
                                        String content = cleanHtml(value.path("Element_001").asString());

                                        if (title.contains("아크 패시브") || content.contains("깨달음") || content.contains("도약")) {
                                            // 깨달음
                                            arkPassivePointText = content;
                                        } else if (title.contains("연마 효과")) {
                                            // 연마 효과 파싱
                                            parseAccOptionItem(content, accOptions);
                                        } else if (title.contains("기본 효과") && (content.contains("힘") || content.contains("민첩") || content.contains("지능"))) {
                                            // 힘, 민첩, 지능
                                            mainStatText = content;
                                        }
                                    }

                                    // 어빌리티 스톤 각인파싱 (IndentStringGroup)
                                    if ("어빌리티 스톤".equals(dto.getType()) && "IndentStringGroup".equals(type) && value.isObject()) {
                                        parseAbilityStoneEngraves(value, accOptions);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            log.warn("장비 툴팁 파싱 실패 (장비명: {}): {}", dto.getName(), e.getMessage());
                        }
                    }

                    return new CharacterInfoResponse.EquipmentResponse(
                            dto.getType(),
                            dto.getName(),
                            dto.getIcon(),
                            dto.getGrade(),
                            quality,
                            petBorder,
                            arkPassivePointText,
                            mainStatText,
                            accOptions
                    );
                })
                .toList();
    }

    /** 보석 슬롯에 스킬 매칭 */
    public static List<CharacterInfoResponse.GemResponse> parseGems(CharacterGemsDto gemsDto) {
        if (gemsDto == null || gemsDto.getGems() == null || gemsDto.getGems().isEmpty()) {
            return List.of();
        }

        List<CharacterGemsDto.SkillEffectDto> skills = gemsDto.getEffects() == null
                || gemsDto.getEffects().getSkills() == null
                ? List.of()
                : gemsDto.getEffects().getSkills();

        return gemsDto.getGems().stream()
                .filter(java.util.Objects::nonNull)
                .map(gem -> {
                    CharacterGemsDto.SkillEffectDto skill = gem.getSlot() == null ? null : skills.stream()
                            .filter(java.util.Objects::nonNull)
                            .filter(candidate -> gem.getSlot().equals(candidate.getGemSlot()))
                            .findFirst()
                            .orElse(null);
                    String type = parseGemType(gem.getTooltip());
                    return new CharacterInfoResponse.GemResponse(
                            gem.getSlot(), gem.getIcon(), gem.getLevel(), type,
                            skill == null ? null : skill.getName(),
                            skill == null ? null : skill.getIcon());
                })
                .toList();
    }

    private static String parseGemType(String tooltipJson) {
        if (tooltipJson == null || tooltipJson.isBlank()) return null;
        try {
            JsonNode root = objectMapper.readTree(tooltipJson);
            String type = cleanHtml(root.path("Element_000").path("value").asString()).trim();
            if (type.isEmpty()) type = cleanHtml(root.path("Element_000").asString()).trim();
            for (String supported : List.of("멸화", "홍염", "겁화", "작열", "광휘")) {
                if (type.contains(supported)) return supported;
            }
            return null;
        } catch (Exception e) {
            log.warn("보석 툴팁 파싱 실패: {}", e.getMessage());
            return null;
        }
    }
    /**
     * 연마 효과 문장 파싱
     */
    private static void parseAccOptionItem(String contentText, List<CharacterInfoResponse.AccOptionDetail> accOptions) {
        if (contentText == null || contentText.isBlank()) return;

        // 여러 줄로 들어오는 경우 분리
        String[] lines = contentText.split("\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            String tierBadge = null;
            if (trimmed.contains("[상]")) { tierBadge = "상"; trimmed = trimmed.replace("[상]", "").trim(); }
            else if (trimmed.contains("[중]")) { tierBadge = "중"; trimmed = trimmed.replace("[중]", "").trim(); }
            else if (trimmed.contains("[하]")) { tierBadge = "하"; trimmed = trimmed.replace("[하]", "").trim(); }

            // 옵션명과 값 분리
            int plusIndex = trimmed.lastIndexOf("+");
            if (plusIndex != -1) {
                String optName = trimmed.substring(0, plusIndex).trim();
                String optValue = trimmed.substring(plusIndex).trim();
                accOptions.add(new CharacterInfoResponse.AccOptionDetail(optName, optValue, tierBadge));
            } else {
                accOptions.add(new CharacterInfoResponse.AccOptionDetail(trimmed, "", tierBadge));
            }
        }
    }

    /**
     * 스톤 각인 효과 파싱
     */
    private static void parseAbilityStoneEngraves(JsonNode indentGroupNode, List<CharacterInfoResponse.AccOptionDetail> accOptions) {
        JsonNode element000 = indentGroupNode.path("Element_000");
        if (cleanHtml(element000.path("topStr").asString()).contains("무작위 각인 효과")) {
            element000.path("contentStr").forEach(itemNode -> {
                String cleaned = cleanHtml(itemNode.path("contentStr").asString()).trim();
                if (cleaned.contains("[") && cleaned.contains("]") && !cleaned.contains("레벨 보너스")) {
                    int start = cleaned.indexOf("[");
                    int end = cleaned.indexOf("]");
                    String name = cleaned.substring(start + 1, end).trim();
                    String level = cleaned.contains("Lv.") ? cleaned.substring(cleaned.indexOf("Lv.") + 3).trim() : "0";

                    accOptions.add(new CharacterInfoResponse.AccOptionDetail(name, level, null));
                }
            });
        }
    }
}
