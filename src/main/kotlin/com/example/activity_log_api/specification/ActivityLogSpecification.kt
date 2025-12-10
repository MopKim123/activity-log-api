package com.example.activity_log_api.specification

import com.example.activity_log_api.model.ActivityLog
import com.example.activity_log_api.model.ActivityType
import com.example.activity_log_api.model.dto.ActivityLogFilterRequest
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime

object ActivityLogFields {
    const val ACTIVITY_TYPE = "activityType"
    const val ACTIVITY_TYPE_ID = "id"
    const val CREATED_AT = "createdAt"
}

class ActivityLogSpecification {
    companion object {

        fun toSpec(filters: ActivityLogFilterRequest): Specification<ActivityLog> {
            return Specification { root: Root<ActivityLog>, _, builder: CriteriaBuilder ->
                if (filters.isEmpty()) {
                    return@Specification builder.conjunction()
                }

                builder.and(
                    filters.activityTypeId?.let {
                        val activityTypeJoin: Path<ActivityType> = root.get(ActivityLogFields.ACTIVITY_TYPE)
                        builder.equal(activityTypeJoin.get<Long>(ActivityLogFields.ACTIVITY_TYPE_ID), it)
                    } ?: builder.conjunction(),

                    datePredicate(root.get(ActivityLogFields.CREATED_AT), filters.startDate, filters.endDate, builder)
                )
            }
        }

        private fun datePredicate(path: Path<LocalDateTime>, start: LocalDateTime?, end: LocalDateTime?, builder: CriteriaBuilder): Predicate {
            val predicates = mutableListOf<Predicate>()
            start?.let { predicates.add(builder.greaterThanOrEqualTo(path, it)) }
            end?.let { predicates.add(builder.lessThanOrEqualTo(path, it)) }
            return if (predicates.isEmpty()) builder.conjunction() else builder.and(*predicates.toTypedArray())
        }

        fun ActivityLogFilterRequest.isEmpty(): Boolean {
            return activityTypeId == null && startDate == null && endDate == null
        }
    }
}