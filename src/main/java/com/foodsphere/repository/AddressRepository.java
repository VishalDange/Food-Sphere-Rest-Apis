package com.foodsphere.repository;

import com.foodsphere.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
