package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.StatusReq;
import kr.co.pincar.jpa.dto.slide.*;
import kr.co.pincar.jpa.entity.enums.SaveType;
import kr.co.pincar.jpa.entity.newEntity.Slide;
import kr.co.pincar.jpa.repository.UserRepository;
import kr.co.pincar.jpa.repository.SlideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlideService {
    private final SlideRepository slideRepository;
    private final UserRepository adminRepository;

    public boolean existsById(Long id) {
        return slideRepository.existsById(id);

    }

    // 슬라이드 저장
    @Transactional
    public void createSlides(SlideReq slideReq) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;
//        String email = userDetails.getUsername();
//
//        Admin admin = adminRepository.findByEmailAndStatus(email, "ACTIVE").orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ADMIN_EMAIL));

        Slide slide;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start_at = LocalDate.parse(slideReq.getStartAt(), formatter);
        LocalDate end_at = LocalDate.parse(slideReq.getEndAt(), formatter);

        try {
            slide = Slide.builder().title(slideReq.getTitle()).link(slideReq.getLink()).content(slideReq.getContent()).start_at(start_at).end_at(end_at).image(slideReq.getImage()).status(SaveType.valueOf(slideReq.getStatus())).build();

            slideRepository.save(slide);

        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_SLIDE);
        }


    }

    // 슬라이드 수정
    @Transactional
    public void updateSlides(SlideReq req, Long id) {
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;
//        String email = userDetails.getUsername();
//
//        Admin admin = adminRepository.findByEmailAndStatus(email, "ACTIVE").orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ADMIN_EMAIL));

        Slide slide = slideRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_SLIDE));

//        if(!admin.getIdx().equals(slide.getAdmin().getIdx())) {
//            throw new BaseException(BaseResponseCode.INVALID_ADMIN);
//        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(req.getStartAt(), formatter);
        LocalDate endDate = LocalDate.parse(req.getEndAt(), formatter);
        try {
            slide.changeSlide(req.getTitle(), req.getContent(), req.getLink(), startDate, endDate, req.getImage(), SaveType.valueOf(req.getStatus()));
            slideRepository.save(slide);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_SLIDE);
        }

    }

    // 슬라이드 조회
    public SlidePageRes readSlidesList(Pageable pageable) {
        Page<Slide> slideList;
        try {
            slideList = slideRepository.findAll(PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_FIND_SLIDE);
        }

        List<SlideListRes> slideListRes = new ArrayList<>();

        slideList.getContent().forEach((item) -> {
            String start_at = item.getStart_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String end_at = item.getEnd_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            SlideListRes slide = SlideListRes.builder()
                    .id(item.getId())
                    .title(item.getTitle())
                    .status(item.getStatus().name())
                    .link(item.getLink())
                    .content(item.getContent())
                    .startAt(start_at)
                    .endAt(end_at)
                    .build();

            slideListRes.add(slide);

        });

        return SlidePageRes.builder()
                .totalPage(pageable.getPageSize())
                .slideListResList(slideListRes)
                .build();

    }

    // 슬라이드 상세조회
    public SlideRes readSlides(Long id) {
        Slide slide = slideRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_SLIDE));

        return SlideRes.builder()
                .id(slide.getId())
                .title(slide.getTitle())
                .status(slide.getStatus().name())
                .link(slide.getLink())
                .content(slide.getContent())
                .startAt(slide.getStart_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .endAt(slide.getEnd_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .image(slide.getImage())
                .build();
    }

    // 슬라이드 종료
    @Transactional
    public void endSlides(StatusReq req) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetails userDetails = (UserDetails) principal;
//        String email = userDetails.getUsername();
//
//        Admin admin = adminRepository.findByEmailAndStatus(email, "ACTIVE").orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_ADMIN_EMAIL));

        List<Long> idList = req.getId();

        if (!req.getStatus().equals("END")) {
            throw new BaseException(BaseResponseCode.INVALID_SAVE_TYPE);
        }

        for (Long id : idList) {
            Slide slide = slideRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_SLIDE));
            try {
                slide.changeStatus();
                slideRepository.save(slide);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_SLIDE);
            }
        }

//        if(!admin.getIdx().equals(slide.getAdmin().getIdx())) {
//            throw new BaseException(BaseResponseCode.INVALID_ADMIN);
//        }


    }

}
