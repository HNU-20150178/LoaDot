package com.loadot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "characters") // 실제 DB 테이블 이름을 지정
public class Character {
    @Id // 2. PK 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 3. Auto Increment 설정
    private Long id;

    @Column(nullable = false, unique = true) // 5. 필드 설정 (중복 불가, 빈 값 불가)
    private String name;

    private String serverName;
    private String characterClass;
    private Double itemLevel;
    private String guildName;
    private String expeditionLevel;
    private String characterImage;

    // 비즈니스 로직을 위한 생성자
    public Character(String name, String serverName, String characterClass, Double itemLevel, String guildName, String expeditionLevel, String characterImage) {
        this.name = name;
        this.serverName = serverName;
        this.characterClass = characterClass;
        this.itemLevel = itemLevel;
        this.guildName = guildName;
        this.expeditionLevel = expeditionLevel;
        this.characterImage = characterImage;
    }
}
