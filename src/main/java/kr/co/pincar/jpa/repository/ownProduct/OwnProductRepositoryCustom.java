package kr.co.pincar.jpa.repository.ownProduct;

import kr.co.pincar.jpa.dto.ownProduct.OwnProductListRes;
import kr.co.pincar.jpa.entity.enums.VehicleType;

import java.util.List;

public interface OwnProductRepositoryCustom {

    List<OwnProductListRes> findAllOwnProductByVehicleType(VehicleType vehicleType);

    OwnProductListRes findOneOwnProduct(Long ownProductId);
}
