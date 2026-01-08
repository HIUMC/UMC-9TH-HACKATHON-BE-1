package com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseErrorCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.code.BaseSuccessCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @Schema(description = "성공 여부", example = "true")
    @JsonProperty("isSuccess")
    private final boolean isSuccess;

    @Schema(description = "응답 코드", example = "USER200_1")
    @JsonProperty("code")
    private final String code;

    @Schema(description = "응답 메시지", example = "성공적으로 사용자를 조회했습니다.")
    @JsonProperty("message")
    private final String message;

    @Schema(description = "응답 데이터")
    @JsonProperty("result")
    private T result;

    // 성공한 경우 (result 포함)
    public static <T> ApiResponse<T> onSuccess(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // 실패한 경우 (result 포함)
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), result);
    }
}
