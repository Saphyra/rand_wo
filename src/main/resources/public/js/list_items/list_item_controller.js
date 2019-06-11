(function ListItemController(){
    scriptLoader.loadScript("/js/common/cache.js");

    events.SEARCH_ITEMS = "search_items";
    events.DELETE_ITEMS = "delete_items";

    const labelCache = new Cache(loadLabel);
    const keyCache = new Cache(loadKey);

    $(document).ready(init);

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.DELETE_ITEMS},
        function(){
            const selectedItems = [];
            $(".select-checkbox:checked").each(function(){selectedItems.push(this.value)});

            if(!selectedItems.length){
                notificationService.showError(Localization.getAdditionalContent("no-selected-items"));
                return;
            }

            if(confirm(Localization.getAdditionalContent("confirm-delete-items"))){
                const request = new Request(HttpMethod.DELETE, Mapping.DELETE_ITEMS, selectedItems);
                    request.processValidResponse = function(){
                        notificationService.showSuccess(Localization.getAdditionalContent("items-deleted"));
                        loadItems();
                    }
                dao.sendRequestAsync(request);
            }
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SEARCH_ITEMS},
        loadItems
    ));

    function loadItems(){
        const searchByLabel = $("#search-by-label").val();
        const searchByKey = $("#search-by-key").val();
        const searchByValue = $("#search-by-value").val();

        const body = {
            searchByLabel: searchByLabel,
            searchByKey: searchByKey,
            searchByValue: searchByValue
        };
        const request = new Request(HttpMethod.POST, Mapping.GET_ITEMS, body);
            request.convertResponse = function(response){
                return JSON.parse(response.body);
            }
            request.processValidResponse = function(items){
                displayItems(items);
            }
        dao.sendRequestAsync(request);
    }

    function displayItems(items){
        items.length ? $("#no-items").hide() : $("#no-items").show();

        const container = document.getElementById("items-table");
            container.innerHTML = "";

        if(!items.length){
            return;
        }

        const columnMapping = mapColumns(items);
        items.sort(function(a, b){
            for(let keyId in columnMapping){
                const aColumn = a.columns[keyId];
                const bColumn = b.columns[keyId];

                if(aColumn != undefined && bColumn == undefined){
                    return -1;
                }

                if(aColumn == undefined && bColumn != undefined){
                    return 1;
                }
            }

            return 0;
        });

        const header = document.createElement("TR");
            header.classList.add("items-head");
            header.appendChild(document.createElement("TH"));

            for(let keyId in columnMapping){
                const columnHeader = document.createElement("TH");
                    columnHeader.innerHTML = keyCache.get(keyId);
                header.appendChild(columnHeader);
            }

            const labelsHeader = document.createElement("TH");
                labelsHeader.innerHTML = Localization.getAdditionalContent("labels-header");
        header.appendChild(labelsHeader);

            const operationsHeader = document.createElement("TH");
                operationsHeader.innerHTML = Localization.getAdditionalContent("operations-header");
        header.appendChild(operationsHeader);
        container.appendChild(header);

        for(let iIndex in items){
            const item = items[iIndex];

            const row = document.createElement("TR");
                row.classList.add("item");

                const checkboxColumn = document.createElement("TD");
                    const checkbox = document.createElement("INPUT");
                        checkbox.classList.add("select-checkbox");
                        checkbox.value = item.itemId;
                        checkbox.type = "checkbox";
                checkboxColumn.appendChild(checkbox);
            row.appendChild(checkboxColumn);

                for(let keyId in columnMapping){
                    const valueColumn = document.createElement("TD");
                    if(item.columns[keyId] != undefined){
                        const value = document.createElement("TEXTAREA");
                            value.classList.add("value-area");
                            value.value = item.columns[keyId];
                            value.readOnly = true;
                            value.style.width = "90%";
                        valueColumn.appendChild(value);
                    }

                    row.appendChild(valueColumn);
                }

                const labelColumn = document.createElement("TD");
                    for(let lIndex in item.labelIds){
                        const labelId = item.labelIds[lIndex];

                        const label = document.createElement("SPAN");
                            label.classList.add("label");
                            label.innerHTML = labelCache.get(labelId);
                        labelColumn.appendChild(label);
                    }
            row.appendChild(labelColumn);

                const operationsColumn = document.createElement("TD");
                    const editButton = document.createElement("BUTTON");
                        editButton.innerHTML = Localization.getAdditionalContent("edit-button");
                        editButton.onclick = function(){
                            window.location.href = "items/edit/" + item.itemId;
                        }
                operationsColumn.appendChild(editButton);
            row.appendChild(operationsColumn);
            container.appendChild(row);
        }

        function mapColumns(items){
            const result = {};

            for(let iIndex in items){
                const item = items[iIndex];
                for(let keyId in item.columns){
                    if(!result[keyId]){
                        result[keyId] = 0;
                    }

                    result[keyId]++;
                }
            }
            return orderMapByProperty(result, function(a, b){return b.getValue() - a.getValue()});
        };
    }

    function init(){
        loadItems();

        document.getElementById("search-by-label").onkeyup = triggerSearch;
        document.getElementById("search-by-key").onkeyup = triggerSearch;
        document.getElementById("search-by-value").onkeyup = triggerSearch;

        function triggerSearch(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.SEARCH_ITEMS));
            }
        }

        document.getElementById("select-all-checkbox").onchange = function(){
            $(".select-checkbox").prop("checked", document.getElementById("select-all-checkbox").checked);
        }
    }

    function loadLabel(labelId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.GET_LABEL, labelId));

        return JSON.parse(response.body).labelValue;
    }

    function loadKey(keyId){
        const response = dao.sendRequest(HttpMethod.GET, Mapping.concat(Mapping.GET_KEY, keyId));
        return JSON.parse(response.body).keyValue;
    }
})();