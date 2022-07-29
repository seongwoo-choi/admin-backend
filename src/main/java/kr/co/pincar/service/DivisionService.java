package kr.co.pincar.service;

import kr.co.pincar.exeception.BaseException;
import kr.co.pincar.exeception.BaseResponseCode;
import kr.co.pincar.jpa.dto.division.DivisionReq;
import kr.co.pincar.jpa.dto.divisionHistory.DivisionHistoryCreateReq;
import kr.co.pincar.jpa.entity.enums.DivisionStatus;
import kr.co.pincar.jpa.entity.newEntity.Division;
import kr.co.pincar.jpa.entity.newEntity.DivisionHistory;
import kr.co.pincar.jpa.repository.DivisionHistoryRepository;
import kr.co.pincar.jpa.repository.DivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DivisionService {
    private final DivisionRepository divisionRepository;
    private final DivisionHistoryRepository divisionHistoryRepository;

    public Division findDivision(Long id) {
        return divisionRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION));
    }

    @Transactional
    public Division changeStaffId(Long id, DivisionHistoryCreateReq req) {
        Division division = findDivision(id);

        division.setStaff_id(req.getStaffId());
        division.setDivision_status(DivisionStatus.INPROGRESS);
        division.setComplete_at(null);

        try {
            divisionRepository.save(division);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DIVISION);
        }

        return division;
    }

    @Transactional
    public Division saveDivision(Division division) {
        Division newDivision = Division.builder()
                .staff_id(division.getStaff_id())
                .division_status(division.getDivision_status())
                .question(division.getQuestion())
                .build();

        try {
            divisionRepository.save(newDivision);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DIVISION);
        }

        return newDivision;
    }

    @Transactional
    public void deleteDivision(Long id) {
        Division division = divisionRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseCode.FAILED_TO_FIND_DIVISION));

        try {
            divisionRepository.deleteById(division.getId());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_DELETE_DIVISION);
        }
    }

    @Transactional
    public void saveDivisionHistory(DivisionHistory divisionHistory) {
        try {
            divisionHistoryRepository.save(divisionHistory);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_DIVISION_HISTORY);
        }
    }

}
