package com.github.saphyra.randwo.item.service.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.randwo.common.CollectionValidator;
import com.github.saphyra.randwo.common.ErrorCode;
import com.github.saphyra.randwo.item.domain.Item;
import com.github.saphyra.randwo.item.domain.ItemView;
import com.github.saphyra.randwo.item.domain.RandomItemRequest;
import com.github.saphyra.randwo.item.service.ItemQueryService;
import com.github.saphyra.randwo.mapping.itemlabel.domain.ItemLabelMapping;
import com.github.saphyra.randwo.mapping.itemlabel.repository.ItemLabelMappingDao;
import com.github.saphyra.randwo.mapping.itemvalue.domain.ItemValueMapping;
import com.github.saphyra.randwo.mapping.itemvalue.repository.ItemValueMappingDao;
import com.github.saphyra.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class RandomItemViewQueryServiceTest {
    private static final UUID LABEL_ID_1 = UUID.randomUUID();
    private static final UUID LABEL_ID_2 = UUID.randomUUID();
    private static final UUID KEY_ID_1 = UUID.randomUUID();
    private static final UUID ITEM_ID_1 = UUID.randomUUID();

    @Mock
    private CollectionValidator collectionValidator;

    @Mock
    private ItemQueryService itemQueryService;

    @Mock
    private ItemLabelMappingDao itemLabelMappingDao;

    @Mock
    private ItemValueMappingDao itemValueMappingDao;

    @Mock
    private ItemViewConverter itemViewConverter;

    @Mock
    private Random random;

    @InjectMocks
    private RandomItemViewQueryService underTest;

    @Mock
    private ItemLabelMapping itemLabelMapping1;

    @Mock
    private ItemLabelMapping itemLabelMapping2;

    @Mock
    private ItemValueMapping itemValueMapping;

    @Mock
    private Item item;

    @Mock
    private ItemView itemView;

    @Test
    public void nullLabelIds() {
        //GIVEN
        List<UUID> labelIds = null;
        List<UUID> keyIds = Arrays.asList(KEY_ID_1);
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(labelIds)
            .keyIds(keyIds)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.getRandomItem(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_LABEL_IDS.getErrorCode());
    }

    @Test
    public void nullKeyIds() {
        //GIVEN
        List<UUID> labelIds = Arrays.asList(LABEL_ID_1);
        List<UUID> keyIds = null;
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(labelIds)
            .keyIds(keyIds)
            .build();
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.getRandomItem(request));
        //THEN
        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.NULL_KEY_IDS.getErrorCode());
    }

    @Test
    public void getRandomItem_noItems() {
        //GIVEN
        List<UUID> labelIds = Arrays.asList(LABEL_ID_1);
        List<UUID> keyIds = Arrays.asList(KEY_ID_1);
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(labelIds)
            .keyIds(keyIds)
            .build();

        given(itemLabelMappingDao.getByLabelId(LABEL_ID_1)).willReturn(Arrays.asList(itemLabelMapping1));
        given(itemLabelMapping1.getItemId()).willReturn(ITEM_ID_1);
        given(itemValueMappingDao.findByItemIdAndKeyId(ITEM_ID_1, KEY_ID_1)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.getRandomItem(request));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.RANDOM_ITEM_CANNOT_BE_SELECTED.getErrorCode());

        verify(collectionValidator).validateDoesNotContainNull(labelIds, ErrorCode.NULL_IN_LABEL_IDS);
        verify(collectionValidator).validateDoesNotContainNull(keyIds, ErrorCode.NULL_IN_KEY_IDS);
    }

    @Test
    public void getRandomItem() {
        //GIVEN
        List<UUID> labelIds = Arrays.asList(LABEL_ID_1, LABEL_ID_2);
        List<UUID> keyIds = Arrays.asList(KEY_ID_1);
        RandomItemRequest request = RandomItemRequest.builder()
            .labelIds(labelIds)
            .keyIds(keyIds)
            .build();

        given(itemLabelMappingDao.getByLabelId(LABEL_ID_1)).willReturn(Arrays.asList(itemLabelMapping1));
        given(itemLabelMappingDao.getByLabelId(LABEL_ID_2)).willReturn(Arrays.asList(itemLabelMapping2));
        given(itemLabelMapping1.getItemId()).willReturn(ITEM_ID_1);
        given(itemLabelMapping2.getItemId()).willReturn(ITEM_ID_1);
        given(itemValueMappingDao.findByItemIdAndKeyId(ITEM_ID_1, KEY_ID_1)).willReturn(Optional.of(itemValueMapping));

        given(itemQueryService.findByItemIdValidated(ITEM_ID_1)).willReturn(item);
        given(itemViewConverter.convert(item)).willReturn(itemView);
        given(random.randInt(0, 0)).willReturn(0);
        //WHEN
        ItemView result = underTest.getRandomItem(request);
        //THEN
        assertThat(result).isEqualTo(itemView);
        verify(collectionValidator).validateDoesNotContainNull(labelIds, ErrorCode.NULL_IN_LABEL_IDS);
        verify(collectionValidator).validateDoesNotContainNull(keyIds, ErrorCode.NULL_IN_KEY_IDS);
    }
}