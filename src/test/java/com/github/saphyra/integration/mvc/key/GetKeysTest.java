package com.github.saphyra.integration.mvc.key;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.saphyra.common.configuration.MvcConfiguration;
import com.github.saphyra.common.testcomponent.DatabaseCleanup;
import com.github.saphyra.common.testcomponent.MockMvcWrapper;
import com.github.saphyra.randwo.common.ObjectMapperDelegator;
import com.github.saphyra.randwo.key.KeyController;
import com.github.saphyra.randwo.key.domain.Key;
import com.github.saphyra.randwo.key.domain.KeyView;
import com.github.saphyra.randwo.key.repository.KeyDao;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration(classes = {
    MvcConfiguration.class,
    GetKeysTest.class
})
public class GetKeysTest {
    private static final UUID KEY_ID = UUID.randomUUID();
    private static final String KEY_VALUE = "key_value";

    @Autowired
    private MockMvcWrapper mockMvcWrapper;

    @Autowired
    private ObjectMapperDelegator objectMapperDelegator;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private KeyDao keyDao;

    @After
    public void cleanUp() {
        databaseCleanup.clearRepositories();
    }

    @Test
    public void getAllKeys() throws Exception {
        //GIVEN
        Key key = Key.builder()
            .keyId(KEY_ID)
            .keyValue(KEY_VALUE)
            .build();
        keyDao.save(key);
        //WHEN
        MockHttpServletResponse result = mockMvcWrapper.getRequest(KeyController.GET_KEYS_MAPPING);
        //THEN
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());

        List<KeyView> resultKeys = objectMapperDelegator.readArrayValue(result.getContentAsString(), KeyView[].class);
        assertThat(resultKeys).hasSize(1);
        KeyView resultKey = resultKeys.get(0);
        assertThat(resultKey.getKeyId()).isEqualTo(KEY_ID);
        assertThat(resultKey.getKeyValue()).isEqualTo(KEY_VALUE);
        assertThat(resultKey.getItems()).isEqualTo(0);
        assertThat(resultKey.isDeletable()).isTrue();
    }
}
