package com.ppbkaf.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "User")
public class ActivatedUserDTO implements Serializable {
    private int id;
    private boolean isActivated;
}
