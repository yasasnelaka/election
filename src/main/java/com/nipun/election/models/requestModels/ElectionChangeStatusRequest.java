package com.nipun.election.models.requestModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectionChangeStatusRequest {
    private int id;
    private int status;
}
