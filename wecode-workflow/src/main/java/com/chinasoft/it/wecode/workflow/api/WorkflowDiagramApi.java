package com.chinasoft.it.wecode.workflow.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 工作流图形API
 */
@RestController
@RequestMapping(value = "/workflow/diagram")
public class WorkflowDiagramApi {

    @GetMapping(value = "/get", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage() throws IOException {
        return null;
    }

}
