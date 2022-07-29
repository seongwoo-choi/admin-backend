package kr.co.pincar.jpa.repository.teamPincarPick;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.pincar.jpa.dto.teamPincarPick.QTeamPincarPickAndOwnProductRes;
import kr.co.pincar.jpa.dto.teamPincarPick.QTeamPincarPickRes;
import kr.co.pincar.jpa.dto.teamPincarPick.TeamPincarPickAndOwnProductRes;
import kr.co.pincar.jpa.dto.teamPincarPick.TeamPincarPickRes;
import kr.co.pincar.jpa.entity.enums.DisplayStatus;
import kr.co.pincar.jpa.entity.enums.VehicleType;
import kr.co.pincar.jpa.entity.newEntity.QOwnProductList;
import kr.co.pincar.jpa.entity.newEntity.QProduct;
import kr.co.pincar.jpa.entity.newEntity.QTeamPincarPick;

import javax.persistence.EntityManager;
import java.util.List;

import static kr.co.pincar.jpa.entity.newEntity.QOwnProductList.*;
import static kr.co.pincar.jpa.entity.newEntity.QProduct.*;
import static kr.co.pincar.jpa.entity.newEntity.QTeamPincarPick.*;

public class TeamPincarPickRepositoryImpl implements TeamPincarPickRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public TeamPincarPickRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long deleteTeamPincarPick(List<Long> idList) {
        return jpaQueryFactory
                .delete(teamPincarPick)
                .where(teamPincarPick.id.in(idList))
                .execute();
    }

    @Override
    public long updateDisplayStatus() { // 기존에 display list로 지정되어있는 애들 모두 NO로 초기화
        return jpaQueryFactory
                .update(teamPincarPick)
                .set(teamPincarPick.displayStatus, DisplayStatus.NO)
                .where(teamPincarPick.displayStatus.eq(DisplayStatus.YES))
                .execute();
    }


    @Override
    public TeamPincarPickRes findOneTeamPincarPick(VehicleType vehicleType, Long pick_id) {
        return jpaQueryFactory
                .select(new QTeamPincarPickRes(
                        teamPincarPick.id,
                        teamPincarPick.displayOrder,
                        teamPincarPick.ownProductList.id,
                        product.image,
                        product.price,
                        ownProductList.yearMileage.viewText,
                        ownProductList.contractPeriod.periodType,
                        ownProductList.deposit.view_text,
                        teamPincarPick.rentalFee
                )).from(teamPincarPick)
                .leftJoin(ownProductList).on(ownProductList.id.eq(teamPincarPick.ownProductList.id))
                .leftJoin(product).on(ownProductList.product.id.eq(product.id))
                .where(teamPincarPick.ownProductList.vehicleType.eq(vehicleType).and(teamPincarPick.id.eq(pick_id)))
                .fetchOne();
    }

    @Override
    public List<TeamPincarPickRes> findAllTeamPincarPick(DisplayStatus displayStatus, VehicleType vehicleType) {
        return jpaQueryFactory
                .select(new QTeamPincarPickRes(
                        teamPincarPick.id,
                        teamPincarPick.displayOrder,
                        teamPincarPick.ownProductList.id,
                        product.image,
                        product.price,
                        ownProductList.yearMileage.viewText,
                        ownProductList.contractPeriod.periodType,
                        ownProductList.deposit.view_text,
                        teamPincarPick.rentalFee
                )).from(teamPincarPick)
                .leftJoin(ownProductList).on(ownProductList.id.eq(teamPincarPick.ownProductList.id))
                .leftJoin(product).on(ownProductList.product.id.eq(product.id))
                .where(teamPincarPick.ownProductList.vehicleType.eq(vehicleType).and(teamPincarPick.displayStatus.eq(displayStatus)))
                .fetch();
    }
}
