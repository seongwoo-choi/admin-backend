package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.IdListReq;
import kr.co.pincar.jpa.dto.event.*;
import kr.co.pincar.jpa.entity.enums.EventStatus;
import kr.co.pincar.jpa.entity.newEntity.Event;
import kr.co.pincar.jpa.entity.enums.SaveType;
import kr.co.pincar.jpa.entity.newEntity.Image;
import kr.co.pincar.jpa.repository.event.EventRepository;
import kr.co.pincar.jpa.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ImageRepository imageRepository;



    public boolean existsByIdx(Long idx) {
        return eventRepository.existsById(idx);
    }

    // 이미지 생성
    public Image makeImage(String image_title, String image_path) {
        return Image.builder()
                .imageTitle(image_title)
                .imagePath(image_path)
                .build();
    }

    // String -> LocalDate(yyyy-MM-dd)
    public LocalDate changeStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(date, formatter);
    }

    // startAt과 현재시간 비교해서 EventStatus 값 리턴하기
    public EventStatus compareStartAtAndNow(LocalDate startAt) {

        return EventStatus.PROCEEDING;
    }

    // 1. 이벤트 생성
    public void createEvent(EventReq eventReq) {
        // 이미지 생성
        Image mainImage = makeImage("이벤트 메인이미지", eventReq.getMainImagePath());
        Image detailImage = makeImage("이벤트 상세이미지", eventReq.getDetailImagePath());

        // 이벤트 생성 및 저장
        Event newEvent = Event.builder()
                .title(eventReq.getTitle())
                .content(eventReq.getContent())
                .startAt(changeStringToLocalDate(eventReq.getStartAt())) // 시작일자 LocalDate 파싱
                .endAt(changeStringToLocalDate(eventReq.getEndAt())) // 종료일자 LocalDate 파싱
                .link(eventReq.getLink())
                .eventStatus(EventStatus.valueOf(eventReq.getEventStatus()))
                .mainImage(mainImage)
                .detailImage(detailImage)
                .saveType(SaveType.valueOf(eventReq.getSaveType()))
                .build();

        try {
            eventRepository.save(newEvent);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_EVENT);
        }
    }

    // 2. 이벤트 수정
    @Transactional
    public void updateEvent(EventReq eventReq, Long eventIdx) {
        Event findEvent = eventRepository.findById(eventIdx).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_EVENT));

        // 기존에 등록되어있는 이미지 삭제
        imageRepository.delete(findEvent.getMainImage());
        imageRepository.delete(findEvent.getDetailImage());

        // 새로운 이미지 생성
        Image mainImage = makeImage("이벤트 메인이미지", eventReq.getMainImagePath());
        Image detailImage = makeImage("이벤트 상세이미지", eventReq.getDetailImagePath());

        // 이벤트 수정(새로운 이미지 연결)
        findEvent.changeEvent(
                eventReq.getTitle(),
                eventReq.getContent(),
                changeStringToLocalDate(eventReq.getStartAt()),
                changeStringToLocalDate(eventReq.getEndAt()),
                eventReq.getLink(),
                EventStatus.valueOf(eventReq.getEventStatus()),
                mainImage,
                detailImage,
                SaveType.valueOf(eventReq.getSaveType()));
    }

    // 3. 이벤트 상태값 변경(종료처리)
    @Transactional
    public void changeEventStatus(IdListReq idListReq) {
        eventRepository.changeEventStatus(idListReq.getIdList(), EventStatus.END);
    }

    // 4. 이벤트 삭제
    @Transactional
    public void deleteEvents(IdListReq idListReq) {
        eventRepository.deleteEvent(idListReq.getIdList());
    }

    // 5. 이벤트 단건 조회(수정용)
    public EventRes readEvent(Long idx) {
        return eventRepository.findOneEvent(idx);
    }


    // 6. 이벤트 리스트 조회
    public EventPageRes readEventsList(LocalDate startDate, LocalDate endDate, Pageable pageable) {

        Page<Event> eventList;

        if (pageable.getPageNumber() < 1) {
            throw new BaseException(BaseResponseCode.INVALID_PAGE);
        }

        try {
            eventList = eventRepository.findByStartAtBetween(startDate, endDate, PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_EVENT);
        }

        List<EventListRes> eventListResList = eventList.stream().map(event -> {
            return EventListRes.builder()
                    .eventIdx(event.getId())
                    .title(event.getTitle())
                    .content(event.getContent())
                    .startDate(event.getStartAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .endDate(event.getEndAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .eventStatus(event.getEventStatus().getViewName())
                    .saveType(event.getSaveType().name())
                    .build();
        }).collect(Collectors.toList());


        return EventPageRes.builder()
                .totalPage(eventList.getTotalPages())
                .eventListResList(eventListResList)
                .build();

    }



}
