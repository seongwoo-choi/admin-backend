package kr.co.pincar.jpa.repository.event;

import kr.co.pincar.jpa.dto.event.EventRes;
import kr.co.pincar.jpa.entity.enums.EventStatus;

import java.util.List;

public interface EventRepositoryCustom {

    long changeEventStatus(List<Long> idList, EventStatus eventStatus);

    long deleteEvent(List<Long> idList);

    EventRes findOneEvent(Long idx);
}
