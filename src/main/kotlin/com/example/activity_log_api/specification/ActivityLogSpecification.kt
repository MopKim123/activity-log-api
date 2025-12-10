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
    const val USER = "user"
    const val USER_ID = "id"
    const val ACTIVITY_TYPE = "activityType"
    const val ACTIVITY_TYPE_ID = "id"
    const val CREATED_AT = "createdAt"
}

class ActivityLogSpecification {
    companion object {

        fun toSpec(filters: ActivityLogFilterRequest): Specification<ActivityLog> {
            return Specification { root: Root<ActivityLog>, _, builder: CriteriaBuilder ->
                if (filters.isEmpty() && filters.userId == null) {
                    return@Specification builder.conjunction()
                }

                val predicates = mutableListOf<Predicate>()

                // Filter by activity type
                filters.activityTypeId?.let {
                    val activityTypeJoin: Path<ActivityType> = root.get(ActivityLogFields.ACTIVITY_TYPE)
                    predicates.add(builder.equal(activityTypeJoin.get<Long>(ActivityLogFields.ACTIVITY_TYPE_ID), it))
                }

                // Filter by date range
                datePredicate(root.get(ActivityLogFields.CREATED_AT), filters.startDate, filters.endDate, builder)
                    .takeIf { it != builder.conjunction() }
                    ?.let { predicates.add(it) }

                // Filter by user
                filters.userId?.let {
                    val userJoin = root.get<Any>(ActivityLogFields.USER)
                    predicates.add(builder.equal(userJoin.get<Long>(ActivityLogFields.USER_ID), it))
                }

                builder.and(*predicates.toTypedArray())
            }
        }

        private fun datePredicate(
            path: Path<LocalDateTime>,
            start: LocalDateTime?,
            end: LocalDateTime?,
            builder: CriteriaBuilder
        ): Predicate {
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
