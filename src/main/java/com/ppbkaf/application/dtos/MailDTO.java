package com.ppbkaf.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailDTO {
    @Email
    private String email;
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
}
