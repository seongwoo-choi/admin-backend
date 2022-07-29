package kr.co.pincar.jpa.entity.newEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;



@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity // 이벤트 추첨자 등록방식에 따라 수정될 듯
public class EventWinner {

    @Id @GeneratedValue
    @Column(name = "event_winner_id")
    private Long id;

    private String winnerInfo;

    private LocalDateTime raffleAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;
}
