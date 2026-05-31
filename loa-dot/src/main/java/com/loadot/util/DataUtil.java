package com.loadot.util;

import com.loadot.dto.CharacterEngravingsDto;
import com.loadot.dto.response.CharacterInfoResponse;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * "1,620.00" -> 1620.00 (Double 변환)
     * 아이템 레벨, 전투력 변환용
     */
    public static Double parseStringToDouble(String levelStr) {
        if (levelStr == null || levelStr.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(levelStr.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * API 툴팁의 JSON을 문자열 리스트로 변환
     */
    public static List<String> parseTooltip(String tooltipJson) {
        if (tooltipJson == null || tooltipJson.isBlank()) return List.of();

        List<String> result = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(tooltipJson);

            // root가 객체라면 내부 필드들을 순회합니다.
            root.forEach(node -> {
                // node는 { "type": ..., "value": ... } 객체입니다.
                String type = node.path("type").asString();
                JsonNode valueNode = node.path("value");

                if (valueNode.isString()) {
                    String cleaned = cleanHtml(valueNode.asString());
                    if (!cleaned.isEmpty()) result.add(cleaned);
                }
                else if ("ItemPartBox".equals(type) && valueNode.isObject()) {
                    // Element_000이 제목, Element_001이 내용임
                    String title = cleanHtml(valueNode.path("Element_000").asString());
                    String content = cleanHtml(valueNode.path("Element_001").asString());

                    if (!content.isEmpty()) {
                        // 제목이 있으면 "제목: 내용", 없으면 "내용"만 추가
                        result.add(title.isEmpty() ? content : title + ": " + content);
                    }
                }
            });
        } catch (Exception e) {
            // 에러 처리
        }
        return result;
    }

    /**
     * 아크패시브툴팁변환
     * @param tooltipJson JSON
     * @return EffectDetail
     */
    public static List<CharacterInfoResponse.EffectDetail> parseTooltipForArkPassive(String tooltipJson) {
        if (tooltipJson == null || tooltipJson.isBlank()) return List.of();

        List<CharacterInfoResponse.EffectDetail> result = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(tooltipJson);

            // 1. Element_000: 스킬 이름 (NameTagBox)
            String title = cleanHtml(root.path("Element_000").path("value").asString());

            // 2. Element_001: 레벨 정보 (CommonSkillTitle)
            // value -> leftText 에 "아크 패시브 레벨 <FONT...>3</FONT>" 형태로 들어있음
            String rawLevel = root.path("Element_001").path("value").path("leftText").asString();
            String level = cleanHtml(rawLevel).replace("아크 패시브 레벨", "Lv.").trim();

            // 3. Element_002: 상세 설명 (MultiTextBox)
            String description = cleanHtml(root.path("Element_002").path("value").asString());

            // 만약 Element_000이 비어있다면 (예비책)
            if (title.isEmpty() && !description.isEmpty()) {
                title = description; // 이전처럼 스왑
                description = "";
            }

            if (!title.isEmpty()) {
                result.add(new CharacterInfoResponse.EffectDetail(title, level, description));
            }
        } catch (Exception e) {
            // 파싱 에러 로그
        }
        return result;
    }

    /**
     * 각인 아크 패시브 효과 리스트의 Description에서 HTML 태그를 제거하여 정돈된 리스트로 반환
     * @param rawEffects API에서 받아온 원본 ArkPassiveEffect 리스트
     * @return HTML 태그가 제거된 ArkPassiveEffect 리스트 (Null-safe)
     */
    public static List<CharacterEngravingsDto.ArkPassiveEffect> parseTooltipForEngravings(List<CharacterEngravingsDto.ArkPassiveEffect> rawEffects) {
        if (rawEffects == null || rawEffects.isEmpty()) {
            return List.of(); // 빈 리스트 리턴으로 NullPointerException 방지
        }

        List<CharacterEngravingsDto.ArkPassiveEffect> processedList = new ArrayList<>();

        for (CharacterEngravingsDto.ArkPassiveEffect effect : rawEffects) {
            CharacterEngravingsDto.ArkPassiveEffect cleanEffect = new CharacterEngravingsDto.ArkPassiveEffect();

            cleanEffect.setAbilityStoneLevel(effect.getAbilityStoneLevel());
            cleanEffect.setGrade(effect.getGrade());
            cleanEffect.setLevel(effect.getLevel());
            cleanEffect.setName(effect.getName());

            // Description에서 HTML 태그만 지우기
            String rawDesc = effect.getDescription();
            cleanEffect.setDescription(rawDesc != null ? cleanHtml(rawDesc) : "");

            processedList.add(cleanEffect);
        }

        return processedList;
    }

    /**
     * HTML 태그 제거 및 특수문자 제외
     */
    public static String cleanHtml(String text) {
        if (text == null) return "";
        return text.replaceAll("<br>", "\n")                              // 줄바꿈 보존
                .replaceAll("<BR>"   , "\n")
                .replaceAll("<[^>]*>", "")                               // 나머지 모든 태그 제거
                .replaceAll("&nbsp;" , " ")                              // 공백 치환
                .replaceAll("\\s+"   , " ")                              // 연속 공백 정리
                .replace("||"        , "")                               // 툴팁 끝에 붙는 구분 기호 제거
                .trim();
    }
}
