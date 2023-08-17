package com.example.post.controllers.api;

import com.example.post.models.PackageDeliveryDto;
import com.example.post.models.PostPackage;
import com.example.post.models.util.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/package")
@Tag(name = "Package api")
public interface PackageApi {
    @Operation(summary = "Getting package info by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = PostPackage.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorMessage.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("/{packageId}")
    ResponseEntity<PostPackage> getPackageInfoById(@PathVariable("packageId") long packageId);

    @Operation(summary = "Getting package delivery status by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = PackageDeliveryDto.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorMessage.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("/{packageId}/status")
    ResponseEntity<PackageDeliveryDto> getDeliveryStatusByPackageId(@PathVariable("packageId") long packageId);

    @Operation(summary = "Getting package delivery history status by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = PackageDeliveryDto.class
                                            )
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorMessage.class
                                    )
                            )
                    }
            )
    })
    @GetMapping("/{packageId}/history")
    ResponseEntity<List<PackageDeliveryDto>> getDeliveryHistoryByPackageId(@PathVariable("packageId") long packageId);
}
