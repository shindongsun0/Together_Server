package com.together.smwu.domain.placeImage.api;

import com.together.smwu.domain.placeImage.appplicatoin.PlaceImageService;
import com.together.smwu.domain.placeImage.dto.PlaceImageRequest;
import com.together.smwu.domain.placeImage.dto.PlaceImageResponse;
import com.together.smwu.domain.security.security.CurrentUser;
import com.together.smwu.domain.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"7.PlaceImage"})
@RequestMapping("/api/place/image")
@RestController
public class placeImageController {
    public final PlaceImageService placeImageService;
    public static String PLACE_IMAGE_URI = "/api/place/image";


    public placeImageController(PlaceImageService placeImageService) {
        this.placeImageService = placeImageService;
    }

    @ApiOperation(value = "PlaceImage 추가", notes = "Place의 보조 Image들을 추가한다.")
    @PostMapping
    public ResponseEntity<Void> addPlaceImages(
            @ApiParam(value = "PlaceImageAddRequest", required = true)
            @RequestBody PlaceImageRequest request,
            @CurrentUser User user) {
        Long placeImageId = placeImageService.addPlaceImage(request);
        return ResponseEntity
                .created(URI.create(PLACE_IMAGE_URI + "/" + placeImageId))
                .build();
    }

    @ApiOperation(value = "Place의 모든 Image 조회", notes = "Place의 모든 Image를 placeId로 조회한다.")
    @GetMapping
    public ResponseEntity<List<PlaceImageResponse>> findPlaceImagesByPlaceId(
            @ApiParam(value = "placeId", required = true)
            @RequestParam Long placeId,
            @CurrentUser User user) {
        List<PlaceImageResponse> responses = placeImageService.findByPlaceId(placeId);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "PlaceImage 업데이트", notes = "Place의 Image들을 업데이트한다.")
    @PutMapping("/{placeImageId}")
    public ResponseEntity<Void> updatePlaceImageById(
            @ApiParam(value = "placeImageId", required = true) @PathVariable Long placeImageId,
            @ApiParam(value = "placeImageRequest", required = true)
            @RequestBody PlaceImageRequest request,
            @CurrentUser User user) {
        placeImageService.update(request);
        return ResponseEntity.noContent().build();
    }
}
