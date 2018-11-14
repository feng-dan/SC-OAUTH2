package com.adrian.controller;

import com.adrian.service.RoleResourcesService;
import com.adrian.util.HttpStatusContent;
import com.adrian.util.enums.OutputState;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author asus
 * @ClassName ResourcesController
 * @description TODO
 * @Date 2018/11/5 15:56
 * @Version 1.0v
 **/
@Log4j
@RestController
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private RoleResourcesService roleResourcesService;

    /**
     * 获取所有父级资源
     *
     * @return List<RoleInfo>
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        PageImpl allByComponentContaining = roleResourcesService.findAllByComponentContaining(page-1, size);
        if (allByComponentContaining.getContent().size() > 0) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, allByComponentContaining), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取所有子级资源
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find")
    public ResponseEntity<?> findNotAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        PageImpl allByComponentNotContains = roleResourcesService.findAllByComponentNotContains(page-1, size);
        if (allByComponentNotContains.getContent().size() > 0) {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.SUCCESS, allByComponentNotContains), HttpStatus.OK);
        } else {
            return new ResponseEntity<HttpStatusContent>(new HttpStatusContent(OutputState.FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
