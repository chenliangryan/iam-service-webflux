package com.yugabyte.demo.iamservicewebflux.service;

import com.yugabyte.demo.iamservicewebflux.domain.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.yugabyte.demo.iamservicewebflux.domain.UserAudit;
import com.yugabyte.demo.iamservicewebflux.domain.UserProfile;
import com.yugabyte.demo.iamservicewebflux.domain.UserSvcAccount;
import com.yugabyte.demo.iamservicewebflux.repository.UserAuditRepository;
import com.yugabyte.demo.iamservicewebflux.repository.UserCredentialsRepository;
import com.yugabyte.demo.iamservicewebflux.repository.UserProfileRepository;
import com.yugabyte.demo.iamservicewebflux.repository.UserSvcAccountRepository;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserAuditRepository userAuditRepository;
    private final UserSvcAccountRepository userSvcAccountRepository;

    public UserService(
            UserCredentialsRepository userCredentialsRepository,
            UserProfileRepository userProfileRepository,
            UserAuditRepository userAuditRepository,
            UserSvcAccountRepository userSvcAccountRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.userProfileRepository = userProfileRepository;
        this.userAuditRepository = userAuditRepository;
        this.userSvcAccountRepository = userSvcAccountRepository;
    }

//    public Mono<UserProfile> authenticate(String userId, String passwordHash) {
//        return userProfileRepository.findById(
//                userCredentialsRepository.authenticate(userId, passwordHash));
//    }

//    public Mono<UserProfile> searchByUserId(String userId) {
//        return userProfileRepository.findById(userCredentialsRepository.findByUserId(userId));
//    }
//
//    public Flux<?> getUserSvcAccountRecentActivities(String userId, Integer count) {
//        return userSvcAccountActivitiesDao.retrieveRecentUserSvcAccountActivities(userId, count);
//    }

//    public Mono<Boolean> updatePassword(String userId, String oldPasswordHash,
//                                        String newPasswordHash) {
//        return userCredentialsRepository.updatePasswordHash(userId, oldPasswordHash, newPasswordHash);
//    }

//    public Mono<UserProfile> createUserProfile(UserProfile userProfile) {
//        return userProfileRepository.save(userProfile);
//    }

//    public Flux<UserProfile> getUserProfiles() {
//        return userProfileRepository.findAll();
//    }
//
//    public Mono<UserProfile> getUserProfile(Integer userProfileId) {
//        return userProfileRepository.findById(userProfileId);
//    }

    public Mono<UserProfileVO> getUserProfile(String userId) {
        if (userId.isEmpty())
            throw new RuntimeException(format("%1$s: Not Found", userId));

        Mono<UserProfile> userProfileMono = userCredentialsRepository.findById(userId)
                .flatMap(userCredential -> {
                    return userProfileRepository.findById(userCredential.getUserProfileId());
                });;
        Flux<UserAudit> userAuditFlux = userAuditRepository.recentLogins(userId, 10);
        Flux<UserSvcAccount> userSvcAccountFlux = userSvcAccountRepository.findServiceAccountsForUser(userId);

        return Flux.concat(Flux.from(userProfileMono), userAuditFlux, userSvcAccountFlux).
                collect(UserProfileVO::new, (upvo, o) -> {
                    if(o instanceof UserProfile) {
                        log.debug("{}: Set user profile", userId);
                        upvo.init((UserProfile) o);
                    }else if (o instanceof UserAudit) {
                        log.debug("{}: Added user audit", userId);
                        upvo.getUserActivities().add(UserActivitiesVO.from((UserAudit) o));
                    }else if (o instanceof UserSvcAccount) {
                        log.debug("{}: Added user service account", userId);
                        upvo.getServiceAccount()
                                .add(ServiceAccountVO.from((UserSvcAccount) o));
                    }else {
                        log.error("{}: Unrecognised object of type: ", o.getClass().getName());
                    }
                });
    }

    public Mono<UserProfileVO> authenticate(String userId, String passwordHash) {
        return userCredentialsRepository.authenticate(userId, passwordHash)
                .flatMap(id -> {
                    return getUserProfile(id);
                });
    }

}