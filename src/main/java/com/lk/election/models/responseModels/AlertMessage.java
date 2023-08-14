package com.lk.election.models.responseModels;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class AlertMessage implements Serializable {
    private String header = "Alert";
    private String message;
    private String type;
    private boolean show;

    @Override
    public String toString() {
        return "AlertMessage{" +
                "header='" + header + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", show=" + show +
                '}';
    }
}