package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.controller;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto.StatisticsResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto.TransactionReqDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.dto.TransactionResDTO;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.exception.code.TransactionSuccessCode;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.service.TransactionService;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

//    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})

    @PostMapping
    @Operation(summary = "수입/지출 내역 추가 API", description = "새로운 내역을 저장합니다.")
    public ApiResponse<Long> createTransaction(
            @RequestBody @Valid TransactionReqDTO.TransactionCreateRequest request,
            @Parameter(hidden = true) @SessionAttribute(name = "MEMBER_ID") Long memberId)
    {
        return ApiResponse.onSuccess(TransactionSuccessCode.OK, transactionService.saveTransaction(request, memberId));
    }

    // 이미지 직접 받는 가정의 코드
//    public ApiResponse<Long> createTransaction(
//            @Parameter(
//                    description = "내역 정보 (JSON)",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) // Swagger에게 JSON임을 명시
//            )
//            @RequestPart(value = "request") @Valid TransactionReqDTO.TransactionCreateRequest request,
//            @RequestPart(value = "image", required = false) MultipartFile image) {
//        Long id = transactionService.saveTransaction(request, image);
//        return ApiResponse.onSuccess(TransactionSuccessCode.OK, id);
//    }

    @DeleteMapping("/{transactionId}")
    @Operation(summary = "수입/지출 내역 단건 삭제 API", description = "특정 내역 1개를 삭제합니다.")
    public ApiResponse<Void> deleteTranscation(@PathVariable Long transactionId,
                                               @Parameter(hidden = true) @SessionAttribute(name = "MEMBER_ID") Long memberId) {
        transactionService.deleteTransaction(transactionId, memberId);
        return ApiResponse.onSuccess(TransactionSuccessCode.OK, null);
    }

    @DeleteMapping
    @Operation(summary = "전체 내역 삭제 API")
    public ApiResponse<Void> deleteAllTransactions(@Parameter(hidden = true) @SessionAttribute(name = "MEMBER_ID") Long memberId) {
        transactionService.deleteAllTransactions(memberId);
        return ApiResponse.onSuccess(TransactionSuccessCode.OK, null);
    }

    @GetMapping("/daily")
    @Operation(summary = "날짜별 조회 API")
    public ApiResponse<TransactionResDTO.DailyTransactionResponse> getDailyTransactions(
            @RequestParam(name = "date") LocalDate date,
            @Parameter(hidden = true) @SessionAttribute(name = "MEMBER_ID") Long memberId) {

        return ApiResponse.onSuccess(TransactionSuccessCode.OK, transactionService.getDailyTransactions(date, memberId));
    }

    @GetMapping("/month")
    @Operation(summary = "월별 요약 조회 API", description = "달력 화면에서 날짜별 수입/지출 합계를 보여주기 위해 사용합니다.")
    public ApiResponse<TransactionResDTO.MonthlySummaryResponse> getMonthlySummary(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            @Parameter(hidden = true) @SessionAttribute(name = "MEMBER_ID") Long memberId) {
        return ApiResponse.onSuccess(TransactionSuccessCode.OK, transactionService.getMonthlySummary(year, month, memberId));
    }

    @GetMapping("/statics")
    public ApiResponse<StatisticsResDTO.MonthlyStats> getStatics(
            @RequestParam String month,
            HttpSession session
    ) {
        Long memberId = (Long) session.getAttribute("MEMBER_ID");
        if (memberId == null) throw new RuntimeException("로그인이 필요합니다.");

        return ApiResponse.onSuccess(TransactionSuccessCode.OK, transactionService.getMonthlyStatistics(memberId, month));
    }
}
