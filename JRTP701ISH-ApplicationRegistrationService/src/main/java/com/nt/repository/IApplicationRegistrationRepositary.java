package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.CitizenAppRegistrationEntity;

public interface IApplicationRegistrationRepositary extends JpaRepository<CitizenAppRegistrationEntity, Integer> {

}
