package com.tomato.sys.controller;

import com.tomato.common.exception.BusinessException;
import com.tomato.common.resp.Resp;
import com.tomato.jpa.domain.service.BaseReadableService;
import com.tomato.jpa.domain.service.BaseWriteableService;
import com.tomato.sys.domain.entity.SysTenant;
import com.tomato.sys.domain.service.SysTenantService;
import com.tomato.web.jpa.controller.AbstractBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多租户
 * @author lizhifu
 */
@RestController
@Tags({@Tag(name = "多租户")})
public class SysTenantController extends AbstractBaseController<SysTenant, Long> {
    private final SysTenantService sysTenantService;
    public SysTenantController(SysTenantService sysTenantService) {
        this.sysTenantService = sysTenantService;
    }
    @Operation(summary = "根据租户ID查询",
            responses = {
                    @ApiResponse(description = "查询到的租户数据", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysTenant.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @Parameters({@Parameter(name = "tenantId", in = ParameterIn.PATH, required = true, description = "租户ID")})
    @GetMapping("/tenant/{tenantId}")
    public Resp<SysTenant> findByTenantId(@PathVariable("tenantId") Long tenantId) {
        SysTenant sysTenant = sysTenantService.findByTenantId(tenantId).orElseThrow(() -> new BusinessException("未找到租户信息"));
        return result(sysTenant);
    }
    @Override
    public BaseReadableService<SysTenant, Long> getReadableService() {
        return this.sysTenantService;
    }

    @Override
    public BaseWriteableService<SysTenant, Long> getWriteableService() {
        return this.sysTenantService;
    }
}
