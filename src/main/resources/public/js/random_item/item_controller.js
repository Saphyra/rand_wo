(function ItemController(){
    scriptLoader.loadScript("/js/common/cache.js");

    events.DISPLAY_NEW_WORD = "display_new_word";

    const keyCache = new Cache(loadKey);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DISPLAY_NEW_WORD},
        function(){
            const selectedLabels = labelController.getSelectedLabels();

            const storedSettings = columnController.getStoredSettings();
            const displayedColumns = Object.keys(storedSettings.getStorage())
                .filter(function(keyId){
                    return $("[value=" + keyId + "]").length;
                })
                 .filter(function(keyId){
                     const storedSetting = storedSettings.getStoredSetting(keyId);
                     return storedSetting.isDisplayed();
                 });
            const columns = displayedColumns
                .filter(function(keyId){
                    const storedSetting = storedSettings.getStoredSetting(keyId);
                    return !storedSetting.isHidden();
                });

            if(selectedLabels.length == 0){
                notificationService.showError(MessageCode.getMessage("no-labels"));
                return;
            }

            if(columns.length == 0){
                notificationService.showError(MessageCode.getMessage("no-columns"));
                return;
            }

            const request = new Request(HttpMethod.POST, Mapping.GET_RANDOM_ITEM, {labelIds: selectedLabels, keyIds: columns});
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(item){
                    displayItem(item, displayedColumns);
                }
            dao.sendRequestAsync(request);
        }
    ));

    function displayItem(item, displayedColumns){
        const container = document.getElementById("items");
            container.innerHTML = "";

        displayedColumns.sort(function(a, b){return keyCache.get(a).localeCompare(keyCache.get(b))});

        const columns = item.columns;
        const storedSettings = columnController.getStoredSettings();
        const headRow = document.createElement("TR");
        const valueRow = document.createElement("TR");

        for(let kIndex in displayedColumns){
            const keyId = displayedColumns[kIndex];
            const storedSetting = storedSettings.getStoredSetting(keyId);

            if(!storedSetting.isDisplayed()){
                continue;
            }

            const headCell = document.createElement("TH");
                headCell.innerHTML = keyCache.get(keyId);
            headRow.appendChild(headCell);

            const valueCell = document.createElement("TD");
                const value = columns[keyId];
                if(value == undefined){
                    valueCell.innerHTML = Localization.getAdditionalContent("no-value");
                }else{
                    const valueArea = document.createElement("TEXTAREA");
                        valueArea.classList.add("value-area");
                        valueArea.value = columns[keyId];
                        valueArea.readOnly = true;
                    valueCell.appendChild(valueArea);

                    if(storedSetting.isHidden()){
                        valueArea.style.display = "none";

                        const revealButton = document.createElement("BUTTON");
                            revealButton.innerHTML = Localization.getAdditionalContent("reveal");
                        valueCell.appendChild(revealButton);

                        revealButton.onclick = function(){
                            valueArea.style.display = "block";
                            valueCell.removeChild(revealButton);
                        }
                    }
                }
            valueRow.appendChild(valueCell);
        }

            const operationsHead = document.createElement("TH");
                operationsHead.innerHTML = Localization.getAdditionalContent("operations");
        headRow.appendChild(operationsHead);

            const operationsCell = document.createElement("TD");
                const deleteButton = document.createElement("BUTTON");
                    deleteButton.innerHTML = Localization.getAdditionalContent("delete");
                    deleteButton.onclick = function(){
                        deleteItem(item.itemId);
                    }
            operationsCell.appendChild(deleteButton);

                const editButton = document.createElement("BUTTON");
                    editButton.innerHTML = Localization.getAdditionalContent("edit");
                    editButton.onclick = function(){
                        window.open("items/edit/" + item.itemId);
                    }
            operationsCell.appendChild(editButton);
        valueRow.appendChild(operationsCell);

        container.appendChild(headRow);
        container.appendChild(valueRow);
    }

    function deleteItem(itemId){
        if(!confirm(Localization.getAdditionalContent("confirm-delete-item"))){
            return;
        }
        const request = new Request(HttpMethod.DELETE, Mapping.DELETE_ITEMS, [itemId]);
            request.processValidResponse = function(){
                document.getElementById("items").innerHTML = "";
                notificationService.showSuccess(MessageCode.getMessage("items-deleted"));
                eventProcessor.processEvent(new Event(events.LOAD_LABELS));
            }
        dao.sendRequestAsync(request);
    }

    function loadKey(keyId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.GET_KEY, keyId));
        return JSON.parse(response.body).keyValue;
    }
})();