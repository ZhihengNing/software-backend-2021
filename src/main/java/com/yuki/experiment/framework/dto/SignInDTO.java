package com.yuki.experiment.framework.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class SignInDTO {

    private BigDecimal attendanceScore;


    private Date lastAttendanceTime;

}
