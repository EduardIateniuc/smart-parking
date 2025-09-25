package eduard.smartparking.service;

import eduard.smartparking.dto.StartParkingResponse;
import eduard.smartparking.dto.UserResponse;
import eduard.smartparking.model.ParkingSession;
import eduard.smartparking.model.User;
import eduard.smartparking.repository.ParkingSessionRepository;
import eduard.smartparking.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final UserRepository userRepo;

    public UserService(ParkingSessionRepository parkingSessionRepository, UserRepository userRepo) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.userRepo = userRepo;
    }

    public List<StartParkingResponse> getParkingSessionByUser(Authentication authentication) {
        User authenticatedUser = userRepo.findByUsername(authentication.getName()).get();
        List<ParkingSession> parkingSessions = parkingSessionRepository.getParkingSessionByUserId(authenticatedUser.getId());
        return parkingSessions.stream()
                .map(session -> StartParkingResponse.builder()
                        .id(session.getId())
                        .cost(session.getCost())
                        .licensePlate(session.getLicensePlate())
                        .startTime(session.getStartTime())
                        .endTime(session.getEndTime())
                        .parkingZone(session.getParkingZone())
                        .user(UserResponse.convertToUserResponse(session.getUser()))
                        .build()
                ).collect(Collectors.toList());
    }

}
