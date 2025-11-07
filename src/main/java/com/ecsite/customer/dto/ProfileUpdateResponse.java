package com.ecsite.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateResponse {

  @Builder.Default private String status = "success";

  @Builder.Default private String message = "会員情報編集が正常に更新されました";

  private ProfileData data;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ProfileData {
    private String id;
    private String name;
    private String updatedAt;
  }
}
