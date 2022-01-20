package com.nipun.election.models.responseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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