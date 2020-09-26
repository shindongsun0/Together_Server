package com.together.smwu.domain.place.api;

import com.together.smwu.domain.place.applicatoin.PlaceService;
import com.together.smwu.domain.place.dto.PlaceCreateRequest;
import com.together.smwu.domain.place.dto.PlaceResponse;
import com.together.smwu.domain.place.dto.PlaceUpdateRequest;
import com.together.smwu.domain.security.security.CurrentUser;
import com.together.smwu.domain.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"6.Place"})
@RequestMapping("/api/place")
@RestController
public class PlaceController {
    public static final String PLACE_URI = "/api/place";

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @ApiOperation(value = "Place 생성", notes = "Place를 생성한다.")
    @PostMapping
    public ResponseEntity<Void> createPlace(
            @ApiParam(value = "PlaceCreateRequest", required = true)
            @RequestBody PlaceCreateRequest request,
            @CurrentUser User user) {
        Long placeId = placeService.create(request);
        return ResponseEntity
                .created(URI.create(PLACE_URI + "/" + placeId))
                .build();
    }

    @ApiOperation(value = "Place 업데이트", notes = "Place를 업데이트한다.")
    @PutMapping
    public ResponseEntity<Void> updatePlace(
            @ApiParam(value = "PlaceUpdateRequest", required = true)
            @RequestBody PlaceUpdateRequest request,
            @CurrentUser User user) {
        placeService.updatePlace(request);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Place 대표 이미지 업데이트", notes = "Place 대표 이미지를 s3에 업데이트한다.")
    @PutMapping("/image/{placeId}")
    public ResponseEntity<Void> updatePlaceMainImage(
            @ApiParam(value = "회원번호", required = true) @PathVariable Long placeId,
            @ApiParam(value = "Place 대표 이미지", required = true) @RequestBody MultipartFile multipartFile,
            @CurrentUser User user) throws IOException {
        placeService.updatePlaceMainImage(multipartFile, placeId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Place 단건 조회", notes = "PlaceId로 Place를 조회한다.")
    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceResponse> findPlaceById(
            @ApiParam(value = "회원번호", required = true) @PathVariable Long placeId,
            @CurrentUser User user) {
        PlaceResponse placeResponse = placeService.findByPlaceId(placeId);
        return ResponseEntity.ok(placeResponse);
    }

    @ApiOperation(value = "Place 전체 조회", notes = "전체 Place를 조회한다.")
    @GetMapping("/all")
    public ResponseEntity<List<PlaceResponse>> findAllPlaces(
            @CurrentUser User user) {
        List<PlaceResponse> placeResponses = placeService.findAllPlaces();
        return ResponseEntity.ok(placeResponses);
    }

    @ApiOperation(value = "Place name으로 조회", notes = "placeName을 포함하는 Place를 조회한다.")
    @GetMapping
    public ResponseEntity<List<PlaceResponse>> findPlacesByName(
            @ApiParam(value = "placeName", required = true) @RequestParam String placeName,
            @CurrentUser User user) {
        List<PlaceResponse> placeResponses = placeService.findByPlaceName(placeName);
        return ResponseEntity.ok(placeResponses);
    }
}
