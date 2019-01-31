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

@Table(name = "UserOpinion")
public class UserOpinionDTO implements Serializable {
    private int id;
    private int saleId;
    private int opinion;
}
