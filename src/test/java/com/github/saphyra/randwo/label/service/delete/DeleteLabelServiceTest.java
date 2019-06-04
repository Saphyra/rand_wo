package com.github.saphyra.randwo.label.service.delete;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.label.repository.LabelDao;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;

@RunWith(MockitoJUnitRunner.class)
public class DeleteLabelServiceTest {
    private static final UUID LABEL_ID = UUID.randomUUID();

    @Mock
    private CollectionValidator collectionValidator;

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private LabelDao labelDao;

    @Mock
    private LabelDeletionValidator labelDeletionValidator;

    @InjectMocks
    private DeleteLabelService underTest;

    @Test
    public void deleteLabels() {
        //GIVEN
        List<UUID> labelIds = Arrays.asList(LABEL_ID);
        //WHEN
        underTest.deleteLabels(labelIds);
        //THEN
        verify(collectionValidator).validateDoesNotContainNull(labelIds, ErrorCode.NULL_IN_LABEL_IDS);
        verify(labelDeletionValidator).validateLabelCanBeDeleted(LABEL_ID);
        verify(itemLabelMappingDao).deleteByLabelId(LABEL_ID);
        verify(labelDao).deleteById(LABEL_ID);
    }
}