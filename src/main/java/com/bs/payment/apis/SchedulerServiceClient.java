package com.bs.payment.apis;

import com.bs.payment.exceptions.internalServer.SchedulerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.DeleteScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.GetScheduleRequest;
import software.amazon.awssdk.services.scheduler.model.GetScheduleResponse;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SchedulerServiceClient {

    private static final String RESERVATION_PREFIX = "Reservation-";
    private static final String SCHEDULE_SUFFIX = "-time_out_schedule";
    private final SchedulerClient schedulerClient;

    @Transactional
    public void deleteTimeOutSchedule(Long reservationId, String userId) {

        log.info("Attempting to delete schedule for reservationId: {}, userId: {}", reservationId, userId);

        String scheduleName = buildScheduleName(reservationId, userId);

        if (!isScheduleExists(scheduleName)) {
            throw new SchedulerNotFoundException(reservationId.toString(), userId);
        }

        DeleteScheduleRequest deleteScheduleRequest = DeleteScheduleRequest.builder()
                .name(scheduleName)
                .build();

        schedulerClient.deleteSchedule(deleteScheduleRequest);

    }

    private boolean isScheduleExists(String scheduleName) {
        GetScheduleRequest request = GetScheduleRequest.builder()
                .name(scheduleName)
                .build();
        GetScheduleResponse getScheduleResponse = schedulerClient.getSchedule(request);

        return getScheduleResponse != null;
    }

    private String buildScheduleName(Long reservationId, String userId) {
        return RESERVATION_PREFIX + reservationId + "-" + userId + SCHEDULE_SUFFIX;
    }

}
