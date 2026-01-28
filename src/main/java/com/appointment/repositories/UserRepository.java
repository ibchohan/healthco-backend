package com.appointment.repositories;

import com.appointment.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameAndIdNot(String username, Long orgId);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long userId, Long orgId);

    Page<User> findAll(Specification<User> userSpecification, Pageable pageable);

    long count();

//    @Query(value = """
//            SELECT EXISTS (
//                SELECT 1 FROM task t WHERE t.assignee_id = :userId OR t.owner_id = :userId
//                UNION
//                SELECT 1 FROM task_step ts WHERE ts.creator_id = :userId
//                UNION
//                SELECT 1 FROM service_order so WHERE so.assignee_id = :userId
//                UNION
//                SELECT 1 FROM job_order jo WHERE jo.assignee_id = :userId
//            )
//            """, nativeQuery = true)
//    boolean isUserLinkedToAnyResource(Long userId);
}
