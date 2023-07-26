package com.example.cs348project.repository;

import com.example.cs348project.entity.LeaderboardSpot;
import com.example.cs348project.entity.LeaderboardSpotID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepository extends JpaRepository<LeaderboardSpot, LeaderboardSpotID> {

}
