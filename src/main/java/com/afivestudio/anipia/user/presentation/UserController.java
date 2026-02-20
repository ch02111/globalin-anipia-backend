package com.afivestudio.anipia.user.presentation;

import com.afivestudio.anipia.global.CommonErrorResponse;
import com.afivestudio.anipia.user.application.UserRegisterCommand;
import com.afivestudio.anipia.user.application.UserService;
import com.afivestudio.anipia.user.application.UserUpdateCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "会員関連API")
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "成功"),
            @ApiResponse(responseCode = "400", description = "不正なリクエスト",
                    content = @Content(schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    @Operation(operationId = "registerUser", summary = "会員登録", description = "会員を登録します。")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void register(@Valid @RequestBody UserRegisterCommand command) {
        userService.register(command);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "成功"),
            @ApiResponse(responseCode = "400", description = "不正なリクエスト",
                    content = @Content(schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    @Operation(operationId = "updateUser", summary = "会員情報更新", description = "会員情報を更新します。")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{userId}")
    public void update(
            @PathVariable @Positive long userId,
            @Valid @RequestBody UserUpdateCommand command
    ) {
        userService.update(userId, command);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "成功"),
            @ApiResponse(responseCode = "400", description = "不正なリクエスト",
                    content = @Content(schema = @Schema(implementation = CommonErrorResponse.class)))
    })
    @Operation(operationId = "deactivateUser", summary = "会員退会", description = "会員を退会させます。")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deactivate(@PathVariable @Positive long userId) {
        userService.withdraw(userId);
    }
}
