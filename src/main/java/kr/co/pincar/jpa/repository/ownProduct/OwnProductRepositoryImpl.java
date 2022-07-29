package kr.co.pincar.jpa.repository.ownProduct;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.pincar.jpa.dto.ownProduct.OwnProductListRes;
import kr.co.pincar.jpa.dto.ownProduct.QOwnProductListRes;
import kr.co.pincar.jpa.entity.enums.VehicleType;

import javax.persistence.EntityManager;
import java.util.List;

import static kr.co.pincar.jpa.entity.newEntity.QOwnProductList.*;
import static kr.co.pincar.jpa.entity.newEntity.QProduct.*;
import static kr.co.pincar.jpa.entity.newEntity.QContractPeriod.*;
import static kr.co.pincar.jpa.entity.newEntity.QDeposit.*;
import static kr.co.pincar.jpa.entity.newEntity.QYearMileage.*;
import static kr.co.pincar.jpa.entity.newEntity.QPrepayment.*;

public class OwnProductRepositoryImpl implements OwnProductRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public OwnProductRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<OwnProductListRes> findAllOwnProductByVehicleType(VehicleType vehicleType) {
        return jpaQueryFactory
                .select(new QOwnProductListRes(
                        ownProductList.id,
                        ownProductList.vehicleType,
                        product.name,
                        product.price,
                        contractPeriod.periodType,
                        yearMileage.viewText,
                        deposit.view_text,
                        prepayment.view_text
                )).from(ownProductList)
                .leftJoin(product).on(ownProductList.product.id.eq(product.id))
                .leftJoin(contractPeriod).on(ownProductList.contractPeriod.id.eq(contractPeriod.id))
                .leftJoin(yearMileage).on(ownProductList.yearMileage.id.eq(yearMileage.id))
                .leftJoin(deposit).on(ownProductList.deposit.id.eq(deposit.id))
                .leftJoin(prepayment).on(ownProductList.prepayment.id.eq(prepayment.id))
                .where(ownProductList.vehicleType.eq(vehicleType))
                .fetch();
    }



    @Override
    public OwnProductListRes findOneOwnProduct(Long ownProductId) {
        return jpaQueryFactory
                .select(new QOwnProductListRes(
                        ownProductList.id,
                        ownProductList.vehicleType,
                        product.name,
                        product.price,
                        contractPeriod.periodType,
                        yearMileage.viewText,
                        deposit.view_text,
                        prepayment.view_text
                )).from(ownProductList)
                .leftJoin(product).on(ownProductList.product.id.eq(product.id))
                .leftJoin(contractPeriod).on(ownProductList.contractPeriod.id.eq(contractPeriod.id))
                .leftJoin(yearMileage).on(ownProductList.yearMileage.id.eq(yearMileage.id))
                .leftJoin(deposit).on(ownProductList.deposit.id.eq(deposit.id))
                .leftJoin(prepayment).on(ownProductList.prepayment.id.eq(prepayment.id))
                .where(ownProductList.id.eq(ownProductId))
                .fetchOne();
    }
}
