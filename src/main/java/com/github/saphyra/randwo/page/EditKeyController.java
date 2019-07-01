package com.github.saphyra.randwo.page;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.key.service.KeyQueryService;
import com.github.saphyra.randwo.page.component.ModelAndViewFactory;
import com.github.saphyra.randwo.page.domain.ModelAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.UUID;

import static com.github.saphyra.randwo.page.KeysController.KEYS_MAPPING;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EditKeyController {
    private static final String EDIT_KEY_MAPPING = "keys/edit/{keyId}";

    private final KeyQueryService keyQueryService;
    private final ModelAndViewFactory modelAndViewFactory;

    @GetMapping(EDIT_KEY_MAPPING)
    public ModelAndView editKey(@PathVariable("keyId") UUID keyId) {
        log.info("Request arrived to {} with keyId {}", EDIT_KEY_MAPPING, keyId);

        try {
            return modelAndViewFactory.create(
                "edit_key",
                Arrays.asList(
                    new ModelAttribute("keyId", keyId)
                ),
                new ModelAttribute("keyValue", keyQueryService.findByKeyIdValidated(keyId).getKeyValue())
            );
        } catch (NotFoundException e) {
            if (e.getErrorMessage().getErrorCode().equals(ErrorCode.KEY_NOT_FOUND.getErrorCode())) {
                log.warn("Key not found. Redirecting...");
                return new ModelAndView("forward:" + KEYS_MAPPING);
            }

            throw e;
        }
    }
}
