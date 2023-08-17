package com.example.post.controllers.api;

import com.example.post.models.PackageDeliveryStatus;
import com.example.post.models.PostPackage;
import com.example.post.models.util.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/office/{postOfficeId}/packages")
@Tag(name = "Package delivery api")
public interface PackageDeliveryApi {
    @Operation(summary = "Register package")
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
    @PostMapping
    ResponseEntity<?> registerPackage(@PathVariable("postOfficeId") long postOffice, @RequestBody PostPackage postPackage);

    @Operation(summary = "Update package delivery status",
            parameters = @Parameter(
                    name = "status",
                    schema = @Schema(
                            allowableValues = {"ARRIVE", "DEPART", "RECEIVE"}
                    )
            )
    )
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
    @PutMapping("/{packageId}/status")
    ResponseEntity<?> updateStatus(@PathVariable("postOfficeId") long postOfficeId,
                                   @PathVariable("packageId") long packageId,
                                   @RequestParam(value = "status") PackageDeliveryStatus status);
}
