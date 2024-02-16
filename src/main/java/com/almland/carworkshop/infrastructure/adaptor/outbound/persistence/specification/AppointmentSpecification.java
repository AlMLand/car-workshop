package com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.specification;

import com.almland.carworkshop.domain.WorkShopOffer;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.AppointmentEntity;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.AppointmentEntity_;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.TimeSlotEntity_;
import com.almland.carworkshop.infrastructure.adaptor.outbound.persistence.entity.WorkShopEntity_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static jakarta.persistence.criteria.JoinType.LEFT;

@Component
public class AppointmentSpecification {
    public Specification<AppointmentEntity> allAppointmentsBySpecification(
            UUID workShopId,
            LocalDateTime from,
            LocalDateTime until,
            WorkShopOffer workShopOffer
    ) {
        return (root, queryBuilder, criteriaBuilder) -> {
            queryBuilder.distinct(true);
            var predicates = getArrayOfPredicates(workShopId, from, until, workShopOffer, root, criteriaBuilder);
            return criteriaBuilder.and(predicates);
        };
    }

    private Predicate[] getArrayOfPredicates(
            UUID workShopId,
            LocalDateTime from,
            LocalDateTime until,
            WorkShopOffer workShopOffer,
            Root<AppointmentEntity> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return Stream.of(
                        getWorkShopIdPredicate(workShopId, root, criteriaBuilder),
                        getFromPredicate(from, root, criteriaBuilder),
                        getUntilPredicate(until, root, criteriaBuilder),
                        getWorkShopOfferPredicate(workShopOffer, root, criteriaBuilder)
                )
                .filter(Objects::nonNull)
                .toList()
                .toArray(new Predicate[0]);
    }

    private Predicate getWorkShopOfferPredicate(
            WorkShopOffer workShopOffer,
            Root<AppointmentEntity> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return workShopOffer == null ?
                null :
                criteriaBuilder.equal(root.get(AppointmentEntity_.workShopOffer), workShopOffer);
    }

    private Predicate getUntilPredicate(
            LocalDateTime until,
            Root<AppointmentEntity> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return until == null ?
                null :
                criteriaBuilder.lessThanOrEqualTo(
                        root.join(AppointmentEntity_.timeSlotEntity, LEFT).get(TimeSlotEntity_.startTime), until
                );
    }

    private Predicate getFromPredicate(
            LocalDateTime from,
            Root<AppointmentEntity> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return from == null ?
                null :
                criteriaBuilder.greaterThanOrEqualTo(
                        root.join(AppointmentEntity_.timeSlotEntity, LEFT).get(TimeSlotEntity_.startTime), from
                );
    }

    private Predicate getWorkShopIdPredicate(
            UUID workShopId,
            Root<AppointmentEntity> root,
            CriteriaBuilder criteriaBuilder
    ) {
        return criteriaBuilder.equal(
                root.join(AppointmentEntity_.workShopEntity, LEFT).get(WorkShopEntity_.workShopId), workShopId
        );
    }
}
