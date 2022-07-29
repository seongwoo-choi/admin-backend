package kr.co.pincar.jpa.repository.event;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.pincar.jpa.dto.event.EventRes;
import kr.co.pincar.jpa.dto.event.QEventRes;
import kr.co.pincar.jpa.entity.enums.EventStatus;
import kr.co.pincar.jpa.entity.newEntity.QEvent;
import kr.co.pincar.jpa.entity.newEntity.QImage;

import javax.persistence.EntityManager;
import java.util.List;

import static kr.co.pincar.jpa.entity.newEntity.QEvent.*;
import static kr.co.pincar.jpa.entity.newEntity.QImage.*;

public class EventRepositoryImpl implements EventRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public EventRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long changeEventStatus(List<Long> idList, EventStatus eventStatus) {
        return jpaQueryFactory
                .update(event)
                .set(event.eventStatus, eventStatus)
                .where(event.id.in(idList))
                .execute();
    }

    @Override
    public long deleteEvent(List<Long> idList) {
        return jpaQueryFactory
                .delete(event)
                .where(event.id.in(idList))
                .execute();
    }

    QImage mainImg = new QImage("mainImg");
    QImage detailImg = new QImage("detailImg");

    @Override
    public EventRes findOneEvent(Long idx) {
        return jpaQueryFactory
                .select(new QEventRes(
                        event.id,
                        event.title,
                        event.content,
                        event.startAt,
                        event.endAt,
                        event.link,
                        mainImg.imagePath,
                        detailImg.imagePath
                ))
                .from(event)
                .leftJoin(mainImg).on(event.mainImage.id.eq(mainImg.id))
                .leftJoin(detailImg).on(event.detailImage.id.eq(detailImg.id))
                .where(event.id.eq(idx))
                .fetchOne();
    }
}
