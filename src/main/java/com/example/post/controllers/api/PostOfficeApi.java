package com.example.post.controllers.api;

import com.example.post.models.PostOffice;
import com.example.post.models.util.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/office")
@Tag(name = "Post office api")
public interface PostOfficeApi {
    @Operation(summary = "Create post office")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Return created post office index",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Long.class
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
    ResponseEntity<Long> createPostOffice(@RequestBody PostOffice office);

    @Operation(summary = "Getting post office info by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = PostOffice.class
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
    @GetMapping("/{officeId}")
    ResponseEntity<PostOffice> getPostOfficeById(@PathVariable("officeId") long officeId);
}
