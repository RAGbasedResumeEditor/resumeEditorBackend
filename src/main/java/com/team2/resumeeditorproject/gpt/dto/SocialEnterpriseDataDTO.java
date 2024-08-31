package com.team2.resumeeditorproject.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialEnterpriseDataDTO {
    private String addr;
    private String baseD;
    private String busiAreaNm;
    private String busiContV;
    private String busiFiledNm;
    private String ceoNmV;
    private String certiEvalResult;
    private String certiIssuD;
    private String certiNumV;
    private String entNmV;
    private String faxNo;
    private String homepageAddrV;
    private String telNo;
}
